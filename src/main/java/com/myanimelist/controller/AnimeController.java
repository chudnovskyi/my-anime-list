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
import com.myanimelist.validation.entity.ValidSearchAnime;

@Controller
@RequestMapping("/anime")
public class AnimeController {
	
	@Autowired
	private AnimeService animeService;
	
	@PostMapping("/{pageId}")
	public String findByName(
			@ModelAttribute("searchAnime") ValidSearchAnime searchAnime,
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		ResponseAnimeWrapper wrapper = 
				animeService.findSearched(searchAnime.getTitle(), searchAnime.getGenres(), pageId);
		
		theModel.addAttribute("searchAnime", searchAnime);
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		return "anime-search";
	}
	
	@GetMapping("/top/{pageId}")
	public String top(
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		ResponseAnimeWrapper wrapper = animeService.findTop(pageId);
		
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		/*
		 * I redirect to "anime-top" but not "anime-search" because
		 * idk how to correctly automatize one html form for two or more methods.
		 */
		return "anime-top";
	}
	
	@GetMapping("/find/{animeId}")
	public String getAnimeById(
			@PathVariable(name = "animeId") int animeId,
			Model theModel) {
		
		Anime anime = animeService.findAnimeById(animeId);
		
		theModel.addAttribute("anime", anime);
		
		return "anime-details";
	}
	
	@GetMapping("/random")
	public String random(
			Model theModel) {
		
		Anime anime = animeService.findRandomAnime();
		
		theModel.addAttribute("anime", anime);
		
		return "anime-details";
	}
}
