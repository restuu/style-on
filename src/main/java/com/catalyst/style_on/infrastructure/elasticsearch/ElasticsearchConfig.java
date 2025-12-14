package com.catalyst.style_on.infrastructure.elasticsearch;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchConfig {
    private String[] uris;
    private String username;
    private String password;
    private Duration connectionTimeout;
    private String indexProduct;
}
