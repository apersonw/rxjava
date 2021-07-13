package org.rxjava.starter.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rxjava.starter.webflux.configuration.RxJavaWebFluxConfigurer;
import org.rxjava.starter.webflux.configuration.ServiceDelegatingWebFluxConfiguration;
import org.rxjava.utils.JsonUtils;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

/**
 * @author happy 2019-04-09 01:32
 * RxJava微服务自动装配
 */
@Configuration
@Import({
        ServiceDelegatingWebFluxConfiguration.class,
        RxJavaWebFluxConfigurer.class
})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class WebfluxAutoConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtils.DEFAULT_MAPPER;
    }
}