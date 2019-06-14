package org.rxjava.security.dynamically.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author happy 2019-06-13 13:13
 */
@RestController
public class DemoController {

    /**
     * 认证
     */
    @GetMapping("login")
    public Mono<String> login() {
        return Mono.just(UUID.randomUUID().toString());
    }
}