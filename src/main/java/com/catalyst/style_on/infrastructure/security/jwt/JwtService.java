package com.catalyst.style_on.infrastructure.security.jwt;

import com.catalyst.style_on.domain.auth.AuthCustomClaim;
import com.catalyst.style_on.domain.auth.AuthTokenProvider;
import com.catalyst.style_on.domain.auth.AuthClaim;
import com.catalyst.style_on.domain.member.MemberService;
import com.catalyst.style_on.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService implements AuthTokenProvider {

    private final JwtConfig config;
    private final MemberService memberService;

    @Override
    public Mono<String> generateToken(AuthCustomClaim claim) {
        return Mono.create(sink -> {
            try {
                sink.success(generateJwtToken(claim));
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    public Mono<AuthClaim> parseToken(String token) {
        return Mono.<Claims>create(sink -> {
                    try {
                        sink.success(parseJwtClaims(token));
                    } catch (Exception e) {
                        sink.error(e);
                    }
                })
                .map(claims -> new AuthClaim(Long.valueOf(claims.getSubject()), claims));
    }

    public Boolean isValidClaims(Claims claims) {
        return !isExpiredClaims(claims);
    }

    private Boolean isExpiredClaims(Claims claims) {
        Instant now = Instant.now();

        return claims.getExpiration().before(Date.from(now));
    }


    private String generateJwtToken(AuthCustomClaim claim) {
        Instant now = Instant.now();

        return Jwts.builder()
                .claims()
                .subject(claim.memberId().toString())
                .and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(config.getExpiryTime())))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new JwtAuthException(e.getMessage());
        }
    }

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(config.getSecretKey());

        return Keys.hmacShaKeyFor(bytes);
    }
}
