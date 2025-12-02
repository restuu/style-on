package com.catalyst.style_on.domain.member;

import reactor.core.publisher.Mono;

public interface MemberService {
    Mono<Member> findByEmail(String email);
}
