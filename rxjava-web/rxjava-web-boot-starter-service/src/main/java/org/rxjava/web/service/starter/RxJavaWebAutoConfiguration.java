package org.rxjava.web.service.starter;

import org.bson.types.ObjectId;
import org.rxjava.web.service.starter.config.CustomAuditorAware;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * RxJava微服务自动装配
 */
@Configuration
@EnableDiscoveryClient
@EnableMongoAuditing
@Import({})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaAuditing
public class RxJavaWebAutoConfiguration {
    @Bean
    public AuditorAware<ObjectId> customAuditorAware() {
        return new CustomAuditorAware();
    }
}