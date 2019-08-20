package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:23
 * 默认登陆信息服务
 */
public class DefaultLoginInfoServiceImpl implements LoginInfoService {
    /**
     * Token检查
     */
    @Override
    public Mono<LoginInfo> checkToken(String token, String httpPath, String httpMethod) {
        return Mono.empty();
    }
}