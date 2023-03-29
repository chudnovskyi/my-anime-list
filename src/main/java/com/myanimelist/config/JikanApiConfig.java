package com.myanimelist.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "jikan")
@Getter
@Setter
public class JikanApiConfig {

    private String baseUrl;

    private Map<String, String> paths;
    private Map<String, String> params;
}
