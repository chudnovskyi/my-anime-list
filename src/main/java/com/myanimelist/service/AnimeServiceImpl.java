package com.myanimelist.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.ResponseAnimeWrapper;

@Service
public class AnimeServiceImpl implements AnimeService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private Environment env;
	
	@Override
	public ResponseAnimeWrapper findByNameAndPage(Anime animeForm, int pageId) {
		String url = 
				env.getProperty("find.name") + animeForm.getTitle() + 
				env.getProperty("param.page") + pageId + 
				env.getProperty("param.limit") + 10 + 
				env.getProperty("param.order_by.rank") +
				env.getProperty("param.order_by.score") +
				env.getProperty("param.sort.desc");
		
		logUrl(url);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseAnimeWrapper wrapper = restTemplate.getForObject(url, ResponseAnimeWrapper.class);
		
		return wrapper;
	}
	
	private void logUrl(String url) {
		logger.info("------------------------" + url + "------------------------");
	}
}
