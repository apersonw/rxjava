package top.rxjava.third.qiniu.file;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author happy 2019-06-04 10:22
 * 开启文件
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RxQiniuConfiguration.class})
public @interface EnableQiniuFile {
}