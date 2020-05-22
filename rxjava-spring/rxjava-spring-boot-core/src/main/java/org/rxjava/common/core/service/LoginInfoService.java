package org.rxjava.common.core.service;

import org.rxjava.common.core.info.LoginInfo;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 21:52
 */
public interface LoginInfoService {

    /**
     * token校验
     */
    Mono<LoginInfo> checkToken(ServerWebExchange serverWebExchange);
}
