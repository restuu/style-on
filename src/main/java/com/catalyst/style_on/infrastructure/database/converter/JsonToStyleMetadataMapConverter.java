package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.style.enumeration.StyleAttibuteEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Map;

@ReadingConverter
@RequiredArgsConstructor
public class JsonToStyleMetadataMapConverter implements Converter<Json, Map<StyleAttibuteEnum, Object>> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Map<StyleAttibuteEnum, Object> convert(@NonNull Json source) {
        // The key is to use a TypeReference to guide Jackson in deserializing the generic map
        return objectMapper.readValue(source.asString(), new TypeReference<>() {});
    }
}