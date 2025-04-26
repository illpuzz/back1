package com.example.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.back.model")
@EnableJpaRepositories(basePackages = "com.example.back.repository")
public class BackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}