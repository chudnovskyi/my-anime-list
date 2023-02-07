package com.myanimelist.controller;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.rest.dto.AnimeListResponse;
import com.myanimelist.rest.dto.AnimeResponse.Anime;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.ReviewService;
import com.myanimelist.validation.entity.ValidReview;
import com.myanimelist.validation.entity.ValidSearchAnime;

@Controller
@RequestMapping("/anime")
public class AnimeController {

	@Autowired
	private AnimeService animeService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private JikanApiService jikanApiService;
	
	private static final Map<String, Consumer<UserAnimeDetail>> STATUS_CONSUMER = Map.of(
			"watching", x -> x.setAsWatching(),
			"planning", x -> x.setAsPlanning(),
			"finished", x -> x.setAsCompleted(),
			"on-hold", x -> x.setAsOnHold(),
			"dropped", x -> x.setAsDropped(),
			"favourite", x -> x.setFavourite(!x.isFavourite())
		);
	
	@GetMapping("/{animeId}")
	public String getAnimeById(
			@PathVariable(name = "animeId") int animeId,
			Model model) {
		
		Anime anime = jikanApiService.findAnime(animeId);
		
		model.addAttribute("anime", anime);
		model.addAttribute("reviews", reviewService.findReviews(animeId));
		model.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMalId()));
		
		if (!model.containsAttribute("reviewForm")) {
			model.addAttribute("reviewForm", new ValidReview(anime.getMalId()));
		}
		
		return "anime-details";
	}
	
	@GetMapping("/random")
	public String getRandomAnime(
			Model model) {
		
		Anime anime = jikanApiService.findRandomAnime();
		
		model.addAttribute("anime", anime);
		model.addAttribute("reviewForm", new ValidReview(anime.getMalId()));
		model.addAttribute("reviews", reviewService.findReviews(anime.getMalId()));
		model.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMalId()));
		
		return "anime-details";
	}

	@PostMapping("/search/{pageId}")
	public String filteredSearch(
			@ModelAttribute("searchAnime") ValidSearchAnime searchAnime,
			@PathVariable(name = "pageId") int pageId,
			Model model) {
		
		AnimeListResponse animeListResponse = 
				jikanApiService.findSearched(searchAnime.getTitle(), searchAnime.getGenres(), pageId);
		
		model.addAttribute("searchAnime", searchAnime);
		model.addAttribute("animeList", animeListResponse.getAnimeList());
		model.addAttribute("pagination", animeListResponse.getPagination());
		
		return "anime-search";
	}

	@GetMapping("/top/{pageId}")
	public String getTopRatedAnime(
			@PathVariable(name = "pageId") int pageId,
			Model model) {
		
		AnimeListResponse animeListResponse = jikanApiService.findTop(pageId);
		
		model.addAttribute("animeList", animeListResponse.getAnimeList());
		model.addAttribute("pagination", animeListResponse.getPagination());
		
		return "anime-top";
	}
	
	@GetMapping("/set/{animeId}")
	public String consumer(
			@PathVariable(name = "animeId") int animeId,
			@RequestParam(name = "status", required = true) String status) {

		Consumer<UserAnimeDetail> consumer = STATUS_CONSUMER.get(status);

		if (consumer == null) {
			throw new IllegalArgumentException("Invalid status ... " + status);
		}

		animeService.alterUserAnimeDetail(animeId, consumer);

		return "redirect:/anime/" + animeId;
	}

	@GetMapping("/score/{animeId}/{score}")
	public String setAnimeScore(
			@PathVariable(name = "animeId") int animeId,
			@PathVariable(name = "score") int score) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setScore(score));
		
		return "redirect:/anime/" + animeId;
	}

	@GetMapping("/reset/{animeId}")
	public String resetAnime(
			@PathVariable(name = "animeId") int animeId) {
		
		animeService.reset(animeId);
		
		return "redirect:/anime/" + animeId;
	}
}
