package com.myanimelist.service;

import java.util.List;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.wrapper.ResponseAnimeWrapper;

public interface AnimeService {

	public ResponseAnimeWrapper findSearched(String title, String genres, int pageId);

	public ResponseAnimeWrapper findTop(int pageId);
	
	public Anime findAnimeById(int animeId);

	public Anime findRandomAnime();

	public void setAnimeAsViewed(int animeId);

	public List<UserAnimeDetail> getViewedList();

	public void setAnimeAsFavourite(int animeId);

	public void setAnimeScore(int animeId, int score);
}
