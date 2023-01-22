package com.myanimelist.dao;

import java.util.List;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeDao {

	public void setAnimeAsViewed(int animeId);

	public List<UserAnimeDetail> getViewedList();
}
