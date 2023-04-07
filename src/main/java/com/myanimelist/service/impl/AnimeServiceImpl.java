package com.myanimelist.service.impl;

import com.myanimelist.entity.Anime;
import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import com.myanimelist.repository.AnimeRepository;
import com.myanimelist.repository.UserAnimeRepository;
import com.myanimelist.response.AnimeResponse;
import com.myanimelist.security.AuthenticationFacade;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    private final UserAnimeRepository userAnimeRepository;
    private final AnimeRepository animeRepository;
    private final JikanApiService jikanApiService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public UserAnime getUserAnime(int animeId) {
        return userAnimeRepository.findByAnime_IdAndUser_Username(animeId, authenticationFacade.getUsername())
                .orElse(new UserAnime());
    }

    @Override
    public Page<UserAnime> getUserAnimeListByStatus(AnimeStatus status, PageRequest pageRequest) {
        return userAnimeRepository.findAllByStatusAndUser_Username(
                status,
                authenticationFacade.getUsername(),
                pageRequest
        );
    }

    @Override
    public Page<UserAnime> getFavouriteUserAnimeList(boolean isFavourite, PageRequest pageRequest) {
        return userAnimeRepository.findAllByFavouriteAndUser_Username(
                isFavourite,
                authenticationFacade.getUsername(),
                pageRequest
        );
    }

    @Override
    public void updateUserAnime(int animeId, Consumer<UserAnime> consumer) {
        Anime anime = animeRepository.findById(animeId)
                .orElseGet(() -> {
                    AnimeResponse.Anime retrievedAnime = jikanApiService.searchById(animeId);
                    return Anime.builder()
                            .id(animeId)
                            .title(retrievedAnime.getTitle())
                            .image(retrievedAnime.getImages().getJpg().getImageUrl())
                            .build();
                });

        UserAnime userAnime = getUserAnime(animeId);

        userAnime.setUser(userService.find(authenticationFacade.getUsername()));
        userAnime.setAnime(anime);

        consumer.accept(userAnime);

        userAnimeRepository.save(userAnime);
    }

    @Override
    public void reset(int animeId) {
        userAnimeRepository.findAllByAnime_Id(animeId)
                .stream()
                .filter(x -> x.getUser().getUsername().equals(authenticationFacade.getUsername()))
                .findFirst()
                .ifPresent(userAnimeRepository::delete);
    }
}
