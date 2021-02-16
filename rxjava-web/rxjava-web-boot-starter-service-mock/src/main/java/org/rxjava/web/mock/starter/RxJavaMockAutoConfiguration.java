package org.rxjava.web.mock.starter;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.web.mock.starter.config.MockProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Mock自动配置信息，此过滤器注入用户登陆信息
 */
@Configuration
@EnableConfigurationProperties({MockProperties.class})
@Order(1)
@Slf4j
public class RxJavaMockAutoConfiguration implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return null;
    }
}