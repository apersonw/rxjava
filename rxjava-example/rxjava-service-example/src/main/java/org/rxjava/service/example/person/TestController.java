package org.rxjava.service.example.person;

import org.rxjava.common.core.annotation.Check;
import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.example.form.TestForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author happy
 */
@RestController
@RequestMapping("person")
public class TestController {

    /**
     * 路径变量测试
     */
    @Login(false)
    @GetMapping("testPath/{id}")
    public Mono<Integer> testPath(
            @PathVariable String id,
            @Valid TestForm form
    ) {
        return Mono.just(100);
    }

    /**
     * 路径权限检查
     */
    @Check(true)
    @GetMapping("checkTest")
    public Mono<String> checkTest(
    ) {
        return Mono.just("checkOk");
    }
}