package com.catalyst.style_on.domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthClaim extends DefaultClaims implements UserDetails {
    private final Long memberId;

    public AuthClaim(Long memberId, Claims claims) {
        super(claims);

        this.memberId = memberId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return memberId.toString();
    }
}
