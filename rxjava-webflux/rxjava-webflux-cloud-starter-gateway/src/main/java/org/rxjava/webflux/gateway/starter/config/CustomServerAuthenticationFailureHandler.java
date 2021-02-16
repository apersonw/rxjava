package org.rxjava.webflux.gateway.starter.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 16:31
 */
public class CustomServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    /**
     * 鉴权失败，则不注入loginInfo，由微服务判断是否需要loginInfo
     */
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        WebFilterChain chain = webFilterExchange.getChain();
        return chain.filter(exchange);
    }
}