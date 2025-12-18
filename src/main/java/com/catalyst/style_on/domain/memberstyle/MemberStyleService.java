package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleImagineStyleDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import org.springframework.core.io.ByteArrayResource;
import reactor.core.publisher.Mono;

public interface MemberStyleService {
    Mono<MemberStyleResponseDTO> submitMemberStyle(Long memberId, MemberStyleSubmitRequestDTO req);
    Mono<ByteArrayResource> imagineMemberStyle(MemberStyleImagineStyleDTO params);
}
