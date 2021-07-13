package org.rxjava.starter.webflux;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author happy 2019-04-09 01:32
 * RxJava微服务自动装配
 */
@Configuration
@Import({

})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class WebfluxAutoConfiguration {
}