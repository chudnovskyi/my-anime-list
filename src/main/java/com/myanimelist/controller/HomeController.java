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

	@Autowired
	private GenreService genreService;

	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String home(
			Model theModel) {
		
		byte[] profilePicture = userService.getProfilePicture();
		
		if (profilePicture != null) {
			theModel.addAttribute("profilePicture", Base64.getEncoder().encodeToString(profilePicture));
		}
	
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
		
		return "redirect:https://github.com/lwantPizza/my-anime-list";
	}
}
