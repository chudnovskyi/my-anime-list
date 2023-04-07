package com.myanimelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:${envTarget:jikan}.properties"
})
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "jikan")
    public JikanApiProperties jikanApiProperties() {
        return new JikanApiProperties();
    }
}
