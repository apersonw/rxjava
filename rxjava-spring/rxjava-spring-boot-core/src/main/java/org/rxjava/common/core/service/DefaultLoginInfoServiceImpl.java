package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
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
    public Mono<LoginInfo> checkToken(String token, String loginType) {
        return Mono.empty();
    }

    /**
     * 访问权限校验(默认校验未通过)
     */
    @Override
    public Mono<Boolean> checkPermission(String userAuthId, String path, String method) {
        return Mono.just(false);
    }
}