package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum StyleBudgetEnum {
    LOW(BigDecimal.ZERO, BigDecimal.valueOf(1000_000)),
    LOW_MID(BigDecimal.valueOf(1000_001), BigDecimal.valueOf(3000_000)),
    MID(BigDecimal.valueOf(3000_001), BigDecimal.valueOf(5000_000)),
    HIGH(BigDecimal.valueOf(5000_001), BigDecimal.ZERO);

    private final BigDecimal min;
    private final BigDecimal max;

    StyleBudgetEnum(BigDecimal min, BigDecimal max) {
        this.min = min;
        this.max = max;
    }
}
