package com.myanimelist.service;

import java.util.List;

import com.myanimelist.entity.Review;
import com.myanimelist.validation.entity.ValidReview;

public interface ReviewService {

	List<Review> findReviews(int animeId);

	void save(ValidReview reviewForm);

	void remove(int reviewId);
}
