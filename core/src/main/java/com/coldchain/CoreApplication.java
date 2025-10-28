package com.coldchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the main "on switch" for your database worker.
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        // This line starts the Spring Boot server
        SpringApplication.run(CoreApplication.class, args);
    }
}