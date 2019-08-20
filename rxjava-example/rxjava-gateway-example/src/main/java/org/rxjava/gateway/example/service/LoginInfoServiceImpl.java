package org.rxjava.gateway.example.service;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.DefaultLoginInfoServiceImpl;
import org.rxjava.common.core.service.LoginInfoService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:36
 */
@Service
public class LoginInfoServiceImpl extends DefaultLoginInfoServiceImpl implements LoginInfoService {

    /**
     * Token校验
     */
    @Override
    public Mono<LoginInfo> checkToken(String token, String httpPath,String httpMethod) {
        return Mono.just(new LoginInfo());
    }

}
