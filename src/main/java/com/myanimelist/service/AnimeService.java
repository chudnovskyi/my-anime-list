package com.myanimelist.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;

import com.myanimelist.entity.UserAnimeDetail;

public interface AnimeService {

	public List<UserAnimeDetail> getUserAnimeDetailList();

	public Page<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate, int page, int size);

	public UserAnimeDetail getUserAnimeDetail(int animeId);

	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer);

	public void reset(int animeId);
}
