package org.rxjava.common.core.service;

import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.info.LoginInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:23
 * 默认登陆信息服务
 */
public class DefaultLoginInfoServiceImpl implements LoginInfoService {
    /**
     * Token检查
     * 默认不返回登陆信息
     */
    @Override
    public Mono<LoginInfo> checkToken(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            return Mono.empty();
        }
        //token换取信息
        return Mono.empty();
    }
}