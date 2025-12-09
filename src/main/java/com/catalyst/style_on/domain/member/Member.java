package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.shared.gender.Gender;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("member")
public record Member(
        @Id
        Long id,
        String name,
        @Email
        String email,
        Gender gender
) {
}
