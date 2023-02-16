package com.myanimelist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(TooManyRequests.class)
	public ResponseEntity<UserErrorResponse> handleTooManyRequestsException() {

		UserErrorResponse error = new UserErrorResponse();

		error.setCode(HttpStatus.TOO_MANY_REQUESTS.value());
		error.setMessage("Too many requests ... Only 3 available per second, try again");
		error.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<UserErrorResponse> handleMaxUploadSizeExceededException() {

		UserErrorResponse error = new UserErrorResponse();

		error.setCode(HttpStatus.PAYLOAD_TOO_LARGE.value());
		error.setMessage("The image is more than 1 MB");
		error.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
	}
}
