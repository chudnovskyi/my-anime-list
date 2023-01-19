package com.myanimelist.service;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.ResponseAnimeWrapper;

public interface AnimeService {

	public ResponseAnimeWrapper findByNameAndPage(Anime animeForm, int pageId);
}
