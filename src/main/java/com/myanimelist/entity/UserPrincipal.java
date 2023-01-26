package com.myanimelist.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role: user.getRoles()) {
		    authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * The logic is this: 
	 * 
	 * When a user registers, a code is sent to his mail, the same 
	 * code is recorded in the database. When the user clicks on 
	 * the link in the mail, the code will be deleted from the 
	 * database, so the field will be null. Then the user will be 
	 * able to successfully log in
	 */
	@Override
	public boolean isEnabled() {
		return user.getActivationCode() == null;
	}
}
