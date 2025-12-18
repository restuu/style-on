package com.catalyst.style_on.infrastructure.ai;


import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class GenAIConfig {
    // @Bean
    public Client client() {
        return new Client();
    }
}
