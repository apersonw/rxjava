package top.rxjava.starter.jpa;

import org.springframework.context.annotation.Import;
import top.rxjava.starter.jpa.configuration.JpaConfiguration;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JpaConfiguration.class})
public @interface EnableJpa {
}
