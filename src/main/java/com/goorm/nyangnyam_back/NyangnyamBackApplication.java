package com.goorm.nyangnyam_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.goorm.nyangnyam_back.repository")
public class NyangnyamBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(NyangnyamBackApplication.class, args);
    }
}