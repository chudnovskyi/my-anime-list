package com.myanimelist.service;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.ResponseAnimeWrapper;

public interface AnimeService {

	public ResponseAnimeWrapper findByTitleAndPage(String title, int pageId);

	public ResponseAnimeWrapper findTop(int pageId);

	public Anime findAnimeById(int animeId);
}
