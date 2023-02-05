package com.myanimelist.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myanimelist.validation.Email;
import com.myanimelist.validation.FieldMatch;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@FieldMatch.List({
		@FieldMatch(firstField = "password", secondField = "matchingPassword", message = "{ConfirmPassword.Match}")
	})
public class ValidUser {

	@NotNull(message = "{Username.NotNull}")
	@Size(min = 3, message = "{Username.Size}")
	private String username;

	@NotNull(message = "{Password.NotNull}")
	@Size(min = 3, message = "{Password.Size}")
	private String password;

	@NotNull(message = "{ConfirmPassword.NotNull}")
	private String matchingPassword;

	@NotNull(message = "{Email.NotNull}")
	@Email(domains = { "gmail.com", "karazin.ua" }, message = "{Email.Domains}")
	private String email;
}
