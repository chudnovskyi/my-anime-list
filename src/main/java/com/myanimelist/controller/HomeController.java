package com.myanimelist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model theModel) {
		
		theModel.addAttribute("theDate", new java.util.Date());
		
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
