package com.myanimelist.dao;

import java.util.List;
import java.util.function.Consumer;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeDao {

	public List<UserAnimeDetail> getUserAnimeDetailList();

	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer);

	public void reset(int animeId);
}
