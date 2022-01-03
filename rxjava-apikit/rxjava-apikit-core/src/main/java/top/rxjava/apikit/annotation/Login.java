package top.rxjava.apikit.annotation;

import java.lang.annotation.*;

/**
 * @author happy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    boolean value() default true;
}