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

	public UserAnimeDetail getUserAnimeDetail(int animeId);
	
	public List<UserAnimeDetail> getUserAnimeDetailList();

	public List<UserAnimeDetail> getUserAnimeWatchingList();

	public List<UserAnimeDetail> getUserAnimePlanningList();

	public List<UserAnimeDetail> getUserAnimeFinishedList();

	public List<UserAnimeDetail> getUserAnimeHoldOnList();

	public List<UserAnimeDetail> getUserAnimeDroppedList();

	public List<UserAnimeDetail> getUserAnimeFavouriteList();

	public void setAnimeAsWatching(int animeId);

	public void setAnimeAsPlanning(int animeId);

	public void setAnimeAsCompleted(int animeId);

	public void setAnimeAsHoldOn(int animeId);

	public void setAnimeAsDropped(int animeId);
	
	public void setAnimeAsFavourite(int animeId);

	public void setAnimeScore(int animeId, int score);

	public void reset(int animeId);
}
