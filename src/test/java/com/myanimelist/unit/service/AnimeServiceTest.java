package com.myanimelist.unit.service;

import com.myanimelist.entity.Anime;
import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import com.myanimelist.repository.AnimeRepository;
import com.myanimelist.repository.UserAnimeRepository;
import com.myanimelist.response.AnimeResponse;
import com.myanimelist.security.AuthenticationFacade;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.UserService;
import com.myanimelist.service.impl.AnimeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {

    @Mock
    private UserAnimeRepository userAnimeRepository;
    @Mock
    private AnimeRepository animeRepository;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private AnimeServiceImpl animeService;

    private UserAnime userAnime;
    private Anime anime;
    private PageRequest pageRequest;

    @BeforeEach
    public void setUp() {
        userAnime = new UserAnime();
        anime = new Anime();
        AnimeResponse.Anime animeResponse = new AnimeResponse.Anime();
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void testGetUserAnime() {
        int animeId = 1;
        String username = "testUser";

        when(authenticationFacade.getUsername()).thenReturn(username);
        when(userAnimeRepository.findByAnime_IdAndUser_Username(animeId, username)).thenReturn(Optional.of(userAnime));

        animeService.getUserAnime(animeId);

        verify(userAnimeRepository, times(1)).findByAnime_IdAndUser_Username(animeId, username);
    }

    @Test
    public void testGetUserAnimeListByStatus() {
        AnimeStatus status = AnimeStatus.WATCHING;
        String username = "testUser";

        when(authenticationFacade.getUsername()).thenReturn(username);
        when(userAnimeRepository.findAllByStatusAndUser_Username(status, username, pageRequest)).thenReturn(Page.empty());

        animeService.getUserAnimeListByStatus(status, pageRequest);

        verify(userAnimeRepository, times(1)).findAllByStatusAndUser_Username(status, username, pageRequest);
    }

    @Test
    public void testGetFavouriteUserAnimeList() {
        boolean isFavourite = true;
        String username = "testUser";

        when(authenticationFacade.getUsername()).thenReturn(username);
        when(userAnimeRepository.findAllByFavouriteAndUser_Username(isFavourite, username, pageRequest)).thenReturn(Page.empty());

        animeService.getFavouriteUserAnimeList(isFavourite, pageRequest);

        verify(userAnimeRepository, times(1)).findAllByFavouriteAndUser_Username(isFavourite, username, pageRequest);
    }

    @Test
    @WithMockUser
    public void testUpdateUserAnime() {
        int animeId = 1;
        String username = "testUser";
        Consumer<UserAnime> consumer = userAnime -> {
        };

        when(authenticationFacade.getUsername()).thenReturn(username);
        when(animeRepository.findById(animeId)).thenReturn(Optional.of(anime));
        when(userAnimeRepository.save(Mockito.any(UserAnime.class)))
                .thenAnswer(invocation -> {
                    UserAnime userAnime = invocation.getArgument(0);
                    userAnime.getAnime().setImage(null); // Set the image property to null
                    return userAnime;
                });

        animeService.updateUserAnime(animeId, consumer);

        verify(animeRepository, times(1)).findById(animeId);
    }
}