package org.rxjava.starter.boot.web;

import org.bson.types.ObjectId;
import org.rxjava.starter.boot.web.aware.CustomAuditorAware;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * RxJava微服务自动装配
 * @author happy
 */
@Configuration
@Import({})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaAuditing
@EnableHystrix
public class RxJavaWebAutoConfiguration {
    @Bean
    public AuditorAware<ObjectId> customAuditorAware() {
        return new CustomAuditorAware();
    }
}