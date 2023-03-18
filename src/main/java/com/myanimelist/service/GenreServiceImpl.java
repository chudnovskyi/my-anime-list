package com.myanimelist.service;

import com.myanimelist.response.GenresResponse;
import com.myanimelist.response.GenresResponse.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final Environment env;
	private final RestTemplate restTemplate = new RestTemplate();

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
}
