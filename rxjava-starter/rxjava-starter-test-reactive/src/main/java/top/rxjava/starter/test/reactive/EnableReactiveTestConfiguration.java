package top.rxjava.starter.test.reactive;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wugang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ReactiveTestConfiguration.class})
public @interface EnableReactiveTestConfiguration {
}
