package org.rxjava.security.example;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-12 10:32
 * 授权管理器
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager {
    @Override
    public Mono<AuthorizationDecision> check(Mono authentication, Object object) {
        return Mono.empty();
    }
}