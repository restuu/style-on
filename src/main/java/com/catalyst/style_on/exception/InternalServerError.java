package com.catalyst.style_on.exception;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String message, Exception cause) {
        super(message, cause);
    }

    public InternalServerError(String message) {
        super(message);
    }
}
