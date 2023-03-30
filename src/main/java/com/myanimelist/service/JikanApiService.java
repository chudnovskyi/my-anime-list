package com.myanimelist.service;

import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import reactor.core.publisher.Mono;

public interface JikanApiService {

    Mono<AnimeListResponse> search(String animeTitle, String animeGenres, int pageNumber);

    Mono<AnimeListResponse> searchByRating(int pageNumber);

    Anime searchById(int animeId);

    Mono<Anime> searchRandom();
}
