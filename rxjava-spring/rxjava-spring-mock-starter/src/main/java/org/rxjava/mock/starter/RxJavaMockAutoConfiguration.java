package org.rxjava.mock.starter;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.utils.JsonUtils;
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
@Order(1)
public class RxJavaMockAutoConfiguration implements WebFilter {

    private static final String LOGIN_INFO = "loginInfo";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId("testUserId");

        String loginInfoJson = null;
        try {
            loginInfoJson = URLEncoder.encode(JsonUtils.serialize(loginInfo), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
    }
}