package com.feed.feedplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = {"com.feed", "com.konnect"})
@EnableKafka
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
