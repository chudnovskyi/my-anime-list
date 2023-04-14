package com.myanimelist.integration.service;

import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.integration.IntegrationTestBase;
import com.myanimelist.service.impl.ReviewServiceImpl;
import com.myanimelist.view.ReviewView;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.myanimelist.integration.service.AnimeServiceIT.F_ANIME_ID;
import static com.myanimelist.integration.service.AnimeServiceIT.S_ANIME_ID;
import static com.myanimelist.integration.service.UserServiceIT.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class ReviewServiceIT extends IntegrationTestBase {

    private final ReviewServiceImpl reviewService;

    @Test
    void getReviews() {
        List<Review> reviews1 = reviewService.getReviews(F_ANIME_ID);
        List<Review> reviews2 = reviewService.getReviews(S_ANIME_ID);

        assertThat(reviews1).hasSize(2);
        assertThat(reviews2).hasSize(1);
        assertThat(reviews2.get(0).getContent()).isEqualTo("comment2");
    }

    @Test
    @WithMockUser(username = USERNAME)
    void saveReview() {
        ReviewView reviewView = new ReviewView();
        reviewView.setAnimeId(F_ANIME_ID);
        reviewView.setContent("testContent");

        reviewService.save(reviewView);
        List<Review> reviews = reviewService.getReviews(1);

        assertThat(reviews).hasSize(3)
                .extracting(Review::getContent)
                .contains("testContent")
                .contains("comment1")
                .contains("comment3");
    }

    @Test
    @WithMockUser(username = USERNAME)
    void removeSuccess() {
        reviewService.getReviews(F_ANIME_ID).forEach(review -> reviewService.remove(review.getId()));

        assertThat(reviewService.getReviews(F_ANIME_ID)).isEmpty();
    }

    @Test
    @WithMockUser(username = "not_dummy")
    void removeFail() {
        assertThatThrownBy(() -> reviewService.remove(0))
                .isInstanceOf(EntityNotFoundException.class);

        assertThatThrownBy(() -> reviewService.remove(reviewService.getReviews(F_ANIME_ID).get(0).getId()))
                .isInstanceOf(UserHasNoAccessException.class);
    }
}
