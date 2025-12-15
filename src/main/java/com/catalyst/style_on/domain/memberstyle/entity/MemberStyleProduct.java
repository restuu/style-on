package com.catalyst.style_on.domain.memberstyle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("member_style_product")
public record MemberStyleProduct(
        @Id Long id,
        Long memberStyleId,
        String modelNumber,
        int displayNumber
) {
}
