package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class MemberStyleSummaryDTOToJsonConverter implements Converter<MemberStyleSummaryDTO, Json> {

    private final ObjectMapper objectMapper;

    @Override
    public Json convert(MemberStyleSummaryDTO source) {
        try {
            return Json.of(objectMapper.writeValueAsBytes(source));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Converter<MemberStyleSummaryDTO, U> andThen(Converter<? super Json, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
