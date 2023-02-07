package com.myanimelist.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.dto.GenresResponse;
import com.myanimelist.rest.dto.GenresResponse.Genre;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private Environment env;

	private RestTemplate restTemplate = new RestTemplate();

	private Map<Integer, String> genres;

	/*
	 * Method will load genre list only once
	 */
	@Override
	public Map<Integer, String> findAllGenres() {
		if (genres == null) {
			String url = 
					env.getProperty("find.genres");
			
			log.info(url);
			
			GenresResponse genresResponse = restTemplate.getForObject(url, GenresResponse.class);
			
			genres = new LinkedHashMap<>();
			
			genres = genresResponse.getGenres()
					.stream()
					.sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
					.collect(Collectors.toMap(
								Genre::getMalId, 
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
