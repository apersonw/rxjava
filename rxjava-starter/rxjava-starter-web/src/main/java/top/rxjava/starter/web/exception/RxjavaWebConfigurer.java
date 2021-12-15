package top.rxjava.starter.web.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

public class RxjavaWebConfigurer extends DelegatingWebMvcConfiguration {
    /**
     * 异常处理器(排在所有异常处理器最前面)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public HandlerExceptionResolver webJsonResponseStatusExceptionHandler(
    ) {
        return new WebJsonResponseStatusExceptionHandler();
    }
}
