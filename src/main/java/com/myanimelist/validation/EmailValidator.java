package com.myanimelist.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
	
	private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	public static Pattern emailRegexPatter;
	
	private String suffixes[]; 
	
	static {
		emailRegexPatter = Pattern.compile(emailRegex);
	}

	@Override
	public void initialize(Email constraintAnnotation) {
		suffixes = constraintAnnotation.suffix();
	}

	@Override
	public boolean isValid(String code, ConstraintValidatorContext theConstraintValidatorContext) {
		
		boolean result = false;
		
		if (code != null) {
			for (String suffix : suffixes) {
				result = emailRegexPatter.matcher(code).matches() && code.endsWith(suffix);
				
				if (result) {
					break;
				}
			}
		} else {
			result = true;
		}
		
		return result;
	}
}
