package com.catalyst.style_on.util;

import com.catalyst.style_on.domain.auth.AuthClaim;
import com.catalyst.style_on.exception.UnauthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthClaim) {
            return ((AuthClaim) principal).getMemberId();
        }

        throw  new UnauthenticatedException("Invalid authentication");
    }

}
