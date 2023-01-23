package com.myanimelist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;


@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<UserErrorResponse> handleException(TooManyRequests exc) {
		
		UserErrorResponse error = new UserErrorResponse();
		
		error.setCode(HttpStatus.TOO_MANY_REQUESTS.value());
		error.setMessage("Too many requests ... Only 3 available per second, try again");
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
	}
}
