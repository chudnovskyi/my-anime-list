package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.wrapper.ResponseAnimeWrapper;
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

	@PostMapping("/{pageId}")
	public String findByName(
			@ModelAttribute("searchAnime") ValidSearchAnime searchAnime,
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		ResponseAnimeWrapper wrapper = 
				jikanApiService.findSearched(searchAnime.getTitle(), searchAnime.getGenres(), pageId);
		
		theModel.addAttribute("searchAnime", searchAnime);
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		return "anime-search";
	}

	@GetMapping("/top/{pageId}")
	public String top(
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		ResponseAnimeWrapper wrapper = jikanApiService.findTop(pageId);
		
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		return "anime-top";
	}

	@GetMapping("/find/{animeId}")
	public String getAnimeById(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		Anime anime = jikanApiService.findAnime(animeId);
		
		theModel.addAttribute("anime", anime);
		theModel.addAttribute("reviews", reviewService.findReviews(animeId));
		theModel.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMal_id()));
		
		/*
		 * for correct working of validation in the reviewForm 
		 * field after redirect due to bindingResult error
		 */
		if (!theModel.containsAttribute("reviewForm")) {
			theModel.addAttribute("reviewForm", new ValidReview(anime.getMal_id()));
		}
		
		return "anime-details";
	}

	@GetMapping("/random")
	public String random(
			Model theModel) {
		
		Anime anime = jikanApiService.findRandomAnime();
		
		theModel.addAttribute("anime", anime);
		theModel.addAttribute("reviewForm", new ValidReview(anime.getMal_id()));
		theModel.addAttribute("reviews", reviewService.findReviews(anime.getMal_id()));
		theModel.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMal_id()));
		
		return "anime-details";
	}

	@GetMapping("/watching/{animeId}")
	public String setAnimeAsWatching(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setWatching(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/planning/{animeId}")
	public String setAnimeAsPlanning(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setPlanning(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/completed/{animeId}")
	public String setAnimeAsCompleted(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setCompleted(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/on-hold/{animeId}")
	public String setAnimeAsOnHold(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setOnHold(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/dropped/{animeId}")
	public String setAnimeAsDropped(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setDropped(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/favourite/{animeId}")
	public String setAnimeAsFavourite(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setFavourite(true));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/score/{score}/{animeId}")
	public String setAnimeScore(
			@PathVariable(name = "animeId") int animeId,
			@PathVariable(name = "score") int score,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setScore(score));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/score/reset/{animeId}")
	public String setAnimeScoreToZero(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.alterUserAnimeDetail(animeId, x -> x.setScore(0));
		
		return "redirect:/anime/find/" + animeId;
	}

	@GetMapping("/reset/{animeId}")
	public String reset(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		animeService.reset(animeId);
		
		return "redirect:/anime/find/" + animeId;
	}
}
