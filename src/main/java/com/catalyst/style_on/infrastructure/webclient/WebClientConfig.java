package com.catalyst.style_on.infrastructure.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient imageWebClient(WebClient.Builder builder) {
        // 1. Configure memory limit (default is 256KB, which is too small for images)
        int size = 16 * 1024 * 1024; // 16MB
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        // 2. Build the client
        // 'builder' here is already pre-configured by Spring Boot with metrics/tracing support
        return builder
                .exchangeStrategies(strategies)
                .build();
    }
}