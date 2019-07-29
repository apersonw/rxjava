package org.rxjava.common.core.annotation;

import java.lang.annotation.*;

/**
 * 认证授权检查
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    boolean value() default true;
}