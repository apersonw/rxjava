package org.rxjava.common.core.annotation;

import java.lang.annotation.*;

/**
 * 是否登陆注解
 * 默认为需要登陆
 * @author happy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    boolean value() default true;
}