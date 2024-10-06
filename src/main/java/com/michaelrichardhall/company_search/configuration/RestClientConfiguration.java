package com.michaelrichardhall.company_search.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${TruProxyAPI.baseUrl}")
    private String baseUrl;

    @Value("${TruProxyAPI.apiToken}")
    private String apiToken;

    @Bean
    RestClient restClient() {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-api-key", apiToken)
                .build();
    }
}
