package org.rxjava.mock.starter;

import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.common.core.utils.JsonUtils;
import org.rxjava.mock.starter.config.MockProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author happy 2019-07-03 11:37
 * Mock自动配置信息，此过滤器注入用户登陆信息
 */
@Configuration
@EnableConfigurationProperties({MockProperties.class})
@Order(1)
public class RxJavaMockAutoConfiguration implements WebFilter {

    private static final String LOGIN_INFO = "loginInfo";

    @Autowired
    private MockProperties mockProperties;

    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange serverWebExchange, @NotNull WebFilterChain webFilterChain) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(mockProperties.getUserId());

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
                    ServerHttpRequest request = serverWebExchange.getRequest();
                    ServerHttpRequest host = request
                            .mutate()
                            .header(LOGIN_INFO, Objects.requireNonNull(loginInfoJson))
                            .build();
                    ServerWebExchange build = serverWebExchange
                            .mutate()
                            .request(host)
                            .build();

                    return webFilterChain.filter(build);
                });
    }
}