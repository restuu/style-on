package com.catalyst.style_on.domain.product;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public record Product(@Id BigInteger id, String modelNumber, String name) {
    boolean hasId() {
        return id != null;
    }
}
