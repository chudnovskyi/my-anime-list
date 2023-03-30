package com.myanimelist.controller;

import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.PageableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.function.Predicate;

import static com.myanimelist.model.AnimeStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class UserAnimeController {

    private final AnimeService animeService;
    private final PageableService<UserAnime> pageableService;

    private static final Map<AnimeStatus, Predicate<UserAnime>> STATUS_PREDICATES = Map.of(
            WATCHING, UserAnime::isWatching,
            PLANNING, UserAnime::isPlanning,
            FINISHED, UserAnime::isCompleted,
            ON_HOLD, UserAnime::isOnHold,
            DROPPED, UserAnime::isDropped,
            FAVOURITE, UserAnime::isFavourite
    );

    @GetMapping
    public String getAnimeListByStatus(
            @RequestParam(name = "status") AnimeStatus status,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size, Model model) {

        Predicate<UserAnime> predicate = STATUS_PREDICATES.get(status);

        if (predicate == null) {
            throw new IllegalArgumentException("Invalid status ... " + status);
        }

        Page<UserAnime> pageable = pageableService.getPageable(
                animeService.filterUserAnimeListByPredicate(predicate),
                page,
                size
        );

        pageableService.preparePageableModel(model, pageable);

        model.addAttribute("tab", status);

        return "list-viewed";
    }
}
