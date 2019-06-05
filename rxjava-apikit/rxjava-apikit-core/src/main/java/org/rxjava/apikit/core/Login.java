package org.rxjava.apikit.core;

import java.lang.annotation.*;

/**
 * 是否登陆注解
 * 默认为需要登陆
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    boolean value() default true;
}