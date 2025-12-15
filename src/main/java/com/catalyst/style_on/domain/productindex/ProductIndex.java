package com.catalyst.style_on.domain.productindex;

public record ProductIndex(
    Long id,
    String sku,
    String name,
    String brandCode,
    Images images
) {
    public record Images(
            String jpg,
            String webp
    ) {}
}
