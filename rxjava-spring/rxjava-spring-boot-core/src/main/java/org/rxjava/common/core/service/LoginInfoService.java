package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 21:52
 */
public interface LoginInfoService {

    /**
     * token校验
     * @param token 密钥
     * @param loginType 登陆类型（PERSON用户，ADMIN管理，THIRD第三方）
     */
    Mono<LoginInfo> checkToken(String token,String loginType);

    /**
     * 权限检查
     * @param userAuthId
     * @param path
     * @param method
     */
    Mono<Boolean> checkPermission(String userAuthId, String path, String method);
}
