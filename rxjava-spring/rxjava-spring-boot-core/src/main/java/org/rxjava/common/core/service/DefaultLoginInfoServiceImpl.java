package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
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
        return Mono.empty();
    }
}