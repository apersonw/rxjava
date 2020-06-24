package org.rxjava.common.core;

import org.rxjava.common.core.tools.RequestTool;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RxJavaCoreAutoConfiguration {
    /**
     * 网络请求工具
     */
    @Bean
    @Primary
    public RequestTool requestTool() {
        return new RequestTool();
    }
}
