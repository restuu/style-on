package com.catalyst.style_on.domain.shared.json;

import com.catalyst.style_on.exception.InternalServerError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    private static ObjectMapper objectMapper;

    /**
     * Initializes the utility class with a Spring-managed ObjectMapper.
     * This method should only be called once at application startup.
     */
    public static void init(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static <T> Mono<T> reactiveDeserialize(String source, Class<T> clazz) {
        // Use fromCallable to wrap the synchronous call that might throw an exception.
        // It's more idiomatic for this use case.
        return Mono.fromCallable(() -> deserialize(source, clazz));
    }

    public static <T> T deserialize(String source, Class<T> clazz) {
        try {
            return objectMapper.readValue(source, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON to {}: {}", clazz.getSimpleName(), source, e);
            // Use your application-specific exception for consistency.
            throw new InternalServerError("Failed to process data due to deserialization error.", e);
        }
    }
}
