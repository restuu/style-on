package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.member.dto.MemberResponseDTO;
import com.catalyst.style_on.exception.DuplicateDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Mono<MemberResponseDTO> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(SecurityMapper::memberToMemberResponseDTO);
    }

    @Override
    public Mono<MemberResponseDTO> findById(Long id) {
        return memberRepository.findById(id)
                .map(SecurityMapper::memberToMemberResponseDTO);
    }

    @Override
    public Mono<Boolean> registerNewMember(Member member) {
        return memberRepository.findByEmail(member.email())
                .doOnNext(x -> log.info("{} found member", x))
                .flatMap(existingMember -> Mono.error(new DuplicateDataException("Member already exists")))
                .switchIfEmpty(Mono.defer(() -> memberRepository.save(member)))
                .thenReturn(Boolean.TRUE);
    }
}
