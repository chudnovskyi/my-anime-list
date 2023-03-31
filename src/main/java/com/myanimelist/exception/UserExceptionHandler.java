package com.myanimelist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            TooManyRequests.class,
            WebClientResponseException.TooManyRequests.class
    })
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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<UserErrorResponse> handleHttpRequestMethodNotSupportedException() {

        UserErrorResponse error = new UserErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Something went wrong. Please, only use the in-app buttons to avoid this error.");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<UserErrorResponse> handleConstraintViolationException(Exception e) {

        UserErrorResponse error = new UserErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<UserErrorResponse> handleEntityNotFoundException(Exception e) {

        UserErrorResponse error = new UserErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
