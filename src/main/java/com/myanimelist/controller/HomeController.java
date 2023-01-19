package com.myanimelist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myanimelist.rest.entity.Anime;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model theModel) {
		
		theModel.addAttribute("theDate", new java.util.Date());
		
		/*
		 * I'm not sure that this is the best approach, but, I'm not the front end developer ;)
		 * The first approach, maybe, will be changed in the future
		 */
		theModel.addAttribute("animeForm", new Anime());
		
		return "home-page";
	}
	
	@GetMapping("/admin")
	public String admin(Model theModel) {
		
		theModel.addAttribute("theDate", new java.util.Date());
		
		return "admin-page";
	}
	
	@GetMapping("/github")
	public String github() {
		
		return "redirect:https://github.com/lwantPizza";
	}
}
