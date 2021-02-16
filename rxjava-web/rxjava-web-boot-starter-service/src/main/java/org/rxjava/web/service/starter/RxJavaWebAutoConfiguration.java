package org.rxjava.web.service.starter;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author happy 2019-04-09 01:32
 * RxJava微服务自动装配
 */
@Configuration
@EnableDiscoveryClient
@EnableJpaAuditing
@Import({})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RxJavaWebAutoConfiguration {
}