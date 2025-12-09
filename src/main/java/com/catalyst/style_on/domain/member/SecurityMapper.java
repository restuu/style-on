package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.member.dto.MemberResponseDTO;

public class SecurityMapper {
    public static MemberResponseDTO memberToMemberResponseDTO(Member member) {
        return new MemberResponseDTO(
                member.id(),
                member.email(),
                member.gender()
        );
    }
}
