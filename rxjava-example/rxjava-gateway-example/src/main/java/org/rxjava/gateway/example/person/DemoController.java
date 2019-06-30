package org.rxjava.gateway.example.person;

import org.rxjava.common.core.annotation.Check;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 16:18
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Check
    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("hello boy");
    }
}