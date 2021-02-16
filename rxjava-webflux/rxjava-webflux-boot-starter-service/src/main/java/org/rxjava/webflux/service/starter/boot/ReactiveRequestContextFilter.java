package org.rxjava.webflux.service.starter.boot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.rxjava.webflux.core.exception.ErrorMessageException;
import org.rxjava.webflux.core.info.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ObjectMapper objectMapper;

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
                            userInfo = objectMapper.readValue(decodeLoginInfoJson,UserInfo.class);
                        } catch (UnsupportedEncodingException | JsonProcessingException e) {
                            e.printStackTrace();
                            throw ErrorMessageException.of("unsupportedEncodingOrJsonException");
                        }
                    }
                    return userInfo;
                })
                .flatMap(userInfo -> chain
                        .filter(exchange)
                        .subscriberContext(ctx -> {
                            if (ObjectUtils.isNotEmpty(userInfo.getAppId())) {
                                return ctx.put("appId", userInfo.getAppId());
                            }
                            return ctx;
                        })
                );

    }
}
