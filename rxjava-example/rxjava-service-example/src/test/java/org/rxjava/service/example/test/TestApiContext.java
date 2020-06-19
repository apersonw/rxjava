package org.rxjava.service.example.test;

import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.reactive.function.client.WebClient;

public class TestApiContext {

    @Bean
    public ClientAdapter clientAdapter() {
        return ReactiveHttpClientAdapter.build(new DefaultConversionService(), WebClient.builder(), "localhost", "8082", "");
    }
}
