package com.example.back.camp_review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "config", "exception"})
@EntityScan("model")
@EnableJpaRepositories("repository")
public class CampReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampReviewApplication.class, args);
    }
}