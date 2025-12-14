package com.catalyst.style_on.domain.productindex;

import com.catalyst.style_on.domain.productindex.dto.ProductIndexSearchParamsDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductIndexService {
    Flux<ProductIndex> searchProducts(ProductIndexSearchParamsDTO params);
}
