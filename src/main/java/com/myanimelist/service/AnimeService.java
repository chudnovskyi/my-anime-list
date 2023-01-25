package com.myanimelist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	public Page<UserAnimeDetail> getUserAnimeWatchingList(Pageable pageable);

	public Page<UserAnimeDetail> getUserAnimePlanningList(Pageable pageable);

	public Page<UserAnimeDetail> getUserAnimeFinishedList(Pageable pageable);

	public Page<UserAnimeDetail> getUserAnimeOnHoldList(Pageable pageable);

	public Page<UserAnimeDetail> getUserAnimeDroppedList(Pageable pageable);

	public Page<UserAnimeDetail> getUserAnimeFavouriteList(Pageable pageable);

	public void setAnimeAsWatching(int animeId);

	public void setAnimeAsPlanning(int animeId);

	public void setAnimeAsCompleted(int animeId);

	public void setAnimeAsOnHold(int animeId);

	public void setAnimeAsDropped(int animeId);
	
	public void setAnimeAsFavourite(int animeId);

	public void setAnimeScore(int animeId, int score);

	public void reset(int animeId);
}
