package com.catalyst.style_on.domain.style;

import reactor.core.publisher.Flux;

public interface StyleService {
    Flux<Style> findStylesByTagName(String name);
}
