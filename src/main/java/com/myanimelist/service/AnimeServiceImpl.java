package com.myanimelist.service;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.dao.AnimeDao;
import com.myanimelist.entity.UserAnimeDetail;

@Service
public class AnimeServiceImpl implements AnimeService {

	@Lazy
	@Autowired
	private AnimeDao animeDao;
	
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
	public Page<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate, Pageable pageable) {
		List<UserAnimeDetail> anime = getUserAnimeDetailList()
				.stream()
				.filter(predicate)
				.toList();
		
		return getPegable(anime, pageable);
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
	
	private PageImpl<UserAnimeDetail> getPegable(List<UserAnimeDetail> animeList, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;
		
		int size = animeList.size();
		
		if (animeList.size() < startItem) {
			animeList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, animeList.size());
            animeList = animeList.subList(startItem, toIndex);
        }
		
		return new PageImpl<UserAnimeDetail>(animeList, PageRequest.of(currentPage, pageSize), size);
	}
}
