package org.rxjava.gateway.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.function.Predicate;

/**
 * @author happy 2019-05-10 18:06
 */
@SpringBootApplication
public class RxGatewayExampleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxGatewayExampleApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p.path("/**")
//                        .filters(u -> u.rewritePath("/([^/]+)/(?<segment>.*)", "/${segment}"))
//                        .uri("https://www.baidu.com"))
//                .build();
//    }
}
