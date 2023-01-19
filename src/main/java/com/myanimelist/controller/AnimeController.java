package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.ResponseAnimeWrapper;
import com.myanimelist.service.AnimeService;

@Controller
@RequestMapping("/anime")
public class AnimeController {
	
	@Autowired
	private AnimeService animeService;
	
	@PostMapping("/find-by-name/{pageId}")
	public String findByName(
			@ModelAttribute(name = "animeForm") Anime animeForm,
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		ResponseAnimeWrapper wrapper = animeService.findByNameAndPage(animeForm, pageId);
		
		theModel.addAttribute("animeForm", animeForm);
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		return "anime-list";
	}
}
