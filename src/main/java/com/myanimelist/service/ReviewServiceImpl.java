package com.myanimelist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myanimelist.dao.ReviewDao;
import com.myanimelist.entity.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	
	@Override
	public List<Review> findReviewsByAnimeId(int animeId) {
		return reviewDao.findReviewsByAnimeId(animeId);
	}
}
