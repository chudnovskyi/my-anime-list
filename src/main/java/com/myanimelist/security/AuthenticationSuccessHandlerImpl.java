package com.myanimelist.security;

import com.myanimelist.entity.User;
import com.myanimelist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final UserService userService;

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
