package com.konnect.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserServiceConfig {

    @Bean("UserClient")
    public RestTemplate restTemplateUser(){
        return new RestTemplate();
    }
}
