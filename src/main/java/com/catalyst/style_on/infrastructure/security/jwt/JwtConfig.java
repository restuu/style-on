package com.catalyst.style_on.infrastructure.security.jwt;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
@Validated
@Getter
@Setter
public class JwtConfig {
    @NotBlank
    private String secretKey;
    @NotNull
    private Duration expiryTime;
}
