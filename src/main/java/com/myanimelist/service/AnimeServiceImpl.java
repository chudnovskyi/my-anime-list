package com.myanimelist.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.ResponseAnimeWrapper;
import com.myanimelist.rest.entity.SingleAnimeWrapper;

@Service
public class AnimeServiceImpl implements AnimeService {

	@Autowired
	private Environment env;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public ResponseAnimeWrapper findByTitleAndPage(String title, int pageId) {
		String url = 
				env.getProperty("find.all") + 
				env.getProperty("param.title") + title + 
				env.getProperty("param.page") + pageId + 
				env.getProperty("param.limit") + 
				env.getProperty("param.order_by.rank") +
				env.getProperty("param.order_by.score") +
				env.getProperty("param.sort.desc");
		
		logUrl(url);
		
		ResponseAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseAnimeWrapper.class);
		
		return wrapper;
	}
	
	@Override
	public ResponseAnimeWrapper findTop(int pageId) {
		String url = 
				env.getProperty("find.all") + 
				env.getProperty("param.page") + pageId + 
				env.getProperty("param.limit") + 
				env.getProperty("param.order_by.rank") +
				env.getProperty("param.order_by.score") +
				env.getProperty("param.sort.desc");
		
		logUrl(url);
		
		ResponseAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseAnimeWrapper.class);
		
		return wrapper;
	}
	
	@Override
	public Anime findAnimeById(int animeId) {
		String url =
				env.getProperty("find.id") + animeId;
		
		logUrl(url);
		
		SingleAnimeWrapper wrapper = restTemplate.getForObject(url, SingleAnimeWrapper.class);
		
		return wrapper.getData();
	}
	
	private void logUrl(String url) {
		logger.info("------------------------" + url + "------------------------");
	}
}
