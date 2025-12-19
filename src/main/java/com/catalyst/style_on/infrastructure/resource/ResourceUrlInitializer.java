package com.catalyst.style_on.infrastructure.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens for the WebServerInitializedEvent to dynamically set the assetBaseUrl
 * if it has not been provided in the application configuration. This decouples
 * the configuration logic from the ResourceConfig data class.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceUrlInitializer implements ApplicationListener<WebServerInitializedEvent> {

    private final ResourceConfig resourceConfig;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        String assetBaseUrl = resourceConfig.getAssetBaseUrl();

        if (assetBaseUrl == null || assetBaseUrl.trim().isEmpty()) {
            int port = event.getWebServer().getPort();
            String dynamicUrl = "http://localhost:" + port;
            resourceConfig.setAssetBaseUrl(dynamicUrl);
            log.info("`resource.assetBaseUrl` not set, dynamically configured to: {}", dynamicUrl);
        } else {
            log.info("Asset base URL loaded from configuration: {}", assetBaseUrl);
        }
    }
}