package org.rxjava.security.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author happy 2019-06-13 13:09
 */
@SpringBootApplication
public class DynamicallyExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicallyExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }
}
