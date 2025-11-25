package com.catalyst.style_on.domain.product;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, BigInteger> {
}
