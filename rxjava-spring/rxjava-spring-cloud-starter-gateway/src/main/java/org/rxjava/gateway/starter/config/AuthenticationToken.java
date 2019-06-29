package org.rxjava.gateway.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author happy 2019-06-29 03:31
 * 授权token
 */
@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
    private String token;
    private LoginInfo loginInfo;

    public AuthenticationToken(String token, String requestPath, String methodValue) {
        super(null);
        this.token = token;
    }

    public AuthenticationToken(String token, LoginInfo loginInfo) {
        super(null);
        this.token = token;
        this.loginInfo = loginInfo;
        setAuthenticated(true);
    }

    /**
     * 凭证即为token
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * LoginInfo
     */
    @Override
    public LoginInfo getPrincipal() {
        return this.loginInfo;
    }
}
