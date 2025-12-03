package com.catalyst.style_on.domain.auth.dto;

import com.catalyst.style_on.domain.shared.gender.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterRequestDTO(
        @NotBlank @Email String email,
        @NotNull Gender gender
) {
}
