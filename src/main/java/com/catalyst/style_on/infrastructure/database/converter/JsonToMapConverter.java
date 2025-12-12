package com.catalyst.style_on.infrastructure.database.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
@RequiredArgsConstructor
public class JsonToMapConverter implements GenericConverter {

    private final ObjectMapper objectMapper;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Json.class, Map.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        final Json json = (Json) source;

        try {
            return objectMapper.readValue(json.asString(), targetType.getType());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse JSON", e);
        }
    }
}
