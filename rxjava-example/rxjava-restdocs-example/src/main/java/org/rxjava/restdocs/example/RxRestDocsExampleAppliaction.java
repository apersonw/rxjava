package org.rxjava.restdocs.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author happy 2019-05-10 18:06
 */
@SpringBootApplication
public class RxRestDocsExampleAppliaction {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxRestDocsExampleAppliaction.class).web(WebApplicationType.REACTIVE).run(args);
    }
}