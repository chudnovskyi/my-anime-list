package com.myanimelist.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyRegistered;

@Repository
public class UserDaoImpl implements UserDao {
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private EntityManager entityManager;

	@Override
	public User findByUsername(String theUsername) {
		Session session = entityManager.unwrap(Session.class);

		Query<User> query = session
			.createQuery(""
				+ "FROM User "
				+ "WHERE username=:theUsername"
				, User.class)
			.setParameter("theUsername", theUsername);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			logger.info("=====>>> =====>>> USER NOT FOUND");
			user = null;
		}

		return user;
	}

	@Override
	public void save(User theUser) {
		Session currentSession = entityManager.unwrap(Session.class);

		logger.info("=====>>> =====>>> CHECK IF THE USERNAME IS ALREADY REGISTERED");
		User userToCheck = findByUsername(theUser.getUsername());
		
		if (userToCheck != null) {
			logger.info("=====>>> =====>>> THERE'S ALREADY USER WITH SUCH USERNAME");
			throw new UsernameAlreadyRegistered("username " + theUser.getUsername() + " already registered ...");
		}
		
		currentSession.saveOrUpdate(theUser);
	}
}
