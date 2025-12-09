package com.catalyst.style_on.infrastructure.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@Configuration
@SecurityScheme(
        name = JWT_BEARER_AUTH,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "Bearer",
        scheme = "bearer"
)
public class SecurityOpenApi {
}
