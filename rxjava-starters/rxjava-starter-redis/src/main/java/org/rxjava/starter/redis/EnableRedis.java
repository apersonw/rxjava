package org.rxjava.starter.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启RedisReactive
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfiguration.class})
public @interface EnableRedis {
}