package com.konnect.postservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RankingServiceConfig {

    @Bean("RankingClient")
    public RestTemplate restTemplateRanking(){
        return new RestTemplate();
    }
}
