package com.ranking.rankingplatform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = {"com.ranking", "com.konnect"})
@EnableMongoAuditing
@EnableKafka
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
