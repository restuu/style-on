package com.catalyst.style_on.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthException extends AuthenticationException {
    public JwtAuthException(String message) {
        super(message);
    }
}
