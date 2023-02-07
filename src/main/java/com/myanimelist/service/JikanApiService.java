package com.myanimelist.service;

import com.myanimelist.dto.AnimeListResponse;
import com.myanimelist.dto.AnimeResponse.Anime;

public interface JikanApiService {

	public AnimeListResponse findSearched(String title, String genres, int pageId);

	public AnimeListResponse findTop(int pageId);

	public Anime findAnime(int animeId);

	public Anime findRandomAnime();
}
