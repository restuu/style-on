package com.catalyst.style_on.domain.style.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StyleAttibuteEnum {
    STYLE_CASUAL(Double.class),
    STYLE_BUSINESS(Double.class),
    STYLE_MINIMALIST(Double.class),
    STYLE_STREETWEAR(Double.class),
    STYLE_FORMAL(Double.class),
    STYLE_LUXURY(Double.class),
    STYLE_SPORTY(Double.class),
    STYLE_UTILITY(Double.class),
    BUDGET_TIER(StyleBudgetEnum.class),
    PREFERENCE_ACCESSORIES(String.class),
    COLOR_PREFERENCE(StyleColorPreferenceEnum.class); // Or a specific Enum like ColorPreferenceEnum.class

    private final Class<?> valueType;

    StyleAttibuteEnum(Class<?> valueType) {
        this.valueType = valueType;
    }

    @JsonCreator // This annotation is helpful for some deserialization scenarios
    public static StyleAttibuteEnum fromString(String value) {
        if (value == null) {
            return null;
        }
        for (StyleAttibuteEnum attr : StyleAttibuteEnum.values()) {
            if (attr.name().equalsIgnoreCase(value)) return attr;
        }
        throw new IllegalArgumentException("Invalid style attribute value: " + value);
    }


    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
