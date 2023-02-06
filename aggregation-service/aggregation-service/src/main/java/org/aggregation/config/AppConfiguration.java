package org.aggregation.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class AppConfiguration {

    @Value("${service.timeout}")
    private Long DEFAULT_TIMEOUT;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .setConnectTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .build();
    }

}
