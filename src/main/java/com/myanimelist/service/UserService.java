package com.myanimelist.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.myanimelist.entity.User;
import com.myanimelist.validation.entity.ValidUser;

public interface UserService extends UserDetailsService {

	User find(String username);

	void save(ValidUser user);

	boolean activateUser(String code);

	void uploadProfilePicture(byte[] bytes);

	byte[] getProfilePicture();
}
