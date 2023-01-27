package com.myanimelist.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myanimelist.service.UserService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

	@Autowired
	private UserService userService;

	@PostMapping("/save-image")
	public String saveUser(
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		userService.uploadProfilePicture(multipartFile.getBytes());
		
		return "redirect:/home?profile";
	}
}
