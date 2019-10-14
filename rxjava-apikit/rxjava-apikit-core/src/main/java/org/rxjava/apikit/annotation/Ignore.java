package org.rxjava.apikit.annotation;

import java.lang.annotation.*;

/**
 * 忽略生成Api
 * 默认为需要登陆
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
}
