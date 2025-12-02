package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.shared.api.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/styles")
public class StyleController {
    private final StyleService styleService;

    @GetMapping
    public Mono<ApiResponse<List<Style>>> findStyles(@NotNull @Valid @RequestParam String tag) {
        return styleService.findStylesByTagName(tag)
                .collectList()
                .map(ApiResponse::ok);
    }

//    @PostMapping
//    public Mono<ApiResponse<?>> submitStylePreferences() {
//
//    }
}
