package com.catalyst.style_on.domain.style.dto;

import com.catalyst.style_on.domain.style.enumeration.StyleBudgetEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleColorPreferenceEnum;

public record StyleMetadataDTO(
        Double styleBusiness,
        Double styleMinimalist,
        StyleBudgetEnum budgetTier,
        String preferenceAccessories,
        StyleColorPreferenceEnum colorPreference
) { }
