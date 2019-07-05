package org.rxjava.gateway.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author happy 2019-07-04 14:46
 */
@ConfigurationProperties("checktoken")
@Data
@Configuration
public class CheckTokenConfig {
    /**
     * 服务Id
     */
    private String serviceId;
    /**
     * 服务端口：默认8080
     */
    private String port = "8080";
}
