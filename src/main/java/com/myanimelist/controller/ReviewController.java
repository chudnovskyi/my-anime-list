package com.myanimelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myanimelist.service.ReviewService;
import com.myanimelist.validation.entity.ValidReview;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/add")
	public String add(
			@ModelAttribute(name = "reviewForm") ValidReview reviewForm,
			Model theModel) {
		
		reviewService.save(reviewForm);
		
		return "redirect:/anime/find/" + reviewForm.getAnimeId();
	}
}
