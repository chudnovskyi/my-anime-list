package com.myanimelist.service;

import java.util.List;

import com.myanimelist.entity.Review;
import com.myanimelist.validation.entity.ValidReview;

public interface ReviewService {

	public List<Review> findReviewsByAnimeId(int animeId);

	public void save(ValidReview reviewForm);

	public Review remove(int reviewId);
}
