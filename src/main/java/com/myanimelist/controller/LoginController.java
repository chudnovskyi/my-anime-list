package com.myanimelist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginForm() {
		return "login-form";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}
}
