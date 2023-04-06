package com.myanimelist.service.impl;

import com.myanimelist.config.JikanApiProperties;
import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import com.myanimelist.service.JikanApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class JikanApiServiceImpl implements JikanApiService {

    private final WebClient webClient;
    private final JikanApiProperties properties;

    public JikanApiServiceImpl(WebClient.Builder webClientBuilder, JikanApiProperties properties) {
        this.webClient = webClientBuilder.baseUrl(properties.getBaseUrl()).build();
        this.properties = properties;
    }

    @Override
    public Mono<AnimeListResponse> search(String animeTitle, String animeGenres, int pageNumber) {
        StringBuilder urlBuilder = new StringBuilder(properties.getPaths().get("anime"));
        urlBuilder.append(properties.getParams().get("page")).append(pageNumber)
                .append(properties.getParams().get("limit"))
                .append(properties.getParams().get("orderBy.score"))
                .append(properties.getParams().get("sort.desc"));

        if (animeTitle != null && !animeTitle.isBlank()) {
            urlBuilder.append(properties.getParams().get("title")).append(animeTitle);
        }

        if (animeGenres != null && !animeGenres.isBlank()) {
            urlBuilder.append(properties.getParams().get("genres")).append(animeGenres);
        }

        String url = urlBuilder.toString();

        log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeListResponse.class)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Anime list not found")));
    }

    public Mono<AnimeListResponse> searchByRating(int pageNumber) {
        String url = properties.getPaths().get("top") +
                properties.getParams().get("page") + pageNumber +
                properties.getParams().get("limit");

        log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeListResponse.class)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Top anime list not found")));
    }

    public Anime searchById(int animeId) {
        String url = properties.getPaths().get("anime.id") + animeId;

        log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeResponse.class)
                .map(AnimeResponse::getAnime)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Anime with id " + animeId + " not found")))
                .block(Duration.of(10, ChronoUnit.SECONDS));
    }

    @Override
    public Mono<Anime> searchRandom() {
        String url = properties.getPaths().get("random");

        log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeResponse.class)
                .map(AnimeResponse::getAnime)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Random anime not found")));
    }
}
