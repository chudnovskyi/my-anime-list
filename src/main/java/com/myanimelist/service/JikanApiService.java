package com.myanimelist.service;

import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import reactor.core.publisher.Mono;

public interface JikanApiService {

	Mono<AnimeListResponse> search(String title, String genres, int pageId);

	Mono<AnimeListResponse> searchByRating(int pageId);

	Anime searchById(int animeId);

	Mono<Anime> searchRandom();
}
