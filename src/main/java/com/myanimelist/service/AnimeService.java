package com.myanimelist.service;

import com.myanimelist.entity.UserAnimeDetail;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AnimeService {

	UserAnimeDetail getUserAnimeDetail(int animeId);

	List<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate);

	void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer);

	void reset(int animeId);
}
