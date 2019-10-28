package org.rxjava.service.analyse.person;

import org.rxjava.apikit.tool.info.TestInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author happy 2019/10/26 22:18
 */
@RestController
@RequestMapping("/admin/")
public class TestController {
    @GetMapping("aslkasdfsf")
    public Mono<TestInfo<String>> test(@PathVariable String id) {
        return Mono.empty();
    }
}
