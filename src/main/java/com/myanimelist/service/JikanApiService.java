package com.myanimelist.service;

import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse.Anime;

public interface JikanApiService {

	AnimeListResponse findSearched(String title, String genres, int pageId);

	AnimeListResponse findTop(int pageId);

	Anime findAnime(int animeId);

	Anime findRandomAnime();
}
