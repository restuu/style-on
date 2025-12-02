package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.style.Style;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
@RequiredArgsConstructor
public class JsonToStyleMetadataConverter implements Converter<Json, Style.Metadata> {

    private final ObjectMapper objectMapper;

    @Override
    public Style.Metadata convert(Json source) {
        try {
            return objectMapper.readValue(source.asString(), Style.Metadata.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
