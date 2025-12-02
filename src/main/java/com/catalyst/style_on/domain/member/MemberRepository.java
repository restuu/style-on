package com.catalyst.style_on.domain.member;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends R2dbcRepository<Member, Long> {
    Mono<Member> findByEmail(@Param("email") String email);
}
