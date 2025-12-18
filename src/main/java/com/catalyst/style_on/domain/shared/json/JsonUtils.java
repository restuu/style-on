package com.catalyst.style_on.domain.shared.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import reactor.core.publisher.Mono;

public final class JsonUtils {
    private static ObjectMapper mapper;

    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = JsonMapper.builder()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                    .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                    .build();
        }
        return mapper;
    }

    public static <T> Mono<T> reactiveDeserialize(String source, Class<T> clazz) {
        return Mono.create(sink -> {
            try {
                T result = getMapper().readValue(source, clazz);
                sink.success(result);
            } catch (JsonProcessingException e) {
                sink.error(new RuntimeException(e));
            }
        });
    }

    public static <T> T deserialize(String source, Class<T> clazz) {
        try {
            return getMapper().readValue(source, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
