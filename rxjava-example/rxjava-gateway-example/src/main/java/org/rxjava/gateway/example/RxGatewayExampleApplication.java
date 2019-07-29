package org.rxjava.gateway.example;

import org.rxjava.api.example.inner.InnerLoginInfoApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.bus.EnableBus;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.rxjava.gateway.starter.config.CheckTokenConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.reactive.function.client.WebClient;

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

    @Bean
    @Qualifier("authClientAdapter")
    public ClientAdapter authClientAdapter(
            @Qualifier("webFluxConversionService")
                    ConversionService conversionService,
            WebClient.Builder webClientBuilder,
            CheckTokenConfig checkTokenConfig
    ) {
        return ReactiveHttpClientAdapter.build(conversionService, webClientBuilder, checkTokenConfig.getServiceId(), checkTokenConfig.getPort());
    }

    @Bean
    public InnerLoginInfoApi innerLoginInfoApi(@Qualifier("authClientAdapter") ClientAdapter clientAdapter) {
        InnerLoginInfoApi api = new InnerLoginInfoApi();
        api.setclientAdapter(clientAdapter);
        return api;
    }
}