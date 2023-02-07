package com.myanimelist.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myanimelist.dto.AnimeResponse.Anime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimeListResponse {

	@JsonProperty("data")
	private List<Anime> animeList;

	private Pagination pagination;

	@Data
	@NoArgsConstructor
	public class Pagination {

		@JsonProperty("last_visible_page")
		private int lastVisiblePage;

		@JsonProperty("has_next_page")
		private boolean hasNextPage;

		@JsonProperty("current_page")
		private int currentPage;
	}
}
