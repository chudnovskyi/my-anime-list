package com.myanimelist.service;

import com.myanimelist.config.JikanApiConfig;
import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class JikanApiServiceImpl implements JikanApiService {

	private final WebClient webClient;
	private final JikanApiConfig config;

	public JikanApiServiceImpl(WebClient.Builder webClientBuilder, JikanApiConfig config) {
		this.webClient = webClientBuilder.baseUrl(config.getBaseUrl()).build();
		this.config = config;
	}

	@Override
	public Mono<AnimeListResponse> search(String title, String genres, int pageId) {
		StringBuilder urlBuilder = new StringBuilder(config.getPaths().get("anime"));
		urlBuilder.append(config.getParams().get("page")).append(pageId)
				.append(config.getParams().get("limit"))
				.append(config.getParams().get("orderBy.score"))
				.append(config.getParams().get("sort.desc"));

		if (title != null && !title.isBlank()) {
			urlBuilder.append(config.getParams().get("title")).append(title);
		}

		if (genres != null && !genres.isBlank()) {
			urlBuilder.append(config.getParams().get("genres")).append(genres);
		}

        String url = urlBuilder.toString();

		log.info(url);

		return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeListResponse.class)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Anime list not found")));
	}

	public Mono<AnimeListResponse> searchByRating(int pageId) {
		String url = config.getPaths().get("top") +
				config.getParams().get("page") + pageId +
				config.getParams().get("limit");

		log.info(url);

		return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeListResponse.class)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Top anime list not found")));
	}

	public Anime searchById(int animeId) {
		String url = config.getPaths().get("anime.id") + animeId;

		log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeResponse.class)
                .map(AnimeResponse::getAnime)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Anime with id " + animeId + " not found")))
                .block();
	}

	@Override
	public Mono<Anime> searchRandom() {
		String url = config.getPaths().get("random");

		log.info(url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(AnimeResponse.class)
                .map(AnimeResponse::getAnime)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Random anime not found")));
	}
}
