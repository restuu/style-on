package com.catalyst.style_on.infrastructure.database;

import com.catalyst.style_on.infrastructure.database.converter.JsonToStyleMetadataMapConverter;
import com.catalyst.style_on.infrastructure.database.converter.MapStyleMetadataToJsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();

        converters.add(new MapStyleMetadataToJsonConverter(objectMapper));
        converters.add(new JsonToStyleMetadataMapConverter(objectMapper));

        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }
}
