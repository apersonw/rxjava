package org.rxjava.common.test;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RxTestConfiguration.class})
public @interface EnableTest {
}
