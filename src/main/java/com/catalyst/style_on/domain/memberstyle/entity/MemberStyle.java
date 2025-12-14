package com.catalyst.style_on.domain.memberstyle.entity;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

@Table("member_style")
public record MemberStyle(
        @Id Long id,
        Long memberId,
        String name,
        MemberStyleSummaryDTO summary,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
