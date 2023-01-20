package com.myanimelist.service;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.wrapper.ResponseAnimeWrapper;

public interface AnimeService {

	public ResponseAnimeWrapper findByTitle(String title, int pageId);

	public ResponseAnimeWrapper findByTitleAndGenres(String title, String genres, int pageId);

	public ResponseAnimeWrapper findTop(int pageId);
	
	public Anime findAnimeById(int animeId);

	public Anime findRandomAnime();
}
