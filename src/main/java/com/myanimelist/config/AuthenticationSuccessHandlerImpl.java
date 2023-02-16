package com.myanimelist.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.myanimelist.entity.User;
import com.myanimelist.service.UserService;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private final UserService userService;

	@Autowired
	public AuthenticationSuccessHandlerImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		User authenticatedUser = userService.find(authentication.getName());

		request.getSession().setAttribute("authenticatedUser", authenticatedUser);
		
		response.sendRedirect(request.getContextPath() + "/home?profile");
	}
}
