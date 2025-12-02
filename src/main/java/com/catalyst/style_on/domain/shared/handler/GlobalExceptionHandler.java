package com.catalyst.style_on.domain.shared.handler;

import com.catalyst.style_on.domain.shared.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception ex) {

        log.error("Internal Server Error", ex);

        ApiResponse<?> response = ApiResponse.internalServerError();
        return ResponseEntity.internalServerError().body(response);
    }
}
