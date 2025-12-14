package com.catalyst.style_on.domain.shared.price;

import java.math.BigDecimal;

public record Price(
        BigDecimal min,
        BigDecimal max
) {
}
