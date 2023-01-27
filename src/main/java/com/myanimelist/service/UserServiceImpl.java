package com.myanimelist.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.dao.RoleDao;
import com.myanimelist.dao.UserDao;
import com.myanimelist.entity.User;
import com.myanimelist.entity.UserPrincipal;
import com.myanimelist.validation.entity.ValidUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MailSenderServiceImpl mailSenderService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Environment env;

	@Override
	@Transactional
	public User find(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	@Transactional
	public void save(ValidUser validUser) {
		User user = new User();

		user.setUsername(validUser.getUsername());
		user.setPassword(passwordEncoder.encode(validUser.getPassword()));
		user.setEmail(validUser.getEmail());
		user.setRoles(Arrays.asList(roleDao.findRole("ROLE_USER")));
		user.setActivationCode(UUID.randomUUID().toString());

		userDao.save(user);

		String message = String.format("" +
				"Hello, %s! \n" + 
				"Welcome to MyAnimeList. Please, follow link to verify your account: \n" +
				"%s/register/activate/%s",
				env.getProperty("host.domain"),
				user.getUsername(), 
				user.getActivationCode());

		mailSenderService.send(user.getEmail(), "Activation code", message);
	}

	@Override
	@Transactional
	public boolean activeteUser(String code) {
		User user = userDao.findByActivationCode(code);

		if (user != null) {
			user.setActivationCode(null);
			return true;
		}

		return false;
	}

	@Override
	@Transactional
	public void uploadProfilePicture(byte[] bytes) {
		userDao.uploadProfilePicture(bytes);
	}

	@Override
	@Transactional
	public byte[] getProfilePicture() {
		return userDao.getProfilePicture();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserPrincipal(user);
	}
}
