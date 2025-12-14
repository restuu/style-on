package com.catalyst.style_on.infrastructure.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchClientConfig {

    private final ElasticsearchConfig elasticsearchConfig;

    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = Arrays.stream(elasticsearchConfig.getUris()).map(HttpHost::create).toArray(HttpHost[]::new);

        String basicAuth = String.join(":", elasticsearchConfig.getUsername() , elasticsearchConfig.getPassword());
        basicAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes(StandardCharsets.UTF_8));

        Header[] defaultHeaders = new Header[]{new BasicHeader("Authorization", "Basic " + basicAuth)};

        return RestClient.builder(hosts)
                .setDefaultHeaders(defaultHeaders)
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout((int) elasticsearchConfig.getConnectionTimeout().toMillis())
                        .setSocketTimeout((int) elasticsearchConfig.getConnectionTimeout().toMillis()) // Often good to set socket timeout as well
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
