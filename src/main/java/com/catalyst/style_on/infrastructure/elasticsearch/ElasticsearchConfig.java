package com.catalyst.style_on.infrastructure.elasticsearch;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;

@Configuration
public class ElasticsearchConfig {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${elasticsearch.uris}")
    private String[] uris;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
    @Value("${elasticsearch.connection-timeout}")
    Duration connectionTimeout;
    @Value("${debug:false}")
    boolean debug;

    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = Arrays.stream(uris).map(HttpHost::create).toArray(HttpHost[]::new);

        String basicAuth = String.join(":", username, password);
        basicAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes(StandardCharsets.UTF_8));

        Header[] defaultHeaders = new Header[]{new BasicHeader("Authorization", "Basic " + basicAuth)};

        return RestClient.builder(hosts)
                .setDefaultHeaders(defaultHeaders)
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout((int) connectionTimeout.toMillis())
                        .setSocketTimeout((int) connectionTimeout.toMillis()) // Often good to set socket timeout as well
                )
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    // Add any other http client configurations here
                    return httpClientBuilder;
                });
    }

    @Bean
    public RestHighLevelClient restClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }
}
