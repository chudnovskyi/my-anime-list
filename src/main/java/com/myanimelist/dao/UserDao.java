package com.myanimelist.dao;

import com.myanimelist.entity.User;

public interface UserDao {

	public User findByUsername(String userName);

	public User findByActivationCode(String code);

	public void save(User user);

	public void uploadProfilePicture(byte[] bytes);

	public byte[] getProfilePicture();
}
