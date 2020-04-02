package org.rxjava.gateway.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author happy 2019-05-10 18:06
 */
@SpringBootApplication
public class RxGatewayExampleApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxGatewayExampleApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
