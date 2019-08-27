package org.rxjava.service.example.inner;

import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 22:39
 */
@RestController
@RequestMapping("inner")
public class InnerLoginInfoController {

    @GetMapping("hello")
    public Mono<String> hello(LoginInfo loginInfo) {
        return Mono.just("hello boy");
    }
}