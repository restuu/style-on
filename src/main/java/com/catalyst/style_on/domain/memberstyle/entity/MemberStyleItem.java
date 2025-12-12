package com.catalyst.style_on.domain.memberstyle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("member_style_item")
public record MemberStyleItem(
        @Id Long id,
        Long memberStyleId,
        Long styleId,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
