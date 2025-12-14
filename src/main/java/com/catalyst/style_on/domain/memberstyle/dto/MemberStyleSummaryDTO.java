package com.catalyst.style_on.domain.memberstyle.dto;

import com.catalyst.style_on.domain.style.enumeration.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class MemberStyleSummaryDTO {
    private Map<StyleEnum, Integer> keyStyle;
    private Map<StyleMovementEnum, Integer> movement;
    private Map<StyleStrapMaterialEnum, Integer> strapMaterial;
    private Map<StyleColorEnum, Integer> color;
    private Map<StylePriceEnum, Integer> price;
}
