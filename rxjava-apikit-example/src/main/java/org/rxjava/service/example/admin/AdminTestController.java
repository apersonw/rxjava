package org.rxjava.service.example.admin;

import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.example.form.TestForm;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author happy 2019-05-18 23:11
 * 注释说明
 */
@RestController
@RequestMapping("admin")
public class AdminTestController {

    /**
     * 路径变量测试
     */
    @Login(false)
    @GetMapping("testPath/{id}")
    public int testPath(
            @PathVariable String id,
            @Valid TestForm form
    ) {
        return 1;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Class<?> adminTestController = systemClassLoader.loadClass("org.rxjava.service.example.admin.AdminTestController");
        Package aPackage = adminTestController.getPackage();
        String name1 = adminTestController.getName();
        String name2 = adminTestController.getSimpleName();

        RestController annotation = AnnotationUtils.getAnnotation(adminTestController, RestController.class);
        RequestMapping requestMapping = AnnotationUtils.getAnnotation(adminTestController, RequestMapping.class);
        Method[] methods = adminTestController.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            System.out.println(name);
            AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
            if (mergedAnnotationAttributes != null) {
                String[] paths = mergedAnnotationAttributes.getStringArray("path");
                String[] values = mergedAnnotationAttributes.getStringArray("value");
                RequestMethod[] requestMethods = (RequestMethod[]) mergedAnnotationAttributes.get("method");
                System.out.println(Arrays.toString(paths));
                System.out.println(Arrays.toString(values));
                System.out.println(Arrays.toString(requestMethods));
                Class genericReturnType1 = (Class) method.getGenericReturnType();

                System.out.println(genericReturnType1.isPrimitive());
            }
        }
    }
}
