package com.myanimelist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myanimelist.response.AnimeResponse.Anime;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnimeListResponse {

    @JsonProperty("data")
    private List<Anime> animeList;

    private Pagination pagination;

    @Data
    @NoArgsConstructor
    public static class Pagination {

        @JsonProperty("last_visible_page")
        private int lastVisiblePage;

        @JsonProperty("has_next_page")
        private boolean hasNextPage;

        @JsonProperty("current_page")
        private int currentPage;
    }
}
