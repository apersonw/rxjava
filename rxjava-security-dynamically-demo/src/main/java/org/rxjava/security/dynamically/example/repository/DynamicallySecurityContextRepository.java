package org.rxjava.security.dynamically.example.repository;

import org.apache.commons.lang3.StringUtils;
import org.rxjava.security.dynamically.example.config.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-13 13:22
 */
@Component
public class DynamicallySecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotEmpty(token)) {
            Authentication auth = new TestingAuthenticationToken(token, token);
            return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
        } else {
            return Mono.empty();
        }
    }
}
