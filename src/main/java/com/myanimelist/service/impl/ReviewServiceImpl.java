package com.myanimelist.service.impl;

import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.repository.ReviewRepository;
import com.myanimelist.security.AuthenticationFacade;
import com.myanimelist.service.ReviewService;
import com.myanimelist.service.UserService;
import com.myanimelist.view.ReviewView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<Review> getReviews(int animeId) {
        return reviewRepository.findAllByAnimeId(animeId);
    }

    @Override
    public void save(ReviewView reviewView) {
        Review review = Review.builder()
                .animeId(reviewView.getAnimeId())
                .content(reviewView.getContent())
                .user(userService.find(authenticationFacade.getUsername()))
                .build();

        reviewRepository.save(review);
    }

    @Override
    public void remove(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);

        if (!review.getUser().getUsername().equals(authenticationFacade.getUsername())) {
            throw new UserHasNoAccessException("User " + authenticationFacade.getUsername() + " can't remove " + review + " belonging to " + review.getUser().getUsername());
        }

        reviewRepository.deleteById(reviewId);
    }
}
