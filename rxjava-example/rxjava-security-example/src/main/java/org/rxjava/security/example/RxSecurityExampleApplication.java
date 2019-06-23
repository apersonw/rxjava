package org.rxjava.security.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @author happy 2019-06-13 13:09
 */
@SpringBootApplication
public class RxSecurityExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RxSecurityExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }

    @Bean
    @Primary
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }
}
