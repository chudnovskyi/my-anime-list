package com.myanimelist.dao;

import java.util.List;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeDao {

	public void setAnimeAsViewed(int animeId);

	public List<UserAnimeDetail> getViewedList();

	public void setAnimeAsFavourite(int animeId);

	public void setAnimeScore(int animeId, int score);
}
