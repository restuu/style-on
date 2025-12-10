package com.catalyst.style_on.domain.style.dto;

import com.catalyst.style_on.domain.style.enumeration.StyleAttibuteEnum;
import org.springframework.data.annotation.Id;

import java.util.Map;

public record StyleResponseDTO(
        @Id Long id,
        String name,
        String imageUrl,
        Map<StyleAttibuteEnum, Object> metadata
) { }

