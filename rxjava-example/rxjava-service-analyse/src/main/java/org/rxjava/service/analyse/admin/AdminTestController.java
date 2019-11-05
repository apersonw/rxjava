package org.rxjava.service.analyse.admin;

import org.rxjava.service.analyse.form.BaseParamTestForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminTestController {
    /**
     * 空测试
     */
    @GetMapping("voidTest")
    public void voidTest() {

    }

    /**
     * 参数
     */
    @GetMapping("paramTest")
    public void paramTest(
            int first,
            @PathVariable String id,
            @RequestParam String name,
            List<String> listTest,
            @Valid BaseParamTestForm form
    ) {
    }
}
