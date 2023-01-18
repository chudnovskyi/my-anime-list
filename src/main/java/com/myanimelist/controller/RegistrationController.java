package com.myanimelist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.exception.UsernameAlreadyRegistered;
import com.myanimelist.service.UserService;
import com.myanimelist.validation.entity.ValidUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;

	// pre-process all web requests (deletes white spaces)
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping
	public String register(
			Model theModel) {
		
		theModel.addAttribute("user", new ValidUser());
		
		return "register-form";
	}
	
	@PostMapping("/proccess")
	public String proccess(
			@Valid @ModelAttribute(name = "user") ValidUser user,
			BindingResult bindingResult,
			Model theModel) {
		
		if (bindingResult.hasErrors()) {
			return "register-form";
		}
		
		try {
			userService.save(user);
		} catch (UsernameAlreadyRegistered e) {
 			theModel.addAttribute("alreadyRegistered", "Username already registered!");
			return "register-form";
		}
		
		return "register-confirmation";
	}
}
