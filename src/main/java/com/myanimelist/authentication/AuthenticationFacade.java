package com.myanimelist.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	
    Authentication getAuthentication();
    
    String getUsername();
}
