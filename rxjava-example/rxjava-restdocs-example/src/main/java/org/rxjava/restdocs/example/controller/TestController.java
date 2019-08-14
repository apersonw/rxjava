package org.rxjava.restdocs.example.controller;

import org.rxjava.common.core.annotation.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-08-05 02:00
 */
@RestController
public class TestController {

    @Login(false)
    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("Hello World");
    }
}