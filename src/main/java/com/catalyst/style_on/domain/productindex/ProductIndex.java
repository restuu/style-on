package com.catalyst.style_on.domain.productindex;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


public record ProductIndex(
         Long id,
         String sku,
         String name,
         String brandCode,
         Images images,
         ProductPrice price
) {

    public record Images(
             String jpg,
             String webp
    ) {
    }

    public record ProductPrice(
             String priceIdr,
             String retailPriceIdr,
             BigDecimal price,
             BigDecimal retailPrice
    ) {

    }

}
