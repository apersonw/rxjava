package org.rxjava.service.example.person;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.example.entity.Example;
import org.rxjava.service.example.entity.ExampleMysql;
import org.rxjava.service.example.form.TestBodyForm;
import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.form.TestMultForm;
import org.rxjava.service.example.model.TestModel;
import org.rxjava.service.example.repository.ExampleMysqlRepository;
import org.rxjava.service.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;

// import org.rxjava.api.person.example.TestApi;

/**
 * @author happy
 */
@RestController
@RequestMapping("mergeTestPath")
@Slf4j
public class TestController {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private ExampleMysqlRepository exampleMysqlRepository;

    @Login(false)
    @GetMapping("testDemo")
    public Mono<Example> testDemo() {
        return exampleService.testDemo();
    }

    @Login(false)
    @GetMapping("testMysql")
    public Mono<ExampleMysql> testMysql() {
        ExampleMysql s = new ExampleMysql();
        s.setId(33L);
        s.setName("测试");
        s.setCreateDate(LocalDateTime.now());
        return exampleMysqlRepository.save(s);
    }

    /**
     * 路径变量测试
     */
    @Login(false)
    @PostMapping("path/{id}")
    public Mono<TestModel> testPath(
            @PathVariable String id,
            @RequestParam String testId,
            @Valid @RequestBody TestBodyForm testBodyForm,
            @Valid TestForm form,
            @Valid TestMultForm multForm
    ) {
//        TestModel testModel = new TestModel();
//        testModel.setId("haha");
//        testModel.setName("我是testModel");
//        log.info("TestController id:{}", id);
//        log.info("TestController objectId:{}", form.getObjectId());
//        log.info("TestController testPath:{}", testModel);
//        Example example = new Example();
//        example.setName("我是测试人员");
//        return exampleRepository.save(example).thenReturn(testModel);
        TestModel testModel = new TestModel();
        testModel.setName("hi");
        return Mono.just(testModel);
    }

    @Login(false)
    @PostMapping("filePart")
    public Mono<TestModel> testRequestPart(
            @RequestPart("file") FilePart filePart
    ) {
        TestModel data = new TestModel();
        data.setName("testModel");
        FileSystemResource fileSystemResource = new FileSystemResource(new File(""));

        return Mono.just(data);
    }

    @GetMapping("testMono")
    public Mono<ExampleMysql> testMono(){
        return exampleMysqlRepository.findOneById();
    }

}