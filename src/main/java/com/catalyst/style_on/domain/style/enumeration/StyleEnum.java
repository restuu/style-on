package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO: make this into db table
@Getter
@RequiredArgsConstructor
public enum StyleEnum {

    SMART_WATCH(1666, "Smart Watch"),
    DIGITAL(1662, "Digital"),
    ANALOG_DIGITAL(1663, "Analog Digital"),
    LUXURY(1664, "Luxury"),
    FASHION(1665, "Fashion"),
    DIVER(1657,"Diver"),
    DRESS(1656,"Dress"),
    FIELD(1661,"Field"),
    SPORT(1659,"Sport"),
    PILOT(1658,"Pilot"),
    CASUAL(1660,"Casual");

    private final int id;
    private final String displayName;

    @Override
    public  String toString() {
        return displayName;
    }
}
