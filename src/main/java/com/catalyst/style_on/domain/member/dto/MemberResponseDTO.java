package com.catalyst.style_on.domain.member.dto;

import com.catalyst.style_on.domain.shared.gender.Gender;
import jakarta.validation.constraints.Email;

public record MemberResponseDTO(
        Long id,
        @Email String email,
        Gender gender
) {
}
