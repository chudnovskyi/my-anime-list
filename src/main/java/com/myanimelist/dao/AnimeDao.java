package com.myanimelist.dao;

import java.util.List;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeDao {
	
	public List<UserAnimeDetail> getUserAnimeDetailList();

	public void setAnimeAsWatching(int animeId);

	public void setAnimeAsPlanning(int animeId);

	public void setAnimeAsCompleted(int animeId);

	public void setAnimeAsOnHold(int animeId);

	public void setAnimeAsDropped(int animeId);
	
	public void setAnimeAsFavourite(int animeId);

	public void setAnimeScore(int animeId, int score);

	public void reset(int animeId);
}
