package com.myanimelist.controller;

import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.service.UserService;
import com.myanimelist.util.WebBindingUtils;
import com.myanimelist.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

	private final UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		WebBindingUtils.initBinder(dataBinder);
	}

	@GetMapping
	public String register(
			Model model) {
		
		model.addAttribute("user", new UserView());
		
		return "register-form";
	}

	@GetMapping("/activate/{code}")
	public String activate(
			@PathVariable(name = "code") String code,
			Model model) {
		
		boolean isActivated = userService.activateUser(code);
		
		if (isActivated) {
			model.addAttribute("accountActivationSuccess", "User successfully activated");
		} else {
			model.addAttribute("accountActivationFailure", "Activation code not found ...");
		}
		
		return "login-form";
	}

	@PostMapping("/process")
	public String process(
			@Valid @ModelAttribute(name = "user") UserView user,
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
