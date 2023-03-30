package com.myanimelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MyAnimeListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAnimeListApplication.class, args);
    }
}
