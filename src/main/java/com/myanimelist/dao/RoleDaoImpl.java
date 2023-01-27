package com.myanimelist.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Role findRole(String theRole) {
		Session session = entityManager.unwrap(Session.class);
		
		Query<Role> query = session.createQuery(""
				+ "FROM Role "
				+ "WHERE name = :theRole", 
				Role.class)
			.setParameter("theRole", theRole);
		
		Role role = null;
		
		try {
			role = query.getSingleResult();
		} catch (NoResultException e) {
			role = null;
		}
		
		return role;
	}
}
