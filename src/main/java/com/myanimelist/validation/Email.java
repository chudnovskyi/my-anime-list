package com.myanimelist.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

	public String[] domains() default { "" };

	public String message() default "";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}
