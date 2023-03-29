package com.myanimelist.controller;

import com.myanimelist.entity.UserAnimeDetail;
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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.function.Consumer;

@Controller
@RequiredArgsConstructor
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;
    private final ReviewService reviewService;
    private final JikanApiService jikanApiService;

    private static final Map<String, Consumer<UserAnimeDetail>> STATUS_CONSUMER = Map.of(
            "watching", UserAnimeDetail::setAsWatching,
            "planning", UserAnimeDetail::setAsPlanning,
            "finished", UserAnimeDetail::setAsCompleted,
            "on-hold", UserAnimeDetail::setAsOnHold,
            "dropped", UserAnimeDetail::setAsDropped,
            "favourite", x -> x.setFavourite(!x.isFavourite())
    );

    @GetMapping("/{animeId}")
    public String searchById(
            @PathVariable(name = "animeId") int animeId,
            Model model) {

        Anime anime = jikanApiService.searchById(animeId);

        model.addAttribute("anime", anime);
        model.addAttribute("reviews", reviewService.retrieveList(animeId));
        model.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMalId()));

        if (!model.containsAttribute("reviewForm")) {
            model.addAttribute("reviewForm", new ReviewView(anime.getMalId()));
        }

        return "anime-details";
    }

    @GetMapping("/random")
    public Mono<String> searchRandom(
            Model model) {

        Mono<Anime> animeMono = jikanApiService.searchRandom();

        return animeMono.flatMap(anime -> {
            model.addAttribute("anime", anime);
            model.addAttribute("reviewForm", new ReviewView(anime.getMalId()));
            model.addAttribute("reviews", reviewService.retrieveList(anime.getMalId()));
            model.addAttribute("userAnimeDetail", animeService.getUserAnimeDetail(anime.getMalId()));

            return Mono.just("anime-details");
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Random anime not found")));
    }

    @PostMapping("/search/{pageId}")
    public Mono<String> search(
            @ModelAttribute("searchAnime") AnimeView searchAnime,
            @PathVariable(name = "pageId") int pageId,
            Model model) {

        Mono<AnimeListResponse> animeListMono = jikanApiService.search(searchAnime.getTitle(), searchAnime.getGenres(), pageId);

        return animeListMono.map(animeListResponse -> {
            model.addAttribute("searchAnime", searchAnime);
            model.addAttribute("animeList", animeListResponse.getAnimeList());
            model.addAttribute("pagination", animeListResponse.getPagination());

            return "anime-search";
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Anime list not found")));
    }

    @GetMapping("/top/{pageId}")
    public Mono<String> searchByRating(
            @PathVariable(name = "pageId") int pageId,
            Model model) {

        Mono<AnimeListResponse> animeListResponseMono = jikanApiService.searchByRating(pageId);

        return animeListResponseMono.flatMap(animeListResponse -> {
            model.addAttribute("animeList", animeListResponse.getAnimeList());
            model.addAttribute("pagination", animeListResponse.getPagination());

            return Mono.just("anime-top");
        }).switchIfEmpty(Mono.error(new EntityNotFoundException("Top anime list not found")));
    }

    @GetMapping("/set/{animeId}")
    public String setAnimeStatus(
            @PathVariable(name = "animeId") int animeId,
            @RequestParam(name = "status") String status) {

        Consumer<UserAnimeDetail> consumer = STATUS_CONSUMER.get(status);

        if (consumer == null) {
            throw new IllegalArgumentException("Invalid status ... " + status);
        }

        animeService.setUserAnimeDetail(animeId, consumer);

        return "redirect:/anime/" + animeId;
    }

    @GetMapping("/score/{animeId}/{score}")
    public String setAnimeScore(
            @PathVariable(name = "animeId") int animeId,
            @PathVariable(name = "score") int score) {

        animeService.setUserAnimeDetail(animeId, x -> x.setScore(score));

        return "redirect:/anime/" + animeId;
    }

    @GetMapping("/reset/{animeId}")
    public String resetAnime(
            @PathVariable(name = "animeId") int animeId) {

        animeService.reset(animeId);

        return "redirect:/anime/" + animeId;
    }
}
