package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StyleColorEnum {
    BLACK("hitam", "Hitam"),
    SILVER("silver", "Silver"),
    BROWN("coklat", "Coklat"),
    GOLD("gold", "Gold"),
    WHITE("putih", "Putih"),
    BEIGE("beige", "Beige"),
    GREEN("hijau", "Hijau"),
    BLUE("biru", "Biru"),
    ORANGE("oranye", "Orange"),
    RED("merah", "Merah"),
    YELLOW("kuning", "Kuning"),
    GRAY("abu", "Abu-abu"),
    PURPLE("ungu", "Ungu"),
    PINK("pink", "Pink"),
    ;

    private final String code;
    private final String displayName;

}
