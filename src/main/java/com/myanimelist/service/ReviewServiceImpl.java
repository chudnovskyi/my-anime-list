package com.myanimelist.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.authentication.AuthenticationFacade;
import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.repository.ReviewRepository;
import com.myanimelist.validation.entity.ValidReview;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	
	private final UserService userService;
	
	private final AuthenticationFacade authenticationFacade;

	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService, AuthenticationFacade authenticationFacade) {
		this.reviewRepository = reviewRepository;
		this.userService = userService;
		this.authenticationFacade = authenticationFacade;
	}

	@Override
	public List<Review> findReviews(int animeId) {
		return reviewRepository.findAllByAnimeId(animeId);
	}

	@Override
	public void save(ValidReview validReview) {
		Review review = new Review();
		
		review.setAnimeId(validReview.getAnimeId());
		review.setContent(validReview.getContent());
		review.setUser(userService.find(authenticationFacade.getUsername()));
		
		reviewRepository.save(review);
	}

	@Override
	public void remove(Integer reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException());
		
		if (!review.getUser().getUsername().equals(authenticationFacade.getUsername())) {
			throw new UserHasNoAccessException("User " + authenticationFacade.getUsername() + " can't remove " + review + " belonging to " + review.getUser().getUsername());
		}

		reviewRepository.deleteById(reviewId);
	}
}
