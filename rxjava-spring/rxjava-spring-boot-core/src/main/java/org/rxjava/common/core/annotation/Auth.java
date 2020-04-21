package org.rxjava.common.core.annotation;

import java.lang.annotation.*;

/**
 * 鉴权
 * 默认需要鉴权
 *
 * @author happy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    boolean value() default true;
}