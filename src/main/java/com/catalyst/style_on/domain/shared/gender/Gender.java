package com.catalyst.style_on.domain.shared.gender;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE,
    FEMALE;

    @Override
    @JsonValue
    public String toString() {
        return switch (this) {
            case MALE -> "male";
            case FEMALE -> "female";
        };
    }

    @JsonCreator // This annotation is helpful for some deserialization scenarios
    public static Gender fromString(String value) {
        if (value == null) {
            return null;
        }
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) return gender;
        }
        throw new IllegalArgumentException("Invalid gender value: " + value + ". Must be 'male' or 'female'.");
    }
}
