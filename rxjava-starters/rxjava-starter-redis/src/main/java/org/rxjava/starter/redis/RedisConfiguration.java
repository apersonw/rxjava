package org.rxjava.starter.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author happy 2019-05-02 14:32
 * 消息总线配置
 */
@Configuration
public class RedisConfiguration {
    /**
     * Redis Bean
     */
    @Bean
    @Primary
    RedisTemplate<String, String> redisTemplate() {
        return new RedisTemplate<>();
    }
}
