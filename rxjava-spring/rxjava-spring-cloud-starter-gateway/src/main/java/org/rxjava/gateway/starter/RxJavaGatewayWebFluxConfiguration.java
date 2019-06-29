package org.rxjava.gateway.starter;

import org.rxjava.gateway.starter.config.RxJavaWebFluxConfigurer;
import org.rxjava.gateway.starter.config.RxJavaWebFluxSecurityConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

/**
 * @author happy 2019-06-28 14:32
 */
@Configuration
@EnableDiscoveryClient
@EnableMongoAuditing
@Import({RxJavaWebFluxConfigurer.class, RxJavaWebFluxSecurityConfig.class})
@EnableWebFluxSecurity
public class RxJavaGatewayWebFluxConfiguration {
}
