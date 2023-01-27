package com.myanimelist.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.entity.Genre;
import com.myanimelist.rest.wrapper.ResponseGenreWrapper;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private Environment env;

	private RestTemplate restTemplate = new RestTemplate();

	private Logger logger = Logger.getLogger(getClass().getName());

	private Map<Integer, String> genres;

	/*
	 * Method will load genre list only once
	 */
	@Override
	public Map<Integer, String> findAllGenres() {
		if (genres == null) {
			String url = 
					env.getProperty("find.genres");
			
			logger.info(url);
			
			ResponseGenreWrapper wrapper = restTemplate.getForObject(url, ResponseGenreWrapper.class);
			
			genres = new LinkedHashMap<>();
			
			genres = wrapper.getData()
					.stream()
					.sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
					.collect(Collectors.toMap(
								Genre::getMal_id, 
								Genre::getName,
								(v1, v2) -> { 
									throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
								}, 
								LinkedHashMap::new)
							);
		}
		
		return genres;
	}

	public Map<Integer, String> getGenres() {
		return genres;
	}
}
