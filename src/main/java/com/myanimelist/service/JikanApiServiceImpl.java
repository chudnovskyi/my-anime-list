package com.myanimelist.service;

import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JikanApiServiceImpl implements JikanApiService {

	private final Environment env;
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public AnimeListResponse findSearched(String title, String genres, int pageId) {
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

		log.info(url);

		return restTemplate.getForObject(url, AnimeListResponse.class);
	}

	@Override
	public AnimeListResponse findTop(int pageId) {
		String url = 
				env.getProperty("find.top") + 
				env.getProperty("param.page") + pageId + 
				env.getProperty("param.limit");

		log.info(url);

		return restTemplate.getForObject(url, AnimeListResponse.class);
	}

	@Override
	public Anime findAnime(int animeId) {
		String url = 
				env.getProperty("find.id") + animeId;

		log.info(url);

		AnimeResponse animeResponse = restTemplate.getForObject(url, AnimeResponse.class);

		if (animeResponse == null) {
			throw new EntityNotFoundException("anime with id " + animeId + " not found");
		}

		return animeResponse.getAnime();
	}

	@Override
	public Anime findRandomAnime() {
		String url = 
				env.getProperty("find.rand");

		log.info(url);

		AnimeResponse animeResponse = restTemplate.getForObject(url, AnimeResponse.class);

		if (animeResponse == null) {
			throw new EntityNotFoundException("random anime not found");
		}

		return animeResponse.getAnime();
	}
}
