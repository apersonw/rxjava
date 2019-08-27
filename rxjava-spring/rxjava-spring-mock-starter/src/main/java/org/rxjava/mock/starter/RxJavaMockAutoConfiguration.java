package org.rxjava.mock.starter;

import org.rxjava.common.core.utils.JsonUtils;
import org.rxjava.mock.starter.loginInfo.MockLoginInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author happy 2019-07-03 11:37
 */
@Configuration
public class RxJavaMockAutoConfiguration implements WebFilter {

    private static final String LOGIN_INFO = "loginInfo";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        MockLoginInfo loginInfo = new MockLoginInfo();
        loginInfo.setUserId("testUserId");

        String loginInfoJson = null;
        try {
            loginInfoJson = URLEncoder.encode(JsonUtils.serialize(loginInfo), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        serverWebExchange.getRequest().getHeaders().add(LOGIN_INFO, loginInfoJson);
        return webFilterChain.filter(serverWebExchange);
    }
}