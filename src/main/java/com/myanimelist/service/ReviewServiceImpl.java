package com.myanimelist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.authentication.AuthenticationFacade;
import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.repository.ReviewRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.validation.entity.ValidReview;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@Override
	public List<Review> findReviews(int animeId) {
		return reviewRepository.findAllByAnimeId(animeId);
	}

	@Override
	public void save(ValidReview reviewForm) {
		Review review = new Review();
		
		review.setAnimeId(reviewForm.getAnimeId());
		review.setContent(reviewForm.getContent());
		review.setUser(userRepository.findByUsername(authenticationFacade.getUsername()));
		
		reviewRepository.save(review);
	}

	@Override
	public void remove(int reviewId) {
		Review review = reviewRepository.getReferenceById(reviewId);
		
		if (!review.getUser().getUsername().equals(authenticationFacade.getUsername())) {
			throw new UserHasNoAccessException("User " + authenticationFacade.getUsername() + " can't remove " + review + " belonging to " + review.getUser().getUsername());
		}
		
		reviewRepository.deleteById(reviewId);
	}
}
