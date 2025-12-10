package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.enumeration.StyleAttibuteEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleBudgetEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleColorPreferenceEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Table("style")
public record Style(
        @Id Long id,
        String name,
        String imageUrl,
        Map<StyleAttibuteEnum, Object> metadata
) {
}
