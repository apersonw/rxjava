package org.rxjava.gateway.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

public class CustomGatewayDiscoveryClientAutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "spring.cloud.gateway.discovery.locator.enabled")
    public DiscoveryClientRouteDefinitionLocator customDiscoveryClientRouteDefinitionLocator(
            ReactiveDiscoveryClient discoveryClient,
            DiscoveryLocatorProperties properties) {
        return new CustomDiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }
}
