package com.myanimelist.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.dao.AnimeDao;
import com.myanimelist.entity.UserAnimeDetail;

@Service
public class AnimeServiceImpl implements AnimeService {

	@Lazy
	@Autowired
	private AnimeDao animeDao;

	@Autowired
	private PageableService pageableService;

	@Override
	public List<UserAnimeDetail> getUserAnimeDetailList() {
		return animeDao.getUserAnimeDetailList();
	}

	@Override
	public UserAnimeDetail getUserAnimeDetail(int animeId) {
		List<UserAnimeDetail> userAnimeDetailList = getUserAnimeDetailList()
				.stream()
				.filter(animeDetailList -> animeDetailList.getAnimeDetail().getMal_id() == animeId)
				.toList();

		return userAnimeDetailList.isEmpty() ? new UserAnimeDetail() : userAnimeDetailList.get(0);
	}

	@Override
	public Page<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate, int page, int size) {
		List<UserAnimeDetail> userAnimeDetailList = getUserAnimeDetailList()
				.stream()
				.filter(predicate)
				.toList();

		return pageableService.getPegable(userAnimeDetailList, page, size);
	}

	@Override
	@Transactional
	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer) {
		animeDao.alterUserAnimeDetail(animeId, consumer);
	}

	@Override
	@Transactional
	public void reset(int animeId) {
		animeDao.reset(animeId);
	}
}
