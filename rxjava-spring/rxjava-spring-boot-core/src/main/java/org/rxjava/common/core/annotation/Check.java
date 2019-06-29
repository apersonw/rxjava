package org.rxjava.common.core.annotation;

import java.lang.annotation.*;

/**
 * 是否需要校验路径访问
 * 默认不需要
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    boolean value() default false;
}