package org.rxjava.api.example.test;

import org.rxjava.api.example.person.TestApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.rxjava.service.starter.boot.RxJavaWebFluxConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TestContext {

    @Bean
    public ClientAdapter clientAdapter() {
        return ReactiveHttpClientAdapter.build(new DefaultFormattingConversionService(), WebClient.builder(), "localhost");
    }

    @Bean
    public TestApi testApi(ClientAdapter clientAdapter) {
        TestApi testApi = new TestApi();
        testApi.setClientAdapter(clientAdapter);
        return testApi;
    }
}
