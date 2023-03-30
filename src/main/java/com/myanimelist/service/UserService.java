package com.myanimelist.service;

import com.myanimelist.entity.User;
import com.myanimelist.view.UserView;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User find(String username);

    void save(UserView user);

    boolean activateUser(String code);

    void uploadProfilePicture(byte[] bytes);

    byte[] getProfilePicture();
}
