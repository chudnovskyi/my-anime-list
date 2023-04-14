package com.myanimelist.integration.service;

import com.myanimelist.integration.IntegrationTestBase;
import com.myanimelist.model.AnimeStatus;
import com.myanimelist.service.impl.AnimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static com.myanimelist.integration.service.UserServiceIT.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@WithMockUser(username = USERNAME)
public class AnimeServiceIT extends IntegrationTestBase {

    public static final Integer F_ANIME_ID = 1;
    public static final String F_ANIME_TITLE = "Cowboy Bebop";
    public static final boolean F_IS_FAVOURITE = true;
    public static final AnimeStatus F_STATUS = AnimeStatus.WATCHING;
    public static final Integer F_ANIME_SCORE = 1;

    public static final Integer S_ANIME_ID = 9999;
    public static final String S_ANIME_TITLE = "One Piece 3D: Mugiwara Chase";
    public static final boolean S_IS_FAVOURITE = false;
    public static final AnimeStatus S_STATUS = AnimeStatus.PLANNING;
    public static final Integer S_ANIME_SCORE = 2;

    private final AnimeServiceImpl animeService;

    @Test
    void getUserAnime() {
        assertThat(animeService.getUserAnime(F_ANIME_ID).getAnime().getTitle()).isEqualTo(F_ANIME_TITLE);
        assertThat(animeService.getUserAnime(F_ANIME_ID).getScore()).isEqualTo(F_ANIME_SCORE);
        assertThat(animeService.getUserAnime(S_ANIME_ID).getScore()).isEqualTo(S_ANIME_SCORE);
        assertThat(animeService.getUserAnime(2).getAnime()).isNull();
        assertThat(animeService.getUserAnime(-10)).isNotNull();
    }

    @Test
    void getUserAnimeListByStatus() {
        assertThat(animeService.getUserAnimeListByStatus(F_STATUS, null).getContent()).hasSize(1);
        assertThat(animeService.getUserAnimeListByStatus(F_STATUS, null).getContent().get(0).getAnime().getTitle()).isEqualTo(F_ANIME_TITLE);
        assertThat(animeService.getUserAnimeListByStatus(S_STATUS, null).getContent()).hasSize(1);
        assertThat(animeService.getUserAnimeListByStatus(S_STATUS, null).getContent().get(0).getAnime().getTitle()).isEqualTo(S_ANIME_TITLE);
    }

    @Test
    void getFavouriteUserAnimeList() {
        assertThat(animeService.getFavouriteUserAnimeList(F_IS_FAVOURITE, null).getContent().get(0).getAnime().getTitle()).isEqualTo(F_ANIME_TITLE);
        assertThat(animeService.getFavouriteUserAnimeList(S_IS_FAVOURITE, null).getContent().get(0).getAnime().getTitle()).isEqualTo(S_ANIME_TITLE);
    }

    @Test
    void updateUserAnime() {
        animeService.updateUserAnime(F_ANIME_ID, x -> x.setScore(10));
        animeService.updateUserAnime(F_ANIME_ID, x -> x.setFavourite(!x.isFavourite()));
        animeService.updateUserAnime(F_ANIME_ID, x -> x.setStatus(AnimeStatus.FINISHED));

        assertThat(animeService.getUserAnime(F_ANIME_ID).getScore()).isEqualTo(10);
        assertThat(animeService.getUserAnime(F_ANIME_ID).isFavourite()).isEqualTo(!F_IS_FAVOURITE);
        assertThat(animeService.getUserAnime(F_ANIME_ID).getStatus()).isEqualTo(AnimeStatus.FINISHED);
    }

    @Test
    void reset() {
        animeService.reset(F_ANIME_ID);

        assertThat(animeService.getUserAnime(F_ANIME_ID).getAnime()).isNull();
    }
}
