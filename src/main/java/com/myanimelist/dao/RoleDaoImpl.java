package com.myanimelist.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public Role findRoleByName(String theRole) {
		Session session = entityManager.unwrap(Session.class);

		Query<Role> query = session
			.createQuery(""
				+ "FROM Role "
				+ "WHERE name=:theRole", 
				Role.class)
			.setParameter("theRole", theRole);
		
		Role role = null;
		
		try {
			role = query.getSingleResult();
		} catch (NoResultException e) {
			logger.info("=====>>> =====>>> ROLE NOT FOUND");
			role = null;
		}
		
		return role;
	}
}
