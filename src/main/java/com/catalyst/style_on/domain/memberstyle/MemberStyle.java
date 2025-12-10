package com.catalyst.style_on.domain.memberstyle;

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
        Map<String, Object> summary,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        @MappedCollection(idColumn = "member_style_id")
        Set<MemberStyleItem> items
) {
}
