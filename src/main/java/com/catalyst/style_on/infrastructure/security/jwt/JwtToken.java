package com.catalyst.style_on.infrastructure.security.jwt;

import com.catalyst.style_on.domain.auth.AuthClaim;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
public class JwtToken extends AbstractAuthenticationToken {

    private final String token;
    private final AuthClaim principal;

    JwtToken(String jwtToken, AuthClaim userDetails) {
        super(userDetails.getAuthorities());

        token = jwtToken;
        principal = userDetails;
    }

    public Authentication withAuthenticated(boolean authenticated){
        Authentication authentication = new JwtToken(token, principal);
        authentication.setAuthenticated(authenticated);

        return authentication;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JwtToken test)) {
            return false;
        }
        if (this.getToken() == null && test.getToken() != null) {
            return false;
        }
        if (this.getToken() != null && !this.getToken().equals(test.getToken())) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int code = super.hashCode();
        if (this.getToken() != null) {
            code ^= this.getToken().hashCode();
        }
        return code;
    }

}
