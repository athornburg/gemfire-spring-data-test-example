package com.gemfire.example.test.gemfireexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@EnableGemfireRepositories
public class GemfireExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GemfireExampleApplication.class, args);
    }

}
