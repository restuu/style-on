package com.catalyst.style_on.domain.style;

import com.catalyst.style_on.domain.style.enumeration.StyleBudgetEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleColorPreferenceEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("style")
public record Style(
        @Id Long id,
        String name,
        String imageUrl,
        Metadata metadata
) {
    public record Metadata(
            Double styleBusiness,
            Double styleMinimalist,
            StyleBudgetEnum budgetTier,
            String preferenceAccessories,
            StyleColorPreferenceEnum colorPreference
    ) {
    }
}
