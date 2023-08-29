package com.post.postplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.post", "com.konnect"})
@EnableMongoAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
