package org.rxjava.webflux.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author happy
 */
@Configuration
public class RxTestConfiguration {
    @Bean
    public ClientAdapterFactory clientAdapterFactory(){
        return new ClientAdapterFactory();
    }
}
