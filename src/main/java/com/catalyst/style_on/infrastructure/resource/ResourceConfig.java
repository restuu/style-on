package com.catalyst.style_on.infrastructure.resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resource")
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
public class ResourceConfig {

    private String assetBaseUrl;

}
