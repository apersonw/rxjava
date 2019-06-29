package org.rxjava.gateway.starter;

import org.rxjava.gateway.starter.config.GatewayDelegatingWebFluxConfiguration;
import org.rxjava.gateway.starter.config.RxJavaWebFluxConfigurer;
import org.rxjava.gateway.starter.config.RxJavaWebFluxSecurityConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * @author happy 2019-06-28 14:32
 */
@Configuration
@EnableDiscoveryClient
@EnableMongoAuditing
@Import({GatewayDelegatingWebFluxConfiguration.class, RxJavaWebFluxConfigurer.class, RxJavaWebFluxSecurityConfig.class})
public class RxJavaGatewayWebFluxConfiguration {
}
