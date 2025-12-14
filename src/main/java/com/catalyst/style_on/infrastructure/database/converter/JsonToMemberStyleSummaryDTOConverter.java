package com.catalyst.style_on.infrastructure.database.converter;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class JsonToMemberStyleSummaryDTOConverter implements Converter<Json, MemberStyleSummaryDTO> {

    private final ObjectMapper objectMapper;

    @Override
    public MemberStyleSummaryDTO convert(Json source) {
        try {
            return objectMapper.readValue(source.asString(), MemberStyleSummaryDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <U> Converter<Json, U> andThen(Converter<? super MemberStyleSummaryDTO, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
