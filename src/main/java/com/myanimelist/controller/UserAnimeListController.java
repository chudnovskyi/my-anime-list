package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.PageableService;

@Controller
@RequestMapping("/list")
public class UserAnimeListController {

	@Autowired
	private AnimeService animeService;

	@Autowired
	private PageableService pageableService;

	@GetMapping("/watching")
	public String watching(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isWatching, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "watching");
		
		return "list-viewed";
	}

	@GetMapping("/planning")
	public String planning(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isPlanning, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "planning");
		
		return "list-viewed";
	}

	@GetMapping("/finished")
	public String finished(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isCompleted, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "finished");
		
		return "list-viewed";
	}

	@GetMapping("/on-hold")
	public String onHold(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isOnHold, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "on-hold");
		
		return "list-viewed";
	}

	@GetMapping("/dropped")
	public String dropped(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isDropped, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "dropped");
		
		return "list-viewed";
	}

	@GetMapping("/favourite")
	public String favourite(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		Page<UserAnimeDetail> pageable = animeService.getUserAnimeDetailList(UserAnimeDetail::isFavourite, page, size);
		
		pageableService.preparePegableModel(theModel, pageable);
		
		theModel.addAttribute("tab", "favourite");
		
		return "list-viewed";
	}
}
