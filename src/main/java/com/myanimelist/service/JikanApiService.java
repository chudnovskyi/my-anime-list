package com.myanimelist.service;

import com.myanimelist.dto.AnimeListResponse;
import com.myanimelist.dto.AnimeResponse.Anime;

public interface JikanApiService {

	AnimeListResponse findSearched(String title, String genres, int pageId);

	AnimeListResponse findTop(int pageId);

	Anime findAnime(int animeId);

	Anime findRandomAnime();
}
