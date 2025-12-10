package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StyleStrapMaterialEnum {

    STAINLESS_STEEL("steel","Stainless Steel"),
    LEATHER("leather",  "Leather"),
    CANVAS("canvas","Canvas"),
    RUBBER("rubber","Rubber"),
    GOLD_METAL("gold-metal","Gold/Metal"),
    CROCODILE_LEATHER("crocodile-leather","Crocodile Leather"),
    PATINA_LEATHER("patina-leather",  "Patina Leather"),
    TITANIUM_RUBBER("titanium-rubber","Titanium/Rubber");


    private final String code;
    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }
}
