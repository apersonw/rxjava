package org.rxjava.gateway.starter.config;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.LoginInfoService;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:31
 */
public class DefaultLoginInfoServiceImpl implements LoginInfoService {

    @Override
    public Mono<LoginInfo> checkPermission(String token, String requestPath, String methodValue) {
        return Mono.empty();
    }
}
