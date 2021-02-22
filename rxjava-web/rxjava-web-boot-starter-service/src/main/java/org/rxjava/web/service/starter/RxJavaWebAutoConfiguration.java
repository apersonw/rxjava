package org.rxjava.web.service.starter;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * RxJava微服务自动装配
 */
@Configuration
@EnableDiscoveryClient
@EnableMongoAuditing
@Import({})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RxJavaWebAutoConfiguration {
}