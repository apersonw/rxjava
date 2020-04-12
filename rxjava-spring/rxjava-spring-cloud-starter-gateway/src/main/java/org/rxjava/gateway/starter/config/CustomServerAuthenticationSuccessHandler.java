package org.rxjava.gateway.starter.config;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.exception.ErrorMessageException;
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
     * 鉴权成功后，将loginInfo注入请求头中
     */
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        WebFilterChain chain = webFilterExchange.getChain();

        ServerHttpRequest request = exchange.getRequest();
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        LoginInfo loginInfo = authenticationToken.getLoginInfo();

        if (ObjectUtils.isEmpty(loginInfo)) {
            return chain.filter(exchange);
        }

        return Mono.just(loginInfo)
                .map(a -> {
                    String loginInfoJson;
                    try {
                        loginInfoJson = URLEncoder.encode(JsonUtils.serialize(loginInfo), "utf8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        throw ErrorMessageException.of("不支持的编码异常");
                    }
                    return loginInfoJson;
                })
                .flatMap(loginInfoJson -> {
                    if (StringUtils.isEmpty(loginInfoJson)) {
                        return chain.filter(exchange);
                    }
                    ServerHttpRequest host = request
                            .mutate()
                            .header("loginInfo", loginInfoJson)
                            .build();
                    ServerWebExchange build = exchange
                            .mutate()
                            .request(host)
                            .build();

                    return chain.filter(build);
                });

    }
}