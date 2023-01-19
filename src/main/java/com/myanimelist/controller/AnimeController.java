package com.myanimelist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.rest.entity.ResponseAnimeWrapper;
import com.myanimelist.service.AnimeService;
import com.myanimelist.validation.entity.ValidSearchAnime;

@Controller
@RequestMapping("/anime")
public class AnimeController {
	
	@Autowired
	private AnimeService animeService;
	
	@PostMapping("/find-by-name/{pageId}")
	public String findByName(
			@Valid @ModelAttribute("searchAnime") ValidSearchAnime searchAnime,
			BindingResult bindingResult,
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		if (bindingResult.hasErrors()) {
			return "redirect:/home?search&error";
		}
		
		ResponseAnimeWrapper wrapper = animeService.findByTitleAndPage(searchAnime.getTitle(), pageId);
		
		theModel.addAttribute("searchAnime", searchAnime);
		theModel.addAttribute("animeList", wrapper.getData());
		theModel.addAttribute("pagination", wrapper.getPagination());
		
		return "anime-list";
	}
}
