package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myanimelist.service.GenreService;
import com.myanimelist.validation.entity.ValidSearchAnime;

@Controller
public class HomeController {
	
	@Autowired
	private GenreService genreService;
	
	@GetMapping("/home")
	public String home(
			Model theModel) {
		
		theModel.addAttribute("theDate", new java.util.Date());

		theModel.addAttribute("searchAnime", new ValidSearchAnime());
		
		theModel.addAttribute("genres", genreService.findAllGenres());

		return "home-page";
	}

	@GetMapping("/admin")
	public String admin(
			Model theModel) {

		theModel.addAttribute("theDate", new java.util.Date());

		return "admin-page";
	}

	@GetMapping("/github")
	public String github() {

		return "redirect:https://github.com/lwantPizza";
	}
}
