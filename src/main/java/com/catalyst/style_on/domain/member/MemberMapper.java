package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.auth.dto.AuthRegisterRequestDTO;

public class MemberMapper {

    public static Member authRegisterRequestDTOToMember(AuthRegisterRequestDTO request) {
        return new Member( null, request.name(), request.email(), request.gender());
    }

}
