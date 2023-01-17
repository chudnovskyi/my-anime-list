package com.myanimelist.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.dao.RoleDao;
import com.myanimelist.dao.UserDao;
import com.myanimelist.entity.Role;
import com.myanimelist.entity.User;
import com.myanimelist.enums.Roles;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUsername(String username) {
		logger.info("=====>>> =====>>> findByUsername(String username)");
		return userDao.findByUsername(username);
	}

	@Override
	@Transactional
	public void save(User user) {
		logger.info("=====>>> =====>>> save(User user)");
		String password = user.getPassword();
		
		user.setPassword(passwordEncoder.encode(password));

		user.setRoles(Arrays.asList(roleDao.findRoleByName(Roles.ROLE_USER.name())));

		userDao.save(user);
	}

	/*
	 * This method is used in spring security to validate any user(authentication) 
	 * which used by the spring security class DaoAuthenticationProvider. 
	 * When we get the user response that would be converted in the spring security 
	 * user class object with necessary details.
	 * 
	 * So It is typically called by an AuthenticationProvider instance in order to authenticate a user. 
	 * For example, when a username and password is submitted, 
	 * a UserDetailsService is called to find the password for that user 
	 * to see if it is correct.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUsername(userName);
		logger.info("=====>>> =====>>> loadUserByUsername");
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	// The code loops through all of the roles and create a new authorities based on info from the role.
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}
