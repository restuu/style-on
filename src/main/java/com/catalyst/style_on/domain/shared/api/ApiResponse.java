package com.catalyst.style_on.domain.shared.api;

public record ApiResponse<T>(boolean success, String message, T data) {

    public static final class Message {
        public static final String SUCCESS = "Success";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, Message.SUCCESS, data);
    }

    public static ApiResponse<?> internalServerError() {
        return new ApiResponse<>(false, Message.INTERNAL_SERVER_ERROR, null);
    }
}
