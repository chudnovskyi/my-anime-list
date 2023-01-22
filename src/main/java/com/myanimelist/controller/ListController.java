package com.myanimelist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.service.AnimeService;

@Controller
@RequestMapping("/list")
public class ListController {
	
	@Autowired
	private AnimeService animeService;

	@GetMapping("/viewed")
	public String viewed(
			Model theModel) {
		
		List<UserAnimeDetail> animeList = animeService.getViewedList();
		
		theModel.addAttribute("animeList", animeList);
		
		return "list-viewed";
	}
}
