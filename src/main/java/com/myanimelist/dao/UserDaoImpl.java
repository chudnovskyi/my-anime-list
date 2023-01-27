package com.myanimelist.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.service.UserService;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;

	@Lazy
	@Autowired
	private UserService userService;

	@Override
	public User findByUsername(String theUsername) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<User> query = session.createQuery(""
				+ "FROM User "
				+ "WHERE username = :theUsername", 
				User.class)
			.setParameter("theUsername", theUsername);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public User findByActivationCode(String code) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<User> query = session.createQuery(""
				+ "FROM User "
				+ "WHERE activationCode = :theActivationCode", 
				User.class)
			.setParameter("theActivationCode", code);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public void save(User theUser) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		User user = findByUsername(theUser.getUsername());
		
		if (user != null) {
			throw new UsernameAlreadyExistsException("Username " + theUser.getUsername() + " already exists!");
		}
		
		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public void uploadProfilePicture(byte[] bytes) {
		User user = userService.find(getAuthUsername());
		
		user.setImage(bytes);
	}

	@Override
	public byte[] getProfilePicture() {
		Session session = entityManager.unwrap(Session.class);
		
		Query<User> query = session.createQuery(""
				+ "FROM User "
				+ "WHERE username = :theUsername", 
				User.class)
			.setParameter("theUsername", getAuthUsername());
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user.getImage();
	}

	private String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
