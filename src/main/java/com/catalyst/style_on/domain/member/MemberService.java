package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.member.dto.MemberResponseDTO;
import reactor.core.publisher.Mono;

public interface MemberService {
    Mono<MemberResponseDTO> findByEmail(String email);
    Mono<MemberResponseDTO> findById(Long id);
    Mono<Boolean> registerNewMember(Member member);
}
