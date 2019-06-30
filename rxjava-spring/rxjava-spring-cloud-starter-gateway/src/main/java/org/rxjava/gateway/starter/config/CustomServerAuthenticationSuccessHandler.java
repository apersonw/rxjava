package org.rxjava.gateway.starter.config;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author happy 2019-06-29 16:31
 */
public class CustomServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    /**
     * 将登陆信息注入请求头
     */
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        LoginInfo loginInfo = authenticationToken.getLoginInfo();
        return Mono.justOrEmpty(loginInfo)
                .map(l -> {
                    String loginInfoJson = null;
                    try {
                        loginInfoJson = URLEncoder.encode(JsonUtils.serialize(loginInfo), "utf8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return loginInfoJson;
                })
                .map(loginInfoJson -> {
                    ServerWebExchange exchange = webFilterExchange.getExchange();
                    WebFilterChain chain = webFilterExchange.getChain();

                    ServerHttpRequest request = exchange.getRequest();
                    ServerHttpRequest host = request
                            .mutate()
                            .header("loginInfo", loginInfoJson)
                            .build();
                    ServerWebExchange build = exchange
                            .mutate()
                            .request(host)
                            .build();
                    return chain.filter(build);
                })
                .then();
    }
}