package com.catalyst.style_on.infrastructure.security.jwt;

import com.catalyst.style_on.exception.JwtAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtToken.class)
                .doOnNext(jwtToken -> {
                    log.info("member id: {}", jwtToken.getPrincipal().getMemberId());
                })
                .filter(jwtToken -> jwtService.isValidClaims(jwtToken.getPrincipal()))
                .map(jwtToken -> jwtToken.withAuthenticated(true))
                .switchIfEmpty(Mono.error(new JwtAuthException("Invalid JWT Token")));
    }
}
