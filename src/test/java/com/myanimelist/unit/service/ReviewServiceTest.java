package com.myanimelist.unit.service;

import com.myanimelist.entity.Review;
import com.myanimelist.entity.User;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.repository.ReviewRepository;
import com.myanimelist.security.AuthenticationFacade;
import com.myanimelist.service.UserService;
import com.myanimelist.service.impl.ReviewServiceImpl;
import com.myanimelist.view.ReviewView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;

    @BeforeEach
    public void setUp() {
        review = getReview();
    }

    @Test
    void findReviewsTest() {
        doReturn(List.of(review)).when(reviewRepository).findAllByAnimeId(review.getAnimeId());

        assertThat(reviewService.getReviews(review.getAnimeId())).hasSize(1);
        assertThat(reviewService.getReviews(review.getAnimeId()).get(0)).isEqualTo(review);
    }

    @Test
    void findNoReviewsTest() {
        doReturn(List.of()).when(reviewRepository).findAllByAnimeId(anyInt());

        assertThat(reviewService.getReviews(anyInt())).isEmpty();
    }

    @Test
    public void saveTest() {
        Review review = getReview();

        ReviewView reviewView = new ReviewView();
        reviewView.setAnimeId(review.getAnimeId());
        reviewView.setContent(review.getContent());

        doReturn(review.getUser().getUsername()).when(authenticationFacade).getUsername();
        doReturn(review.getUser()).when(userService).find(review.getUser().getUsername());

        reviewService.save(reviewView);

        verify(reviewRepository, times(1)).save(review);
        verify(authenticationFacade, times(1)).getUsername();
        verify(userService, times(1)).find(review.getUser().getUsername());
    }

    @Test
    void removeSuccess() {
        doReturn(Optional.of(review)).when(reviewRepository).findById(review.getId());
        doReturn(review.getUser().getUsername()).when(authenticationFacade).getUsername();

        reviewService.remove(review.getId());

        verify(reviewRepository).deleteById(review.getId());
    }

    @Test
    void removeFail() {
        assertAll(() -> assertThrows(EntityNotFoundException.class, () -> reviewService.remove(Integer.MAX_VALUE)), () -> assertThrows(EntityNotFoundException.class, () -> reviewService.remove(null)), () -> {
            doReturn(Optional.of(review)).when(reviewRepository).findById(review.getId());
            doReturn("dummy").when(authenticationFacade).getUsername();

            assertThrows(UserHasNoAccessException.class, () -> reviewService.remove(review.getId()));
        });
    }

    private Review getReview() {
        Review review = new Review();
        review.setAnimeId(1);
        review.setContent("nice");
        review.setUser(getUser());
        return review;
    }

    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setEmail("user@gmail.com");
        user.setPassword("$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC");
        user.setActivationCode("activate");
        user.setReviews(Collections.emptyList());
        return user;
    }
}
