package com.coldchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// --- THIS IS THE FIX ---
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// --- THIS IS THE FIX ---
@EnableJpaRepositories("com.coldchain.repository")
@EntityScan("com.coldchain.model")

@SpringBootApplication
@ComponentScan(basePackages = "com.coldchain")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}