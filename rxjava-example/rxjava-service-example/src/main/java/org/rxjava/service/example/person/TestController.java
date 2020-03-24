package org.rxjava.service.example.person;

import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.model.TestModel;
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

    /**
     * 路径变量测试
     */
    @GetMapping("path/{id}")
    public Mono<TestModel> testPath(
            @PathVariable String id,
            @Valid TestForm form
    ) {
        return Mono.just(new TestModel());
    }
}