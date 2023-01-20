package com.myanimelist.service;

import java.util.List;

import com.myanimelist.entity.Review;

public interface ReviewService {

	public List<Review> findReviewsByAnimeId(int animeId);
}
