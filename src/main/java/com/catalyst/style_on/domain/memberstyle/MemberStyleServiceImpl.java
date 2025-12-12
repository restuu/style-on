package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import com.catalyst.style_on.domain.memberstyle.entity.MemberStyle;
import com.catalyst.style_on.domain.memberstyle.entity.MemberStyleItem;
import com.catalyst.style_on.domain.style.StyleRepository;
import com.catalyst.style_on.domain.style.enumeration.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberStyleServiceImpl implements MemberStyleService {

    private final MemberStyleRepository memberStyleRepository;
    private final MemberStyleItemRepository memberStyleItemRepository;
    private final StyleRepository styleRepository;

    @Override
    @Transactional
    public Mono<MemberStyleResponseDTO> submitMemberStyle(Long memberId, MemberStyleSubmitRequestDTO req) {
        return styleRepository.findAllById(req.styleIds())
                .collectList()
                .map(styles -> {
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

                    log.debug("key count: {}", keyStyleCount);
                    log.debug("movement count: {}", movementCount);
                    log.debug("strap material count: {}", strapMaterialCount);
                    log.debug("color count: {}", colorCount);
                    log.debug("price count: {}", priceCount);

                    Map<String, Object> summary = Map.of(
                            "key_style", keyStyleCount,
                            "movement", movementCount,
                            "strap_material", strapMaterialCount,
                            "color", colorCount,
                            "price", priceCount
                    );


                    // TODO: Implement logic to determine the final style name based on counts
                    String determinedStyleName = "Top Style"; // Placeholder

                    return Tuples.of(determinedStyleName, summary);
                })
                .flatMap(processed -> {
                    ZonedDateTime now = ZonedDateTime.now();

                    // 1. Create the parent MemberStyle with its items set to null.
                    // It cannot be saved with new children attached.
                    MemberStyle memberStyle = new MemberStyle(
                            null,
                            memberId,
                            processed.getT1(),
                            processed.getT2(),
                            now,
                            now
                    );

                    // 2. Save the parent entity first to generate its ID.
                    return memberStyleRepository.save(memberStyle);
                })
                .flatMap(savedMemberStyle -> {
                    // 3. Now that the parent has an ID, create the child items.
                    // The framework will use the parent's ID to set the foreign key.
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
                .map(saved -> new MemberStyleResponseDTO(saved.id(), saved.name()));

    }
}
