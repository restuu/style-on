package com.catalyst.style_on.domain.auth;

import com.catalyst.style_on.domain.auth.dto.AuthRegisterRequestDTO;
import com.catalyst.style_on.domain.shared.api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public Mono<ApiResponse<?>> register(@RequestBody @Valid AuthRegisterRequestDTO request) {
        return Mono.just(ApiResponse.ok(request));
    }

}
