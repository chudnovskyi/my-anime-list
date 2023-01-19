package com.myanimelist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({ 
	  "classpath:${envTarget:JikanREST}.properties"
	})
public class PropertiesConfig {
	
}