package com.myanimelist.dao;

import java.util.List;

import com.myanimelist.entity.Review;
import com.myanimelist.validation.entity.ValidReview;

public interface ReviewDao {

	public List<Review> findReviewsByAnimeId(int animeId);

	public void save(ValidReview reviewForm);
}
