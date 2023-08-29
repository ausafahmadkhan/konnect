package com.user.userplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {"com.user", "com.konnect"})
@EnableMongoAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
