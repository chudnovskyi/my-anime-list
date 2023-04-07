package com.myanimelist.service;

import com.myanimelist.entity.Review;
import com.myanimelist.view.ReviewView;

import java.util.List;

public interface ReviewService {

    List<Review> getReviews(int animeId);

    void save(ReviewView reviewForm);

    void remove(Integer reviewId);
}
