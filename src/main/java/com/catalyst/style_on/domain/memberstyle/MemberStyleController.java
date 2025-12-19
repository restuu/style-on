package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleImagineStyleDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.util.SecurityUtils;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@RestController
@RequestMapping("api/v1/member-styles")
@SecurityRequirement(name = JWT_BEARER_AUTH)
@RequiredArgsConstructor
@Slf4j
public class MemberStyleController {
    private final MemberStyleService memberStyleService;

    @PostMapping("")
    public Mono<ApiResponse<MemberStyleResponseDTO>> submitMemberStyles(@RequestBody @Valid MemberStyleSubmitRequestDTO req) {
        return SecurityUtils.getCurrentMemberId()
                .flatMap(memberId -> memberStyleService.submitMemberStyle(memberId, req))
                .map(ApiResponse::ok);
    }

    @PutMapping(path = "/{member_style_id}/imagine",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<ResponseEntity<Resource>> submitImagineImage(@PathVariable("member_style_id") Long memberStyleId,
                                                             @RequestPart("image") Mono<FilePart> image,
                                                             @RequestPart("model_number") String modelNumber) {
        return Mono.zip(objects -> {
                            Long memberId = (Long) objects[0];
                            DataBuffer filePart = (DataBuffer) objects[1];

                            return new MemberStyleImagineStyleDTO(memberId, memberStyleId, filePart, modelNumber);
                        },
                        SecurityUtils.getCurrentMemberId(),
                        image.flatMap(i -> DataBufferUtils.join(i.content()))
                )
                .flatMap(memberStyleService::imagineMemberStyle)
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentLength(resource.contentLength())
                        .body(resource));
    }
}
