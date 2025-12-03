package com.catalyst.style_on.exception;

import org.springframework.security.core.AuthenticationException;

public class UnauthenticatedException extends AuthenticationException {
    public UnauthenticatedException(String message) {
        super(message);
    }
}
