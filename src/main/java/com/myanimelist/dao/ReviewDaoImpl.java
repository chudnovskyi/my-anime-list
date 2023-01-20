package com.myanimelist.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Review;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private EntityManager entityManager;

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
}
