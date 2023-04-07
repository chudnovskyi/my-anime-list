package com.myanimelist.service;

import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.function.Consumer;

public interface AnimeService {

    UserAnime getUserAnime(int animeId);

    Page<UserAnime> getUserAnimeListByStatus(AnimeStatus status, PageRequest score);

    Page<UserAnime> getFavouriteUserAnimeList(boolean isFavourite, PageRequest score);

    void updateUserAnime(int animeId, Consumer<UserAnime> consumer);

    void reset(int animeId);
}
