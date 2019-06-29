package org.rxjava.common.core.service;

import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:23
 * 登陆信息服务接口
 */
@Service
public interface LoginInfoService {
    /**
     * 权限检查
     */
    Mono<LoginInfo> checkPermission(String token, String requestPath, String methodValue);
}
