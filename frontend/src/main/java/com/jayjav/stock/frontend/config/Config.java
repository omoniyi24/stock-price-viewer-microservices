package com.jayjav.stock.frontend.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Primary
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
