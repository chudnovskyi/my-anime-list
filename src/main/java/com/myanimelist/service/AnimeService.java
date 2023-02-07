package com.myanimelist.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeService {

	public UserAnimeDetail getUserAnimeDetail(int animeId);

	public List<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate);

	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer);

	public void reset(int animeId);
}
