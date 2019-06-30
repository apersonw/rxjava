package org.rxjava.gateway.starter;

import org.rxjava.gateway.starter.config.RxJavaWebFluxSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author happy 2019-06-28 14:32
 */
@Configuration
@Import({RxJavaWebFluxSecurityConfig.class})
public class RxJavaGatewayAutoConfiguration {
}
