package com.myanimelist.config;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.myanimelist.entity.User;
import com.myanimelist.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("<<<<<==========>>>>> AuthenticationSuccessHandler");

		User authenticatedUser = userService.findByUsername(authentication.getName());

		request.getSession().setAttribute("authenticatedUser", authenticatedUser);

		response.sendRedirect(request.getContextPath() + "/");
	}
}
