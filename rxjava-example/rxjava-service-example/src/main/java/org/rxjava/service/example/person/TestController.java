package org.rxjava.service.example.person;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.service.example.entity.Example;
import org.rxjava.service.example.repository.ExampleMysqlRepository;
import org.rxjava.service.example.service.ExampleService;
import org.rxjava.webflux.core.annotation.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

}