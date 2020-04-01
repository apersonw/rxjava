package org.rxjava.service.example.person;

import org.rxjava.service.example.RxServiceExampleApplication;
import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
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
@RequestMapping("mergeTestPath")
public class TestController {
    @Autowired
    private ReactiveRedisTemplate<String,String> reactiveRedisTemplate;

    /**
     * 路径变量测试
     */
    @GetMapping("path/{id}")
    public Mono<TestModel> testPath(
            @PathVariable String id,
            @Valid TestForm form
    ) {
        System.out.println(id);
        return Mono.just(new TestModel());
    }
}