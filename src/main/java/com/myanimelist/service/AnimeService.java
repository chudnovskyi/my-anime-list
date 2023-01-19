package com.myanimelist.service;

import com.myanimelist.rest.entity.ResponseAnimeWrapper;

public interface AnimeService {

	public ResponseAnimeWrapper findByTitleAndPage(String title, int pageId);
}
