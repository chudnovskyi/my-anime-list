package com.myanimelist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.service.UserService;
import com.myanimelist.utils.WebBindingUtils;
import com.myanimelist.validation.entity.ValidUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		WebBindingUtils.initBinder(dataBinder);
	}

	@GetMapping
	public String register(
			Model model) {
		
		model.addAttribute("user", new ValidUser());
		
		return "register-form";
	}

	@GetMapping("/activate/{code}")
	public String activate(
			@PathVariable(name = "code") String code,
			Model model) {
		
		boolean isActivated = userService.activeteUser(code);
		
		if (isActivated) {
			model.addAttribute("accountActivationSuccess", "User successfully activated");
		} else {
			model.addAttribute("accountActivationFailure", "Activation code not found ...");
		}
		
		return "login-form";
	}

	@PostMapping("/proccess")
	public String proccess(
			@Valid @ModelAttribute(name = "user") ValidUser user,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			return "register-form";
		}
		
		try {
			userService.save(user);
		} catch (UsernameAlreadyExistsException e) {
 			model.addAttribute("alreadyRegistered", "Username already registered!");
			return "register-form";
		}
		
		model.addAttribute("successfullyRegistered", "A verification email has been sent to: \n" + user.getEmail());
		return "login-form";
	}
}
