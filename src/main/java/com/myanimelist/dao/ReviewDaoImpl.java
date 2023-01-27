package com.myanimelist.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Review;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.service.UserService;
import com.myanimelist.validation.entity.ValidReview;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserService userService;

	@Override
	public List<Review> findReviews(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<Review> query = session.createQuery(""
				+ "FROM Review "
				+ "WHERE anime_id = :theAnimeId", 
				Review.class)
			.setParameter("theAnimeId", animeId);
		
		return query.getResultList();
	}

	@Override
	public void save(ValidReview reviewForm) {
		Session session = entityManager.unwrap(Session.class);
		
		Review review = new Review(
				reviewForm.getAnimeId(),
				reviewForm.getContent(),
				userService.find(getAuthUsername())
			);
		
		session.save(review);
	}

	@Override
	public Review remove(int reviewId) {
		Session session = entityManager.unwrap(Session.class);
		
		Review review = session.get(Review.class, reviewId);
		
		if (review.getUser().getUsername().equals(getAuthUsername())) {
			session.remove(review);
		} else {
			throw new UserHasNoAccessException(""
					+ "User " + getAuthUsername()
					+ " can't remove " + review
					+ " belonging to " + review.getUser().getUsername());
		}
		
		return review;
	}

	private String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
