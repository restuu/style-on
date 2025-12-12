package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@RestController
@RequestMapping("/api/v1/styles")
@SecurityRequirement(name = JWT_BEARER_AUTH)
@Tag(name = "Style", description = "Style related APIs")
@RequiredArgsConstructor
@Slf4j
public class StyleController {
    private final StyleService styleService;

    @Operation(description = "Retrieves a list of all available styles.")
    @GetMapping("")
    public Mono<ApiResponse<List<StyleResponseDTO>>> findStyles() {
        return styleService.findAllStyles()
                .collectList()
                .map(ApiResponse::ok);
    }

}
