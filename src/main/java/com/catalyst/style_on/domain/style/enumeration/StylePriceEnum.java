package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public enum StylePriceEnum {
    // Kode Harga | Kategori Nama | Rentang Harga (IDR)
    ENTRY_LEVEL("H1", "Entry Level", new BigDecimal("0"), new BigDecimal("4000000")), // Rp 0 – Rp 4 Juta
    MID_LEVEL("H2", "Mid Level", new BigDecimal("4000000"), new BigDecimal("15000000")), // Rp 4 Juta – Rp 15 Juta
    PREMIUM("H3", "Premium", new BigDecimal("15000000"), new BigDecimal("50000000")), // Rp 15 Juta – Rp 50 Juta
    LUXURY("H4", "Luxury", new BigDecimal("50000000"), null), // > Rp 50 Juta
    ;

    private final String code;
    private final String displayName;
    private final BigDecimal min;
    private final BigDecimal max;

    @Override
    public String toString() {
        return displayName;
    }
}
