package com.growing.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenAiConfig {
    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + openAiKey)
                .build();
    }
}