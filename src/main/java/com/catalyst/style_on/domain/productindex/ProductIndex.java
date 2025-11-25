package com.catalyst.style_on.domain.productindex;

import org.springframework.data.annotation.Id;

public record ProductIndex(
    @Id String id
) { }
