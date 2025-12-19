package com.catalyst.style_on.infrastructure.json;

import com.catalyst.style_on.domain.shared.json.JsonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@Slf4j
public class JacksonConfig {

    private final ObjectMapper objectMapper = createObjectMapper();

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    @PostConstruct
    public void initializeJsonUtils() {
        log.info("Initializing JsonUtils...");
        JsonUtils.init(this.objectMapper);
    }

    private ObjectMapper createObjectMapper() {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .build();
    }
}
