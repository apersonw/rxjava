package org.rxjava.service.analyse.admin;

import org.rxjava.service.analyse.form.BaseParamTestForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理测试类
 * 这是管理测试类的描述
 */
@RestController
public class AdminTestController {
    /**
     * 空测试
     */
    @GetMapping("voidTest")
    public void voidTest() {

    }

    /**
     * 参数类注释
     *
     * @param first
     * @param id       第二个
     * @param name     第三个
     * @param listTest 第四个
     * @param form     第五个
     * @return 字符串注释
     */
    @GetMapping("paramTest")
    public Mono<String> paramTest(
            int first,
            @PathVariable String id,
            @RequestParam String name,
            List<String> listTest,
            @Valid BaseParamTestForm form
    ) {
        return Mono.empty();
    }
}
