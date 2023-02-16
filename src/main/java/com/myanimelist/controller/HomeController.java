package com.myanimelist.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myanimelist.service.GenreService;
import com.myanimelist.service.UserService;
import com.myanimelist.validation.entity.ValidSearchAnime;

@Controller
public class HomeController {

	private final GenreService genreService;

	private final UserService userService;

	@Autowired
	public HomeController(GenreService genreService, UserService userService) {
		this.genreService = genreService;
		this.userService = userService;
	}

	@GetMapping("/home")
	public String home(
			Model model) {
		
		byte[] profilePicture = userService.getProfilePicture();
		
		if (profilePicture != null) {
			model.addAttribute("profilePicture", Base64.getEncoder().encodeToString(profilePicture));
		}
	
		model.addAttribute("theDate", new java.util.Date());
		model.addAttribute("searchAnime", new ValidSearchAnime());
		model.addAttribute("genres", genreService.findAllGenres());
		
		return "home-page";
	}

	@GetMapping("/admin")
	public String admin(
			Model model) {
		
		model.addAttribute("theDate", new java.util.Date());
		
		return "admin-page";
	}

	@GetMapping("/github")
	public String github() {
		
		return "redirect:https://github.com/lwantPizza/my-anime-list";
	}
}
