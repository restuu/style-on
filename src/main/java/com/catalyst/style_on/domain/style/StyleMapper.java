package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;

public class StyleMapper {
    public static StyleResponseDTO styleToStyleResponseDTO(Style style) {
        return new StyleResponseDTO(
                style.id(),
                style.name(),
                style.imageUrl(),
                style.note()
        );
    }
}
