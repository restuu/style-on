package com.catalyst.style_on.domain.memberstyle.dto;

import java.math.BigDecimal;
import java.util.List;

public record MemberStyleResponseDTO(
        Long id,
        String styleName,
        String styleDescription,
        List<Product> products
) {
    public record Product(
            String modelNumber,
            String name,
            BigDecimal price,
            BigDecimal retailPrice,
            String imageUrl
    ){}
}
