package com.myanimelist.service;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.wrapper.ResponseAnimeWrapper;

public interface JikanApiService {

	public ResponseAnimeWrapper findSearched(String title, String genres, int pageId);

	public ResponseAnimeWrapper findTop(int pageId);

	public Anime findAnime(int animeId);

	public Anime findRandomAnime();
}
