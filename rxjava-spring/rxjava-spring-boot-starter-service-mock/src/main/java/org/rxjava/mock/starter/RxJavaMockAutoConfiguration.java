package org.rxjava.mock.starter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.info.LoginInfo;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.common.core.info.UserInfo;
import org.rxjava.common.core.utils.JsonUtils;
import org.rxjava.mock.starter.config.MockProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
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
@Slf4j
public class RxJavaMockAutoConfiguration implements WebFilter {

    private static final String LOGIN_INFO = "loginInfo";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockProperties mockProperties;

    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange serverWebExchange, @NotNull WebFilterChain webFilterChain) {
        log.info("当前请求的是mock，如果是正式环境，请注意排查问题！");
        PathContainer path = serverWebExchange.getRequest().getPath().pathWithinApplication();
        String pathValue = path.value();

        //内部服务不注入任何信息
        if (pathValue.startsWith("/inner/")) {
            return webFilterChain.filter(serverWebExchange);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(mockProperties.getUserId());

        return Mono.just(userInfo)
                .map(a -> {
                    String loginInfoJson;
                    try {
                        loginInfoJson = URLEncoder.encode(objectMapper.writeValueAsString(userInfo),"utf8");
                    } catch (UnsupportedEncodingException | JsonProcessingException e) {
                        e.printStackTrace();
                        throw ErrorMessageException.of("unsupportedEncodingOrJsonException");
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