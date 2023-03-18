package com.myanimelist.controller;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.ReviewService;
import com.myanimelist.view.AnimeView;
import com.myanimelist.view.ReviewView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Consumer;

@Controller
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeController {

	private final AnimeService animeService;
	private final ReviewService reviewService;
	private final JikanApiService jikanApiService;

	private static final Map<String, Consumer<UserAnimeDetail>> STATUS_CONSUMER = Map.of(
			"watching", UserAnimeDetail::setAsWatching,
			"planning", UserAnimeDetail::setAsPlanning,
			"finished", UserAnimeDetail::setAsCompleted,
			"on-hold", UserAnimeDetail::setAsOnHold,
			"dropped", UserAnimeDetail::setAsDropped,
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
			model.addAttribute("reviewForm", new ReviewView(anime.getMalId()));
		}
		
		return "anime-details";
	}
	
	@GetMapping("/random")
	public String getRandomAnime(
			Model model) {
		
		Anime anime = jikanApiService.findRandomAnime();
		
		model.addAttribute("anime", anime);
		model.addAttribute("reviewForm", new ReviewView(anime.getMalId()));
		model.addAttribute("reviews", reviewService.findReviews(anime.getMalId()));
		model.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMalId()));
		
		return "anime-details";
	}

	@PostMapping("/search/{pageId}")
	public String filteredSearch(
			@ModelAttribute("searchAnime") AnimeView searchAnime,
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
			@RequestParam(name = "status") String status) {

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
