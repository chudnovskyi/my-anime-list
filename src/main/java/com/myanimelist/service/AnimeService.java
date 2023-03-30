package com.myanimelist.service;

import com.myanimelist.entity.UserAnime;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AnimeService {

    UserAnime getUserAnime(int animeId);

    List<UserAnime> filterUserAnimeListByPredicate(Predicate<UserAnime> predicate);

    void updateUserAnime(int animeId, Consumer<UserAnime> consumer);

    void reset(int animeId);
}
