package com.myanimelist.validation;

import java.util.logging.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private String firstField;
	private String secondField;
	private String message;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		this.firstField = constraintAnnotation.firstField();
		this.secondField = constraintAnnotation.secondField();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		boolean isValid = false;
		
		try {
			Object firstFieldObj = new BeanWrapperImpl(value).getPropertyValue(firstField);
			Object secondFieldObj = new BeanWrapperImpl(value).getPropertyValue(secondField);

			if (firstFieldObj == null && secondFieldObj == null) {
				isValid = true;
			} else if (firstFieldObj != null && firstFieldObj.equals(secondFieldObj)) {
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (Exception e) {
			logger.info("===>> EXCEPTION IN FieldMatchValidator");
		}

		if (!isValid) {
			context
				.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(firstField)
				.addConstraintViolation()
				.disableDefaultConstraintViolation();
		}
		
		return isValid;
	}
}
