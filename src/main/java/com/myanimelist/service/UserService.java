package com.myanimelist.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.myanimelist.entity.User;
import com.myanimelist.validation.entity.ValidUser;

public interface UserService extends UserDetailsService {

	public User findByUsername(String username);

	public void save(ValidUser user);
}
