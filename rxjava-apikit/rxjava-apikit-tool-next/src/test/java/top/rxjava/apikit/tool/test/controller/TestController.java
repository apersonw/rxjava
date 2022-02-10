package top.rxjava.apikit.tool.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.rxjava.apikit.tool.test.form.TestForm;
import top.rxjava.apikit.tool.test.model.TestModel;

import javax.validation.Valid;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("test")
public class TestController {
    /**
     * 走一波测试
     * @param testForm
     * @param testParamId
     * @return
     */
    @GetMapping("test")
    public Mono<TestModel> test(@Valid TestForm testForm,@RequestParam String testParamId){
        return Mono.empty();
    }
}
