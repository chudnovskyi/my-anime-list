package com.myanimelist.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.service.UserService;
import com.myanimelist.validation.entity.ValidReview;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private UserService userService;

	@Override
	public List<Review> findReviewsByAnimeId(int animeId) {
		Session session = entityManager.unwrap(Session.class);

		Query<Review> query = session
			.createQuery(""
				+ "FROM Review "
				+ "WHERE anime_id=:theAnimeId", 
				Review.class)
			.setParameter("theAnimeId", animeId);
		
		List<Review> reviews = null;
		
		try {
			reviews = query.getResultList();
		} catch (NoResultException e) {
			logger.info("=====>>> =====>>> !!!!!!!!!! REVIEWS NOT FOUND !!!!!!!!!!");
			reviews = null;
		}
		
		return reviews;
	}

	@Override
	public void save(ValidReview reviewForm) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Review review = new Review(
				reviewForm.getAnimeId(),
				reviewForm.getContent(),
				userService.findByUsername(currentPrincipalName)
				);

		session.save(review);
		
		logger.info("USERNAME: " + review.getUser().getUsername() + " added review " + review.getContent());
	}

	@Override
	public Review remove(int reviewId) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Review review = session
				.get(Review.class, reviewId);
		
		if (review.getUser().getUsername().equals(currentPrincipalName)) {
			session.remove(review);
			logger.info("USERNAME: " + review.getUser().getUsername() + " DELETED REVIEW WITH ID " + reviewId);
		} else {
			throw new UserHasNoAccessException(""
					+ "User with username " + currentPrincipalName + " "
					+ "cannot remove review " + review + " "
					+ "belonging to " + review.getUser().getUsername());
		}
		
		return review;
	}
}
