package com.catalyst.style_on.domain.memberstyle.dto;

import java.util.List;

public record MemberStyleResponseDTO(
        Long id,
        String styleName,
        List<Product> products
) {
    public record Product(
            String modelNumber,
            String imageUrl
    ){}
}
