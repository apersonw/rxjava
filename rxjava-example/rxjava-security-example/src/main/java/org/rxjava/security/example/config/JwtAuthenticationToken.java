package org.rxjava.security.example.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author happy 2019-06-23 23:10
 * jwt认证
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private UserDetails principal;
    private String jwtToken;

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.jwtToken = token;
    }

    public JwtAuthenticationToken(UserDetails principal, String jwtToken, Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.principal = principal;
        this.jwtToken = jwtToken;
        setAuthenticated(true);
    }

    /**
     * 凭证即为token
     */
    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    /**
     * UserDetails
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }
}
