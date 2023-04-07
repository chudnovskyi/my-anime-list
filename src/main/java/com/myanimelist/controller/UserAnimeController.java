package com.myanimelist.controller;

import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import com.myanimelist.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/list")
public class UserAnimeController {

    private final AnimeService animeService;

    @GetMapping
    public String getAnimeList(
            @RequestParam(name = "status", required = false) AnimeStatus status,
            @RequestParam(name = "favourite", required = false, defaultValue = "false") Boolean isFavourite,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") @Min(1) Integer size,
            Model model) {

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("score").descending().and(Sort.by("anime.title")));

        Page<UserAnime> userAnimePage;
        if (isFavourite) {
            userAnimePage = animeService.getFavouriteUserAnimeList(true, pageRequest);
        } else {
            userAnimePage = animeService.getUserAnimeListByStatus(status, pageRequest);
        }

        model.addAttribute("content", userAnimePage.getContent());
        model.addAttribute("page", userAnimePage);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, userAnimePage.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);

        return "anime/anime-list";
    }
}
