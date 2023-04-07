package com.myanimelist.controller;

import com.myanimelist.service.GenreService;
import com.myanimelist.service.UserService;
import com.myanimelist.view.AnimeView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GenreService genreService;
    private final UserService userService;

    @GetMapping("/home")
    public String homePage(
            Model model) {

        byte[] profilePicture = userService.getProfilePicture();

        if (profilePicture != null) {
            model.addAttribute("profilePicture", Base64.getEncoder().encodeToString(profilePicture));
        }

        model.addAttribute("date", new Date());
        model.addAttribute("animeView", new AnimeView());
        model.addAttribute("genres", genreService.retrieveAllGenres());

        return "home-page";
    }

    @GetMapping("/admin")
    public String adminPage(
            Model model) {

        model.addAttribute("date", new Date());

        return "admin-page";
    }

    @GetMapping("/github")
    public String github() {
        return "redirect:https://github.com/lwantPizza/my-anime-list";
    }
}
