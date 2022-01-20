package top.rxjava.apikit.tool.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("test")
    public Mono<Void> test(){
        return Mono.empty();
    }
}
