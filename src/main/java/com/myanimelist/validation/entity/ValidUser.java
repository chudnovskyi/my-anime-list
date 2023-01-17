package com.myanimelist.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myanimelist.validation.Email;

/* 
 * I use a separate entity class for 
 * validation to avoid annotation buildup 
 * and make the code more maintainable.
 * 
 * Initially, instances of this object 
 * will be transferred into html form, 
 * and after validation, at the service 
 * layer, all fields will be transferred 
 * to the "real" User object.
 */
public class ValidUser {

	@NotNull(message = "{Username.NotNull}")
	@Size(min = 3, message = "{Username.Size}")
	private String username;

	@NotNull(message = "{Password.NotNull}")
	@Size(min = 3, message = "{Password.Size}")
	private String password;

	@NotNull(message = "{Email.NotNull}")
	@Email(suffix = {"gmail.com", "karazin.ua"}, 
			message = "{Email.domains}")
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
