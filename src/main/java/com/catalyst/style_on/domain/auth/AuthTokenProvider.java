package com.catalyst.style_on.domain.auth;

import reactor.core.publisher.Mono;

public interface AuthTokenProvider {
    Mono<String> generateToken(AuthCustomClaim claim);
}
