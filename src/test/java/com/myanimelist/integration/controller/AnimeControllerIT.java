package com.myanimelist.integration.controller;

import com.myanimelist.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.myanimelist.integration.service.AnimeServiceIT.F_ANIME_ID;
import static com.myanimelist.integration.service.UserServiceIT.USERNAME;
import static com.myanimelist.model.AnimeStatus.DROPPED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = USERNAME)
public class AnimeControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void resetUserAnime() throws Exception {
        mockMvc.perform(get("/anime/reset/{animeId}", F_ANIME_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/anime/{\\d+}")
                );

        mockMvc.perform(get("/anime/reset/{animeId}", -1))
                .andExpectAll(
                        status().is4xxClientError(),
                        jsonPath("$.message").value("resetUserAnime.animeId: must be greater than or equal to 1")
                );
    }

    @Test
    void setUserAnimeScore() throws Exception {
        mockMvc.perform(get("/anime/score/{animeId}/{score}", F_ANIME_ID, 10))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/anime/{\\d+}")
                );

        mockMvc.perform(get("/anime/score/{animeId}/{score}", F_ANIME_ID, -1))
                .andExpectAll(
                        status().is4xxClientError(),
                        jsonPath("$.message").value("setUserAnimeScore.score: must be greater than or equal to 0")
                );
    }

    @Test
    void setAnimeStatusAsFavourite() throws Exception {
        mockMvc.perform(get("/anime/set/{animeId}/favourite", F_ANIME_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/anime/{\\d+}")
                );

        mockMvc.perform(get("/anime/set/{animeId}/favourite", -1))
                .andExpectAll(
                        status().is4xxClientError(),
                        jsonPath("$.message").value("setAnimeStatusAsFavourite.animeId: must be greater than or equal to 1")
                );
    }

    @Test
    void setAnimeStatus() throws Exception {
        mockMvc.perform(get("/anime/set/{animeId}", F_ANIME_ID)
                        .param("status", DROPPED.name()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/anime/{\\d+}")
                );

        mockMvc.perform(get("/anime/set/{animeId}", F_ANIME_ID)
                        .param("status", "unknown_status"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("Invalid status: unknown_status")
                );

        mockMvc.perform(get("/anime/set/{animeId}", -1)
                        .param("status", DROPPED.name()))
                .andExpectAll(
                        status().is4xxClientError(),
                        jsonPath("$.message").value("setAnimeStatus.animeId: must be greater than or equal to 1")
                );
    }

    @Test
    void getTopRatedAnime() throws Exception {
        mockMvc.perform(get("/anime/top/{pageId}", 1))
                .andExpectAll(
                        status().is2xxSuccessful()
                );

        mockMvc.perform(get("/anime/top/{pageId}", -1))
                .andExpectAll(
                        status().is4xxClientError(),
                        jsonPath("$.message").value("getTopRatedAnime.pageId: must be greater than or equal to 1")
                );
    }
}
