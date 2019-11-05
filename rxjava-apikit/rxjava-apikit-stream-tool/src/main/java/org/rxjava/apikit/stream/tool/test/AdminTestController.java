package org.rxjava.apikit.stream.tool.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 控制器中文名称
 * 此类的描述信息
 */
@RestController
public class AdminTestController {
    /**
     * 空测试
     * 空测试的描述信息
     */
    @GetMapping("voidTest")
    public void voidTest() {

    }

    /**
     * 参数
     * @param first 第一个整型参数
     * @param id 商品Id
     */
    @GetMapping("paramTest")
    public void paramTest(
            int first,
            @PathVariable String id,
            @RequestParam String name,
            List<String> listTest
    ) {
    }
}
