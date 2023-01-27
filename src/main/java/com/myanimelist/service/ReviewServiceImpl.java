package com.myanimelist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.dao.ReviewDao;
import com.myanimelist.entity.Review;
import com.myanimelist.validation.entity.ValidReview;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	@Override
	@Transactional
	public List<Review> findReviews(int animeId) {
		return reviewDao.findReviews(animeId);
	}

	@Override
	@Transactional
	public void save(ValidReview reviewForm) {
		reviewDao.save(reviewForm);
	}

	@Override
	@Transactional
	public Review remove(int reviewId) {
		return reviewDao.remove(reviewId);
	}
}
