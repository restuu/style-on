package com.catalyst.style_on.domain.memberstyle;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStyleRepository extends R2dbcRepository<MemberStyle, Long> {
}
