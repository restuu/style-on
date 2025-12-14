package com.catalyst.style_on.domain.productindex.dto;

import com.catalyst.style_on.domain.shared.price.Price;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
@EqualsAndHashCode
@Getter
public class ProductIndexSearchParamsDTO {
    private final Map<Integer, Integer> keyStyle;
    private final Map<Integer, Integer> movement;
    private final Map<String, Integer> strapMaterial;
    private final Map<String, Integer> color;
    private final Map<Price, Integer> price;
}
