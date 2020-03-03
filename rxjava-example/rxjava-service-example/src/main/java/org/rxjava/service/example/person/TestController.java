package org.rxjava.service.example.person;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.service.example.form.TestEnumForm;
import org.rxjava.service.example.form.TestForm;
import org.rxjava.service.example.type.ImageType;
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
    @GetMapping("checkTest")
    public Mono<String> checkTest(
            LoginInfo loginInfo
    ) {
        return Mono.just("checkOk");
    }

    /**
     * 枚举测试
     */
    @GetMapping("enumTest")
    public Mono<String> testEnum(@Valid TestEnumForm form){
        return Mono.empty();
    }
}