package com.myanimelist.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.AnimeList;

@Controller
@RequestMapping("/anime")
public class AnimeController {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private Environment env;
	
	@PostMapping("/find-by-name")
	public String getAnimeByName(
			@ModelAttribute(name = "animeForm") Anime anime,
			Model theModel) {
		
		String urlToFind = env.getProperty("find.name") + anime.getTitle();
		
		logUrl(urlToFind);
		
		RestTemplate restTemplate = new RestTemplate();
		
		AnimeList animeList = restTemplate.getForObject(urlToFind, AnimeList.class);
		
		theModel.addAttribute("animeList", animeList.getData());
		
		return "anime-list";
	}
	
	private void logUrl(String url) {
		logger.info("------------------------" + url + "------------------------");
	}
}
