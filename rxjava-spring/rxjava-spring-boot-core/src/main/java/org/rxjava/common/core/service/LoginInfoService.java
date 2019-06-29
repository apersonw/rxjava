package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 21:52
 */
public interface LoginInfoService {

    Mono<LoginInfo> checkToken(String token);

    Mono<Boolean> checkPermission(String userAuthId, String path, String method);
}
