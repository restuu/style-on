package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import reactor.core.publisher.Mono;

public interface MemberStyleService {
    Mono<MemberStyleResponseDTO> submitMemberStyle(Long memberId, MemberStyleSubmitRequestDTO req);
}
