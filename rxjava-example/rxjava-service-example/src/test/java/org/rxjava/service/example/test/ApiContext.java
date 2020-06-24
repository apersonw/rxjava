package org.rxjava.service.example.test;

import org.rxjava.api.example.person.TestApi;
import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.test.ClientAdapterFactory;
import org.rxjava.common.test.EnableTest;
import org.springframework.context.annotation.Bean;

@EnableTest
public class ApiContext {
    @Bean
    public ClientAdapter clientAdapter(ClientAdapterFactory clientAdapterFactory) {
        return clientAdapterFactory.build("localhost", "8082", "");
    }

    @Bean
    public TestApi testApi(ClientAdapter clientAdapter) {
        return new TestApi(clientAdapter);
    }
}
