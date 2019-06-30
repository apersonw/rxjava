package org.rxjava.common.core.annotation;

import java.lang.annotation.*;

/**
 * 是否需要校验路径访问
 * true则检查，false则不检查
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    boolean value() default true;
}