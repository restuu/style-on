package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.entity.MemberStyleItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStyleItemRepository extends R2dbcRepository<MemberStyleItem, Long> {
}
