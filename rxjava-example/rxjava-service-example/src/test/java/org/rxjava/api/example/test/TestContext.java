package org.rxjava.api.example.test;

import org.rxjava.service.starter.boot.RxJavaWebFluxConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RxJavaWebFluxConfigurer.class)
public class TestContext {
}
