package com.med.config;


/*
  @author   george
  @project   elqueuebeta
  @class  HttpConfig
  @version  1.0.0 
  @since 21/10/2026 - 23.41
*/

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class HttpConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(20000)
                .setReadTimeout(20000)
                .build();
    }
}
