package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.catalyst.style_on.domain.productindex.dto.ProductIndexSearchParamsDTO;
import com.catalyst.style_on.domain.shared.price.Price;
import com.catalyst.style_on.domain.style.enumeration.StyleColorEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleMovementEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleStrapMaterialEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberStyleMapper {
    public static ProductIndexSearchParamsDTO memberStyleSummaryDTOToProductIndexSearchParamsDTO(MemberStyleSummaryDTO summary) {
        Map<Integer, Integer> keyStyle =   summary.getKeyStyle()
                .keySet().stream()
                .collect(Collectors.toMap(
                        StyleEnum::getId,
                        summary.getKeyStyle()::get
                ));

        Map<Price, Integer> price = summary.getPrice().keySet()
                .stream()
                .collect(Collectors.toMap(
                        key -> new Price(key.getMin(), key.getMax()),
                        summary.getPrice()::get
                ));

        Map<String, Integer> color = summary.getColor().keySet()
                .stream()
                .collect(Collectors.toMap(
                        StyleColorEnum::getCode,
                        summary.getColor()::get
                ));

        Map<String, Integer> strapMaterial = summary.getStrapMaterial().keySet()
                .stream()
                .collect(Collectors.toMap(
                        StyleStrapMaterialEnum::getCode,
                        summary.getStrapMaterial()::get
                ));

        Map<Integer, Integer> movement = summary.getMovement().keySet()
                .stream()
                .collect(Collectors.toMap(
                        StyleMovementEnum::getId,
                        summary.getMovement()::get
                ));


        return ProductIndexSearchParamsDTO.builder()
                .keyStyle(keyStyle)
                .price(price)
                .color(color)
                .strapMaterial(strapMaterial)
                .movement(movement)
                .build();
    }
}
