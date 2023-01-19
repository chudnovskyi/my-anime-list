package com.myanimelist.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.entity.AnimeList;

@Controller
@RequestMapping("/anime")
public class AnimeController {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private static final String URL_SEARCH_BY_NAME = "https://api.jikan.moe/v4/anime?q=";
	
	@GetMapping("/{animeName}")
	public String getAnimeById(
			@PathVariable(name = "animeName") String animeName,
			Model theModel) {
		
		String urlToFind = URL_SEARCH_BY_NAME + animeName;
		
		logger.info("------------------------" + urlToFind + "------------------------");
		
		RestTemplate restTemplate = new RestTemplate();
		
		AnimeList animeList = restTemplate.getForObject(urlToFind, AnimeList.class);
		
		logger.info("-----" + animeList.getData() + "-----");
		
		theModel.addAttribute("animeData", animeList.getData());
		
		return "anime-test";
	}
}
