package org.rxjava.service.example.person;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TestController {

    /**
     * 路径变量测试
     */
    @GetMapping("path/{id}")
    public Mono<TestModel> testPath(
            @PathVariable String id,
            @Valid TestForm form
    ) {
        TestModel testModel = new TestModel();
        testModel.setId("haha");
        testModel.setName("我是testModel");
        log.info("TestController id:{}",id);
        log.info("TestController objectId:{}",form.getObjectId());
        log.info("TestController testPath:{}",testModel);
        return Mono.just(testModel);
    }
}