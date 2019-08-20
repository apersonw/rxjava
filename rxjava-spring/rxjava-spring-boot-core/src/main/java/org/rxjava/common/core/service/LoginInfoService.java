package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 21:52
 */
public interface LoginInfoService {

    /**
     * token校验
     * @param token 密钥
     * @param httpPath 请求路径
     * @param httpMethod 请求方法
     */
    Mono<LoginInfo> checkToken(String token, String httpPath, String httpMethod);
}
