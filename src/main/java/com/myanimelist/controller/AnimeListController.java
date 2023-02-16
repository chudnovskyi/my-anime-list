package com.myanimelist.controller;

import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.PageableService;

@Controller
@RequestMapping("/list")
public class AnimeListController {

	private final AnimeService animeService;

	private final PageableService pageableService;

	@Autowired
	public AnimeListController(AnimeService animeService, PageableService pageableService) {
		this.animeService = animeService;
		this.pageableService = pageableService;
	}

	private static final Map<String, Predicate<UserAnimeDetail>> STATUS_PREDICATES = Map.of(
			"watching", UserAnimeDetail::isWatching,
			"planning", UserAnimeDetail::isPlanning,
			"finished", UserAnimeDetail::isCompleted,
			"on-hold", UserAnimeDetail::isOnHold,
			"dropped", UserAnimeDetail::isDropped,
			"favourite", UserAnimeDetail::isFavourite
		);

	@GetMapping
	public String list(
			@RequestParam(name = "status") String status,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size, Model model) {

		Predicate<UserAnimeDetail> predicate = STATUS_PREDICATES.get(status);

		if (predicate == null) {
			throw new IllegalArgumentException("Invalid status ... " + status);
		}

		Page<UserAnimeDetail> pageable = pageableService.getPageable(
				animeService.getUserAnimeDetailList(predicate),
				page,
				size
			);

		pageableService.preparePageableModel(model, pageable);

		model.addAttribute("tab", status);

		return "list-viewed";
	}
}
