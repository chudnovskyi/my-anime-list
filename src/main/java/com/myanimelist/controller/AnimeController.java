package com.myanimelist.controller;

import com.myanimelist.model.AnimeStatus;
import com.myanimelist.response.AnimeListResponse;
import com.myanimelist.response.AnimeResponse.Anime;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.ReviewService;
import com.myanimelist.view.AnimeView;
import com.myanimelist.view.ReviewView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;
    private final ReviewService reviewService;
    private final JikanApiService jikanApiService;

    @GetMapping("/{animeId}")
    public String searchById(
            @PathVariable(name = "animeId") @Min(1) int animeId,
            Model model) {

        Anime anime;
        try {
            anime = jikanApiService.searchById(animeId);
        } catch (WebClientResponseException.NotFound e) {
            throw new EntityNotFoundException(e.getMessage());
        }

        model.addAttribute("anime", anime);
        model.addAttribute("reviews", reviewService.getReviews(animeId));
        model.addAttribute("userAnime", animeService.getUserAnime(anime.getMalId()));

        if (!model.containsAttribute("reviewView")) {
            model.addAttribute("reviewView", new ReviewView(anime.getMalId()));
        }

        return "anime/anime-details";
    }

    @GetMapping("/random")
    public Mono<String> getRandomAnime(
            Model model) {

        Mono<Anime> animeMono = jikanApiService.searchRandom();

        return animeMono.flatMap(anime -> {
            model.addAttribute("anime", anime);
            model.addAttribute("reviewView", new ReviewView(anime.getMalId()));
            model.addAttribute("reviews", reviewService.getReviews(anime.getMalId()));
            model.addAttribute("userAnime", animeService.getUserAnime(anime.getMalId()));

            return Mono.just("anime/anime-details");
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Random anime not found")));
    }

    @PostMapping("/search/{pageId}")
    public Mono<String> searchAnime(
            @ModelAttribute("searchAnime") AnimeView animeView,
            @PathVariable(name = "pageId") @Min(1) int pageId,
            Model model) {

        Mono<AnimeListResponse> animeListMono = jikanApiService.search(animeView.getTitle(), animeView.getGenres(), pageId);

        return animeListMono.map(animeListResponse -> {
            model.addAttribute("animeView", animeView);
            model.addAttribute("animeList", animeListResponse.getAnimeList());
            model.addAttribute("pagination", animeListResponse.getPagination());

            return "anime/anime-search";
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Anime list not found")));
    }

    @GetMapping("/top/{pageId}")
    public Mono<String> getTopRatedAnime(
            @PathVariable(name = "pageId") @Min(1) int pageId,
            Model model) {

        Mono<AnimeListResponse> animeListResponseMono = jikanApiService.searchByRating(pageId);

        return animeListResponseMono.flatMap(animeListResponse -> {
            model.addAttribute("animeList", animeListResponse.getAnimeList());
            model.addAttribute("pagination", animeListResponse.getPagination());

            return Mono.just("anime/anime-top");
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Top anime list not found")));
    }

    @GetMapping("/set/{animeId}")
    public String setAnimeStatus(
            @PathVariable(name = "animeId") @Min(1) int animeId,
            @RequestParam(name = "status") AnimeStatus status) {

        animeService.updateUserAnime(animeId, (x) -> x.setStatus(status));

        return "redirect:/anime/{animeId}";
    }

    @GetMapping("/set/{animeId}/favourite")
    public String setAnimeStatusAsFavourite(
            @PathVariable(name = "animeId") @Min(1) int animeId) {

        animeService.updateUserAnime(animeId, x -> x.setFavourite(!x.isFavourite()));

        return "redirect:/anime/{animeId}";
    }

    @GetMapping("/score/{animeId}/{score}")
    public String setUserAnimeScore(
            @PathVariable(name = "animeId") @Min(1) int animeId,
            @PathVariable(name = "score") @Min(0) @Max(10) int score) {

        animeService.updateUserAnime(animeId, x -> x.setScore(score));

        return "redirect:/anime/{animeId}";
    }

    @GetMapping("/reset/{animeId}")
    public String resetUserAnime(
            @PathVariable(name = "animeId") @Min(1) int animeId) {

        animeService.reset(animeId);

        return "redirect:/anime/{animeId}";
    }
}
