package org.rxjava.webflux.service.starter;

import org.rxjava.webflux.service.starter.boot.CustomMongoConfiguration;
import org.rxjava.webflux.service.starter.boot.ReactiveRequestContextFilter;
import org.rxjava.webflux.service.starter.boot.RxJavaWebFluxConfigurer;
import org.rxjava.webflux.service.starter.boot.ServiceDelegatingWebFluxConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * @author happy 2019-04-09 01:32
 * RxJava微服务自动装配
 */
@Configuration
@EnableDiscoveryClient
@EnableMongoAuditing
@Import({
        ServiceDelegatingWebFluxConfiguration.class,
        RxJavaWebFluxConfigurer.class,
        //CustomMongoConfiguration.class,已经不再需要自行创建索引了
        ReactiveRequestContextFilter.class
})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RxJavaServiceAutoConfiguration {
}