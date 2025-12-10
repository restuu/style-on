package com.catalyst.style_on.domain.memberstyle.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MemberStyleSubmitRequestDTO(
        @NotNull @NotEmpty List<Long> styleIds
) {
}
