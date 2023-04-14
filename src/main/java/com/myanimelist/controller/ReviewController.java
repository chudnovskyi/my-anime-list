package com.myanimelist.controller;

import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.service.ReviewService;
import com.myanimelist.util.WebBindingUtils;
import com.myanimelist.view.ReviewView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        WebBindingUtils.initBinder(dataBinder);
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute(name = "reviewView") ReviewView reviewView,
            BindingResult bindingResult,
            RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.reviewView", bindingResult);
            attr.addFlashAttribute("reviewView", reviewView);
        } else {
            reviewService.save(reviewView);
        }

        return "redirect:/anime/" + reviewView.getAnimeId();
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

        return "redirect:/anime/{animeId}";
    }
}
