package com.catalyst.style_on.domain.style.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StyleMovementEnum {

    AUTOMATIC(41, "Automatic"),
    DIGITAL(42, "Digital"),
    QUARTZ_BATTERY(43, "Quartz/Battery"),
    SOLAR_POWERED(44, "Solar Powered"),
    MANUAL_WINDING(45, "Manual Winding"),
    MECHANICAL(46, "Mechanical"),
    DIGITAL_ANALOG(158, "Digital Analog"),
    ANALOG(159, "Analog"),
    KINETIC(1041, "Kinetic"),
    SPRING_DRIVE(1057, "Spring Drive"),
    MOTHERBOARD_CPU(1733, "Garmin Motherboard CPU");


    private final int id;
    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }
}
