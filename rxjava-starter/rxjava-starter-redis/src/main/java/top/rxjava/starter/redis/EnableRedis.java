package top.rxjava.starter.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Redis
 * @author happy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfiguration.class})
public @interface EnableRedis {
}