package com.myanimelist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenresResponse {

    @JsonProperty("data")
    private List<Genre> genres;

    @Data
    @NoArgsConstructor
    public static class Genre {

        @JsonProperty("mal_id")
        private int malId;
        private String name;
    }
}
