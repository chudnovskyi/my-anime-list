package com.myanimelist.controller;

import java.util.logging.Logger;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.service.ReviewService;
import com.myanimelist.validation.entity.ValidReview;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@PostMapping("/add")
	public String add(
			@Valid @ModelAttribute(name = "reviewForm") ValidReview reviewForm,
			BindingResult bindingResult,
			RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.reviewForm", bindingResult);
		    attr.addFlashAttribute("reviewForm", reviewForm);
		    logger.info("<<-- bindingResult error creating review -->>");
		} else {
			reviewService.save(reviewForm);
		}
		
		return "redirect:/anime/find/" + reviewForm.getAnimeId();
	}
	
	@GetMapping("/remove/{reviewId}")
	public String remove(
			@PathVariable(name = "reviewId") int reviewId,
			Model theModel) {
		
		try {
			Review removedReview = reviewService.remove(reviewId);
			return "redirect:/anime/find/" + removedReview.getAnime_id();
		} catch (UserHasNoAccessException e) {
			logger.info(e.getMessage());
			theModel.addAttribute("userHasNoAccess", "You cannot delete someone else's review!!");
			return "home-page";
		}
	}
}
