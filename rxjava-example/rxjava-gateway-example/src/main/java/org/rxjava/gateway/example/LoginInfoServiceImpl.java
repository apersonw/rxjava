package org.rxjava.gateway.example;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.LoginInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    @Override
    public Mono<LoginInfo> checkToken(ServerWebExchange serverWebExchange) {
        return Mono.just(new LoginInfo());
    }
}
