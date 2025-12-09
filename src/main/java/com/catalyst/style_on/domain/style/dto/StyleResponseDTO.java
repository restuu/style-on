package com.catalyst.style_on.domain.style.dto;

import org.springframework.data.annotation.Id;

public record StyleResponseDTO(
        @Id Long id,
        String name,
        String imageUrl,
        StyleMetadataDTO metadata
) { }

