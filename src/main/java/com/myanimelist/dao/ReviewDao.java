package com.myanimelist.dao;

import java.util.List;

import com.myanimelist.entity.Review;

public interface ReviewDao {

	public List<Review> findReviewsByAnimeId(int animeId);
}
