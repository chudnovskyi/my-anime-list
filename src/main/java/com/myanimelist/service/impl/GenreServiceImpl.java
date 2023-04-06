package com.myanimelist.service.impl;

import com.myanimelist.config.JikanApiProperties;
import com.myanimelist.response.GenresResponse;
import com.myanimelist.response.GenresResponse.Genre;
import com.myanimelist.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {

    private final WebClient webClient;
    private final JikanApiProperties properties;

    public GenreServiceImpl(WebClient.Builder webClientBuilder, JikanApiProperties properties) {
        this.webClient = webClientBuilder.baseUrl(properties.getBaseUrl()).build();
        this.properties = properties;
    }

    @Override
    @Cacheable("genres")
    public Map<Integer, String> retrieveAllGenres() {
        String url = properties.getPaths().get("genres");

        log.info(url);

        GenresResponse genresResponse = webClient.get().uri(url)
                .retrieve()
                .bodyToMono(GenresResponse.class)
                .block();

        if (genresResponse == null) {
            throw new EntityNotFoundException("genres not found");
        }

        return genresResponse.getGenres()
                .stream()
                .sorted(Comparator.comparing(Genre::getName))
                .collect(Collectors.toMap(
                        Genre::getMalId,
                        Genre::getName,
                        (v1, v2) -> {
                            throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                        },
                        LinkedHashMap::new)
                );
    }
}
