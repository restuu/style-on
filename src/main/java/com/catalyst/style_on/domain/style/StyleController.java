package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = JWT_BEARER_AUTH)
@RequestMapping("/api/v1/styles")
public class StyleController {
    private final StyleService styleService;

    @GetMapping
    public Mono<ApiResponse<List<StyleResponseDTO>>> findStyles() {
        return styleService.findAllStyles()
                .collectList()
                .map(ApiResponse::ok);
    }

}
