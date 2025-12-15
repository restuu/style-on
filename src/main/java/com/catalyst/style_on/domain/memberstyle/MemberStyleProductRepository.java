package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.entity.MemberStyleProduct;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStyleProductRepository extends R2dbcRepository<MemberStyleProduct, Long> {
}
