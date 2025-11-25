package com.catalyst.style_on.domain.productindex;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductIndexRepository {
    Mono<ProductIndex> findBySku(String sku);
}
