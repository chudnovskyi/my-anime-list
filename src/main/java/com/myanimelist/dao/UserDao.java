package com.myanimelist.dao;

import com.myanimelist.entity.User;

public interface UserDao {

    public User findByUsername(String userName);
    
    public void save(User user);
}
