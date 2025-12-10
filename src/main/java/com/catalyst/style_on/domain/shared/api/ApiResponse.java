package com.catalyst.style_on.domain.shared.api;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(boolean success, String message, T data) {

    public static final class Message {
        public static final String SUCCESS = "Success";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
        public static final String NOT_FOUND = "Not Found";
        public static final String BAD_REQUEST = "Bad Request";
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, Message.SUCCESS, data);
    }

    public static ApiResponse<?> internalServerError() {
        return new ApiResponse<>(false, Message.INTERNAL_SERVER_ERROR, null);
    }

    public static ApiResponse<?> internalServerError(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public static ApiResponse<?> notFound() {
        return new ApiResponse<>(false, Message.NOT_FOUND, null);
    }

    public static ApiResponse<?> badRequest(String message) {
        return new ApiResponse<>(false, message, null);
    }


    public static  ApiResponse<Void> unauthenticated() {
        return new ApiResponse<>(false, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
    }
}
