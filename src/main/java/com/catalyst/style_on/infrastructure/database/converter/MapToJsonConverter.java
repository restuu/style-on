package com.catalyst.style_on.infrastructure.database.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
@RequiredArgsConstructor
public class MapToJsonConverter implements GenericConverter {

    private final ObjectMapper objectMapper;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Map.class, Json.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        try {
            return Json.of(objectMapper.writeValueAsBytes(source));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error encoding object to JSON", e);
        }
    }
}
