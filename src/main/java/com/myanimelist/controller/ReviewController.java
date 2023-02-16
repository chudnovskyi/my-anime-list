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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.service.ReviewService;
import com.myanimelist.utils.WebBindingUtils;
import com.myanimelist.validation.entity.ValidReview;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		WebBindingUtils.initBinder(dataBinder);
	}

	@PostMapping("/add")
	public String add(
			@Valid @ModelAttribute(name = "reviewForm") ValidReview reviewForm,
			BindingResult bindingResult,
			RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.reviewForm", bindingResult);
			attr.addFlashAttribute("reviewForm", reviewForm);
		} else {
			reviewService.save(reviewForm);
		}
		
		return "redirect:/anime/" + reviewForm.getAnimeId();
	}

	@GetMapping("/remove/{animeId}/{reviewId}")
	public String remove(
			@PathVariable(name = "reviewId") int reviewId,
			@PathVariable(name = "animeId") int animeId,
			Model model) {
		
		try {
			reviewService.remove(reviewId);
		} catch (UserHasNoAccessException e) {
			model.addAttribute("userHasNoAccess", "You cannot delete someone else's review!!");
			return "home-page";
		}
		
		return "redirect:/anime/" + animeId;
	}
}
