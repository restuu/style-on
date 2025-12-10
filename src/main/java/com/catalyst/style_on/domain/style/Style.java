package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.enumeration.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("style")
public record Style(
        @Id Long id,
        String name,
        String imageUrl,
        StyleEnum[] keyStyle,
        StyleMovementEnum movement,
        StyleStrapMaterialEnum strapMaterial,
        StyleColorEnum[] colors,
        StylePriceEnum price,
        String note
) {
}
