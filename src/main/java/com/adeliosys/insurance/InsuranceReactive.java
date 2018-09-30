package com.adeliosys.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * Sample insurance application that provides insurance quotes.
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class InsuranceReactive {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceReactive.class, args);
    }
}
