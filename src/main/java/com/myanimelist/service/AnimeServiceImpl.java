package com.myanimelist.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.dao.AnimeDao;
import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.wrapper.ResponseAnimeWrapper;
import com.myanimelist.rest.wrapper.ResponseSingleAnimeWrapper;

@Service
public class AnimeServiceImpl implements AnimeService {

	@Autowired
	@Lazy
	private AnimeDao animeDao;
	
	@Autowired
	private Environment env;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public ResponseAnimeWrapper findSearched(String title, String genres, int pageId) {
		String url =
				env.getProperty("find.all") +
				env.getProperty("param.page") + pageId +
				env.getProperty("param.limit") +
				env.getProperty("param.order_by.score") +
				env.getProperty("param.sort.desc");
		
		if (title != null && !title.isBlank()) {
			url += env.getProperty("param.title") + title;
		}
		
		if (genres != null && !genres.isBlank()) {
			url += env.getProperty("param.genres") + genres;
		}
		
		logUrl(url);
		
		ResponseAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseAnimeWrapper.class);
		
		return wrapper;
	}
	
	@Override
	public ResponseAnimeWrapper findTop(int pageId) {
		String url =
				env.getProperty("find.top") +
				env.getProperty("param.page") + pageId + 
				env.getProperty("param.limit");
		
		logUrl(url);
		
		ResponseAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseAnimeWrapper.class);
		
		return wrapper;
	}
	
	@Override
	public Anime findAnimeById(int animeId) {
		String url =
				env.getProperty("find.id") + animeId;
		
		logUrl(url);
		
		ResponseSingleAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseSingleAnimeWrapper.class);
		
		return wrapper.getData();
	}
	
	@Override
	public Anime findRandomAnime() {
		String url =
				env.getProperty("find.rand");
		
		logUrl(url);
		
		ResponseSingleAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseSingleAnimeWrapper.class);
		
		return wrapper.getData();
	}
	
	@Override
	@Transactional
	public void setAnimeAsViewed(int animeId) {
		animeDao.setAnimeAsViewed(animeId);
	}
	
	@Override
	public List<UserAnimeDetail> getViewedList() {
		return animeDao.getViewedList();
	}
	
	private void logUrl(String url) {
		logger.info("------------------------>> " + url);
	}
}
