package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.ai.AIModel;
import com.catalyst.style_on.domain.downloader.Downloader;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleImagineStyleDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.catalyst.style_on.domain.memberstyle.entity.MemberStyle;
import com.catalyst.style_on.domain.memberstyle.entity.MemberStyleItem;
import com.catalyst.style_on.domain.memberstyle.entity.MemberStyleProduct;
import com.catalyst.style_on.domain.productindex.ProductIndex;
import com.catalyst.style_on.domain.productindex.ProductIndexService;
import com.catalyst.style_on.domain.productindex.dto.ProductIndexSearchParamsDTO;
import com.catalyst.style_on.domain.style.Style;
import com.catalyst.style_on.domain.style.StyleRepository;
import com.catalyst.style_on.domain.style.enumeration.*;
import com.catalyst.style_on.exception.InternalServerError;
import com.google.genai.Client;
import com.google.genai.types.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberStyleServiceImpl implements MemberStyleService {

    private final MemberStyleRepository memberStyleRepository;
    private final MemberStyleItemRepository memberStyleItemRepository;
    private final MemberStyleProductRepository memberStyleProductRepository;
    private final StyleRepository styleRepository;
    private final ProductIndexService productIndexService;
    private final Client genAi;
    private final Downloader downloader;

    @Override
    @Transactional
    public Mono<MemberStyleResponseDTO> submitMemberStyle(Long memberId, MemberStyleSubmitRequestDTO req) {
        return styleRepository.findAllById(req.styleIds())
                .collectList()
                .zipWhen(styles -> Mono.just(this.summarizeSelectedStyles(styles)))
                .flatMap(tuple2 -> {
                    var summary = tuple2.getT2();
                    var styles = tuple2.getT1();

                    var nameMono = MemberStyleUtils.generateStyleName(styles, summary)
                            .map(Mono::just)
                            .orElseGet(() -> generateStyleName(summary)
                                    .doOnNext(generatedName -> log.info("AI generated name: {}", generatedName)));

                    var descriptionMono = MemberStyleUtils.generateStyleDescription(styles, summary)
                            .map(Mono::just)
                            .orElseGet(() -> generateStyleDescription(summary)
                                    .doOnNext(generatedDescription -> log.info("AI generated description: {}", generatedDescription)));


                    return Mono.zip(nameMono, descriptionMono)
                            .flatMap(nameAndDescription -> {
                                var name = nameAndDescription.getT1();
                                var description = nameAndDescription.getT2();

                                var now = ZonedDateTime.now();

                                MemberStyle memberStyle = new MemberStyle(
                                        null,
                                        memberId,
                                        name,
                                        description,
                                        summary,
                                        now,
                                        now
                                );

                                return memberStyleRepository.save(memberStyle);
                            });
                })
                .flatMap(savedMemberStyle -> {
                    Set<MemberStyleItem> items = req.styleIds().stream()
                            .map(styleId -> new MemberStyleItem(null,
                                    savedMemberStyle.id(),
                                    styleId,
                                    ZonedDateTime.now(),
                                    ZonedDateTime.now()))
                            .collect(Collectors.toSet());

                    return memberStyleItemRepository.saveAll(items)
                            .then(Mono.just(savedMemberStyle));
                })
                .zipWhen(savedMemberStyle -> {

                    ProductIndexSearchParamsDTO searchParams = MemberStyleMapper
                            .memberStyleSummaryDTOToProductIndexSearchParamsDTO(savedMemberStyle.summary());

                    return productIndexService.searchProducts(searchParams)
                            .collectList()
                            .flatMap(productIndices -> {
                                List<MemberStyleProduct> memberStyleProducts = new ArrayList<>();

                                for (int i = 0; i < productIndices.size(); i++) {
                                    ProductIndex productIndex = productIndices.get(i);

                                    MemberStyleProduct memberStyleProduct = new MemberStyleProduct(
                                            null,
                                            savedMemberStyle.id(),
                                            productIndex.sku(),
                                            i
                                    );
                                    memberStyleProducts.add(memberStyleProduct);
                                }
                                return memberStyleProductRepository.saveAll(memberStyleProducts)
                                        .then(Mono.just(productIndices));
                            });

                })
                .map(tuple2 -> {
                    MemberStyle memberStyle = tuple2.getT1();
                    List<ProductIndex> productIndices = tuple2.getT2();

                    List<MemberStyleResponseDTO.Product> products = new ArrayList<>();
                    for (ProductIndex productIndex : productIndices) {
                        MemberStyleResponseDTO.Product product = new MemberStyleResponseDTO.Product(
                                productIndex.sku(),
                                productIndex.name(),
                                productIndex.price().price(),
                                productIndex.price().retailPrice(),
                                buildProductImageUrl(productIndex)
                        );

                        products.add(product);
                    }

                    return new MemberStyleResponseDTO(memberStyle.id(),
                            memberStyle.name(),
                            memberStyle.description(),
                            products);
                });

    }

    @Override
    public Mono<ByteArrayResource> imagineMemberStyle(MemberStyleImagineStyleDTO params) {

        return productIndexService.findBySku(params.modelNumber())
                .map(this::buildProductImageUrl)
                .flatMap(downloader::downloadToMemory)
                .flatMap(imageBuffer ->
                        combineSubmittedImageWithProductImage(params.image(), imageBuffer))
                .flatMap(response -> {
                    // The parts() method from the GenAI client can return null.
                    // We must handle this case explicitly.
                    var parts = response.parts();
                    if (parts == null) {
                        return Mono.error(new InternalServerError("AI response did not contain any parts."));
                    }

                    byte[] generatedImage = null;
                    for (Part part : parts) {
                        generatedImage = part.inlineData()
                                .flatMap(Blob::data)
                                .orElse(null);
                        if (generatedImage != null) break;
                    }

                    if (generatedImage == null) {
                        return Mono.error(new InternalServerError("Agent did not return an image in the response parts."));
                    }
                    return Mono.just(new ByteArrayResource(generatedImage));
                });
    }

    private MemberStyleSummaryDTO summarizeSelectedStyles(List<Style> styles) {
        Map<StyleEnum, Integer> keyStyleCount = new HashMap<>();
        Map<StyleMovementEnum, Integer> movementCount = new HashMap<>();
        Map<StyleStrapMaterialEnum, Integer> strapMaterialCount = new HashMap<>();
        Map<StyleColorEnum, Integer> colorCount = new HashMap<>();
        Map<StylePriceEnum, Integer> priceCount = new HashMap<>();

        styles.forEach(style -> {
            Arrays.stream(style.keyStyle()).forEach(key -> keyStyleCount.merge(key, 1, Integer::sum));
            movementCount.merge(style.movement(), 1, Integer::sum);
            strapMaterialCount.merge(style.strapMaterial(), 1, Integer::sum);
            Arrays.stream(style.colors()).forEach(color -> colorCount.merge(color, 1, Integer::sum));
            priceCount.merge(style.price(), 1, Integer::sum);
        });

        return MemberStyleSummaryDTO.builder()
                .movement(movementCount)
                .keyStyle(keyStyleCount)
                .strapMaterial(strapMaterialCount)
                .color(colorCount)
                .price(priceCount)
                .build();
    }

    private String buildProductImageUrl(ProductIndex productIndex) {
        String host = "https://assetsnew.machtwatch.net";

        return host +
                "/images/product/" +
                productIndex.brandCode() +
                "/" +
                productIndex.images().jpg();
    }

    private Mono<String> generateStyleName(MemberStyleSummaryDTO summary) {
        String prompt = """
                With examples such as:
                
                %s
                
                and this weighed parameter: %s
                
                Please create 1 style name with length not longer than 50 char without any special characters.
                Return only the style name since I will save this generated text directly to database.
                """
                .formatted(
                        StringUtils.join(MemberStyleUtils.listAvailableNames(), "\n"),
                        summary.toString()
                );

        log.info("Generating style name with prompt: {}", prompt);

        return Mono.fromCallable(() -> genAi.models
                        .generateContent(AIModel.GEMINI_2_5_FLASH, prompt, GenerateContentConfig.builder()
                                .httpOptions(HttpOptions.builder()
                                        .timeout(15 * 1000)
                                        .build())
                                .build())
                        .text())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<String> generateStyleDescription(MemberStyleSummaryDTO summary) {
        String prompt = """
                With examples such as:
                
                %s
                
                and this weighed parameter: %s
                
                Please create style description with length not longer than 200 char.
                Return only the style description since I will save this generated text directly to database.
                """
                .formatted(
                        StringUtils.join(MemberStyleUtils.listAvailableDescriptions(), "\n"),
                        summary.toString()
                );

        log.info("Generating style description with prompt: {}", prompt);

        return Mono.fromCallable(() -> genAi.models
                        .generateContent(AIModel.GEMINI_2_5_FLASH, prompt,
                                GenerateContentConfig.builder()
                                        .httpOptions(HttpOptions.builder()
                                                .timeout(15 * 1000)
                                                .build())
                                        .build())
                        .text())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<GenerateContentResponse> combineSubmittedImageWithProductImage(DataBuffer image, DataBuffer productImage) {
        return Mono.fromCallable(() -> {

                    GenerateContentConfig config = GenerateContentConfig.builder()
                            .responseModalities("TEXT", "IMAGE")
                            .httpOptions(HttpOptions.builder()
                                    .timeout(15 * 1000) // 15 seconds
                                    .build())
                            .build();

                    byte[] imageBytes = new byte[image.readableByteCount()];
                    image.read(imageBytes);

                    byte[] productImageBytes = new byte[productImage.readableByteCount()];
                    productImage.read(productImageBytes);

                    String specializedPrompt = """
                            Perform a Virtual Try-On.
                            Input 1 (Person/Wrist): [image1]
                            Input 2 (Garment): [image2]
                            Task: Generate a photorealistic image of the person wearing the garment.
                            Output: Image only.
                            """;

                    GenerateContentResponse response = genAi.models.generateContent(
                            AIModel.GEMINI_2_5_FLASH_IMAGE, // Only if this specific model version confirms VTO support
                            Content.fromParts(
                                    Part.fromText(specializedPrompt),
                                    Part.fromBytes(imageBytes, "image/jpeg"),   // Explicitly handle order
                                    Part.fromBytes(productImageBytes, "image/jpeg")
                            ),
                            config
                    );

                    return response;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
