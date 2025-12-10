package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.style.enumeration.StyleAttibuteEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@WritingConverter
@RequiredArgsConstructor
public class MapStyleMetadataToJsonConverter implements Converter<Map<StyleAttibuteEnum, Object>, Json> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Json convert(@NonNull Map<StyleAttibuteEnum, Object> source) {
        return Json.of(objectMapper.writeValueAsBytes(source));
    }
}