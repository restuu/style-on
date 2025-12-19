package com.catalyst.style_on.domain.productindex;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductIndex {

    private final Long id;
    private final String sku;
    private final String name;
    private final String brandCode;
    private final Images images;
    private final Price price;

    @Data
    @Builder
    public static class Images {
        private final String jpg;
        private final String webp;
    }

    @Data
    @Builder
    public static class Price {
        private final String priceIdr;
        private final String retailPriceIdr;
        private final BigDecimal price;
        private final BigDecimal retailPrice;
    }

}
