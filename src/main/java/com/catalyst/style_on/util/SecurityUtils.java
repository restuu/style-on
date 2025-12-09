package com.catalyst.style_on.util;

import com.catalyst.style_on.domain.auth.AuthClaim;
import com.catalyst.style_on.exception.UnauthenticatedException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class SecurityUtils {

    public static Mono<Long> getCurrentMemberId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .switchIfEmpty(Mono.error(new UnauthenticatedException("User is not authenticated")))
                .map(authentication -> {
                    if (authentication == null || !authentication.isAuthenticated()) {
                        throw new UnauthenticatedException("User is not authenticated");
                    }
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof AuthClaim) {
                        return ((AuthClaim) principal).getMemberId();
                    }
                    throw new UnauthenticatedException("Invalid authentication principal");
                });
    }

}
