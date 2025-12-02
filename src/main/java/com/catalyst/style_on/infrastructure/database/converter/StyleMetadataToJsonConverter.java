package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.style.Style;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
@RequiredArgsConstructor
public class StyleMetadataToJsonConverter implements Converter<Style.Metadata, Json> {

    private final ObjectMapper objectMapper;

    @Override
    public Json convert(Style.Metadata source) {
        try {
            return Json.of(objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
