package org.rxjava.service.example.person;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.service.example.entity.Example;
import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.form.TestMultForm;
import org.rxjava.service.example.model.TestModel;
import org.rxjava.service.example.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author happy
 */
@RestController
@RequestMapping("mergeTestPath")
@Slf4j
public class TestController {

    @Autowired
    private ExampleRepository exampleRepository;

    /**
     * 路径变量测试
     */
    @GetMapping("path/{id}")
    public Mono<TestModel> testPath(
            @PathVariable String id,
            @Valid TestForm form,
            @Valid TestMultForm multForm
    ) {
        TestModel testModel = new TestModel();
        testModel.setId("haha");
        testModel.setName("我是testModel");
        log.info("TestController id:{}", id);
        log.info("TestController objectId:{}", form.getObjectId());
        log.info("TestController testPath:{}", testModel);
        Example example = new Example();
        example.setName("我是测试人员");
        return exampleRepository.save(example).thenReturn(testModel);
    }
}