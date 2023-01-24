package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.service.AnimeService;

@Controller
@RequestMapping("/list")
public class UserAnimeListController {
	
	@Autowired
	private AnimeService animeService;

	@GetMapping("/watching")
	public String watching(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimeWatchingList());
		
		return "list-viewed";
	}
	
	@GetMapping("/planning")
	public String planning(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimePlanningList());
		
		return "list-viewed";
	}
	
	@GetMapping("/finished")
	public String finished(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimeFinishedList());
		
		return "list-viewed";
	}
	
	@GetMapping("/hold-on")
	public String holdOn(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimeHoldOnList());
		
		return "list-viewed";
	}
	
	@GetMapping("/dropped")
	public String dropped(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimeDroppedList());
		
		return "list-viewed";
	}
	
	@GetMapping("/favourite")
	public String favourite(
			Model theModel) {
		
		theModel.addAttribute("animeList", animeService.getUserAnimeFavouriteList());
		
		return "list-viewed";
	}
}
