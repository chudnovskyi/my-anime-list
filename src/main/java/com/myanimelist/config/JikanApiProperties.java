package com.myanimelist.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class JikanApiProperties {

    private String baseUrl;

    private Map<String, String> paths;
    private Map<String, String> params;
}
