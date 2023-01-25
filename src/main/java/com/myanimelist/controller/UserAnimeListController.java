package com.myanimelist.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.service.AnimeService;

@Controller
@RequestMapping("/list")
public class UserAnimeListController {
	
	@Autowired
	private AnimeService animeService;

	@GetMapping("/watching")
	public String watching(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimeWatchingList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "watching");
		
		return "list-viewed";
	}
	
	@GetMapping("/planning")
	public String planning(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimePlanningList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "planning");
		
		return "list-viewed";
	}
	
	@GetMapping("/finished")
	public String finished(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimeFinishedList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "finished");
		
		return "list-viewed";
	}
	
	@GetMapping("/on-hold")
	public String onHold(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimeOnHoldList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "on-hold");
		
		return "list-viewed";
	}
	
	@GetMapping("/dropped")
	public String dropped(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimeDroppedList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "dropped");
		
		return "list-viewed";
	}
	
	@GetMapping("/favourite")
	public String favourite(
			Model theModel,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
		
		preparePegable(theModel, animeService.getUserAnimeFavouriteList(PageRequest.of(page - 1, size)));
		theModel.addAttribute("tab", "favourite");
		
		return "list-viewed";
	}
	
	private void preparePegable(Model theModel, Page<UserAnimeDetail> animePage) {
		theModel.addAttribute("animePage", animePage);
		
		int totalPages = animePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            theModel.addAttribute("pageNumbers", pageNumbers);
        }
	}
}
