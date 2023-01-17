package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyRegistered;
import com.myanimelist.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String register(
			Model theModel) {
		
		theModel.addAttribute("user", new User());
		
		return "register-form";
	}
	
	@PostMapping("/proccess")
	public String proccess(
			@ModelAttribute(name = "user") User user) {
		
		try {
			userService.save(user);
		} catch (UsernameAlreadyRegistered e) {
			return "register-failure";
		}
		
		return "register-confirmation";
	}
}
