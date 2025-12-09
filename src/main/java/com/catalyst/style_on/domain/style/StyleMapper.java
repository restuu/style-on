package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.dto.StyleMetadataDTO;
import com.catalyst.style_on.domain.style.dto.StyleResponseDTO;

public class StyleMapper {
    public static StyleResponseDTO styleToStyleResponseDTO(Style style) {
        return new  StyleResponseDTO(
                style.id(),
                style.name(),
                style.imageUrl(),
                new StyleMetadataDTO(
                        style.metadata().styleBusiness(),
                        style.metadata().styleMinimalist(),
                        style.metadata().budgetTier(),
                        style.metadata().preferenceAccessories(),
                        style.metadata().colorPreference()
                )
        );
    }
}
