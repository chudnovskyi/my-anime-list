package com.myanimelist.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.wrapper.ResponseGenreWrapper;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private Environment env;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Map<Integer, String> genres;
	
	@Override
	@Transactional
	public Map<Integer, String> findAllGenres() {
		// not sure if this is the best approach ... 
		if (genres == null) {
			String url = 
					env.getProperty("find.genres");
			
			logUrl(url);
			
			ResponseGenreWrapper wrapper = restTemplate.getForObject(url, ResponseGenreWrapper.class);
			
			genres = new HashMap<>();
			
			genres = wrapper.getData()
					.stream()
					.collect(Collectors.toMap((x) -> x.getMal_id(), (x) -> x.getName()));
		} 
		
		return genres;
	}
	
	public Map<Integer, String> getGenres() {
		return genres;
	}

	private void logUrl(String url) {
		logger.info("------------------------>> " + url);
	}
}
