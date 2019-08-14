package org.rxjava.gateway.example;

import org.rxjava.common.bus.EnableBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author happy 2019-06-11 01:04
 */
@EnableBus
@SpringBootApplication
public class RxGatewayExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RxGatewayExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/user/**").filters(p -> p.stripPrefix(1)).uri("http://localhost:8081"))
                .build();
    }
}