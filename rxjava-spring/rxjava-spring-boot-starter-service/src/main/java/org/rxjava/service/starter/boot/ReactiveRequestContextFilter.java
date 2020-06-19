package org.rxjava.service.starter.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.common.core.info.UserInfo;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author happy
 */
@Slf4j
public class ReactiveRequestContextFilter implements WebFilter {
    private static final String LOGIN_INFO = "loginInfo";

    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        return Mono.just(new UserInfo())
                .map(userInfo -> {
                    ServerHttpRequest request = exchange.getRequest();
                    String loginInfoJson = request.getHeaders().getFirst(LOGIN_INFO);
                    if (StringUtils.isNotEmpty(loginInfoJson)) {
                        try {
                            String decodeLoginInfoJson = URLDecoder.decode(loginInfoJson, "utf8");
                            userInfo = JsonUtils.deserialize(decodeLoginInfoJson, UserInfo.class);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            throw ErrorMessageException.of("unsupportedEncodingException");
                        }
                    }
                    return userInfo;
                })
                .flatMap(userInfo -> chain
                        .filter(exchange)
                        .subscriberContext(ctx -> {
                            if (ObjectUtils.isNotEmpty(userInfo.getAppId())) {
                                return ctx.put("settlerId", userInfo.getAppId());
                            }
                            return ctx;
                        })
                );

    }
}
