package com.myanimelist.service;

import com.myanimelist.rest.dto.AnimeListResponse;
import com.myanimelist.rest.dto.AnimeResponse.Anime;

public interface JikanApiService {

	public AnimeListResponse findSearched(String title, String genres, int pageId);

	public AnimeListResponse findTop(int pageId);

	public Anime findAnime(int animeId);

	public Anime findRandomAnime();
}
