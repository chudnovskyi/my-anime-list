package com.myanimelist.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping("/find-by-name/{pageId}")
	public String getAnimeByName(
			@ModelAttribute(name = "animeForm") Anime animeForm,
			@PathVariable(name = "pageId") int pageId,
			Model theModel) {
		
		String urlToFind = env.getProperty("find.name") + animeForm.getTitle() + "&page=" + pageId;
		
		logUrl(urlToFind);
		
		RestTemplate restTemplate = new RestTemplate();
		
		AnimeList animeList = restTemplate.getForObject(urlToFind, AnimeList.class);
		
		theModel.addAttribute("animeForm", animeForm);
		theModel.addAttribute("animeList", animeList.getData());
		theModel.addAttribute("pagination", animeList.getPagination());
		
		return "anime-list";
	}
	
	private void logUrl(String url) {
		logger.info("------------------------" + url + "------------------------");
	}
}
