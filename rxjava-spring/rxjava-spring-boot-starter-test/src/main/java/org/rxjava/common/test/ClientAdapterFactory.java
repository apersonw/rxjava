package org.rxjava.common.test;

import org.rxjava.apikit.client.ClientAdapter;
import org.rxjava.common.core.api.ReactiveHttpClientAdapter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientAdapterFactory {
    public ClientAdapter build(String host, String port, String serviceId) {
        return ReactiveHttpClientAdapter.build(new DefaultConversionService(), WebClient.builder(), host, port, serviceId);
    }

    /**
     * 默认端口8080
     */
    public ClientAdapter build(String host, String serviceId) {
        return this.build(host, "8080", serviceId);
    }

    /**
     * 默认host:localhost
     * 默认port:8080
     */
    public ClientAdapter build(String serviceId) {
        return this.build("localhost", serviceId);
    }
}
