package org.rxjava.starter.redis.reactive;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启RedisReactive
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisReactiveConfiguration.class})
public @interface EnableRedisReactive {
}