package com.catalyst.style_on.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    @Order(1) // This chain is checked first
    SecurityWebFilterChain publicEndpointsSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // This security chain only applies to the /scalar/** path
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/scalar/**"))
                .authorizeExchange(exchange -> exchange
                        // All requests to /scalar/** are permitted
                        .anyExchange().permitAll()
                )
                .build();
    }

    @Bean
    @Order(2) // This chain is checked second
    SecurityWebFilterChain apiSecurityFilterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager authenticationManager,
            ServerAuthenticationConverter authenticationConverter,
            ServerAuthenticationEntryPoint authenticationEntryPoint
    ) {

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

        return http
                // This security chain applies to all other paths, especially /api/**
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        // All other /api/** requests must be authenticated
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // Use our custom entry point to handle authentication failures
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // disable csrf for stateless service
                .cors(ServerHttpSecurity.CorsSpec::disable) // allow all
                .build();
    }
}
