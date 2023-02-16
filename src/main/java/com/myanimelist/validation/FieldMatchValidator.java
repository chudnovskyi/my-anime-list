package com.myanimelist.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

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

		boolean isValid;

		Object firstFieldObj = new BeanWrapperImpl(value).getPropertyValue(firstField);
		Object secondFieldObj = new BeanWrapperImpl(value).getPropertyValue(secondField);

		if (firstFieldObj == null && secondFieldObj == null) {
			isValid = true;
		} else isValid = firstFieldObj != null && firstFieldObj.equals(secondFieldObj);

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
