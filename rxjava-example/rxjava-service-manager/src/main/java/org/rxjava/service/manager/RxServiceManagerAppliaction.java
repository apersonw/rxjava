package org.rxjava.service.manager;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author happy 2019-05-10 18:06
 */
@SpringBootApplication
public class RxServiceManagerAppliaction {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxServiceManagerAppliaction.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
