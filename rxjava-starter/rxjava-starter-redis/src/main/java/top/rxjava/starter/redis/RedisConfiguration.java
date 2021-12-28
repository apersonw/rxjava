package top.rxjava.starter.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * 响应式Redis配置
 *
 * @author happy
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
