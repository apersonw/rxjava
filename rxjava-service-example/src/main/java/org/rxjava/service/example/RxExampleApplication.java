package org.rxjava.service.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author happy 2019-06-11 01:04
 */
@SpringBootApplication
public class RxExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RxExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }
}