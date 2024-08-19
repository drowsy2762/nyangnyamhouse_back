package com.goorm.nyangnyam_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.goorm.nyangnyam_back.repository")
public class MongoConfig {

}