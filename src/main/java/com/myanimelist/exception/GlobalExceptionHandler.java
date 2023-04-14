package com.myanimelist.exception;

import com.myanimelist.model.AnimeStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            TooManyRequests.class,
            WebClientResponseException.TooManyRequests.class
    })
    public ResponseEntity<ErrorResponse> handleTooManyRequestsException() {

        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.TOO_MANY_REQUESTS.value());
        error.setMessage("Too many requests ... Only 3 available per second, try again");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException() {

        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.PAYLOAD_TOO_LARGE.value());
        error.setMessage("The image is more than 1 MB");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException() {

        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Something went wrong. Please, only use the in-app buttons to avoid this error.");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception e) {

        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception e) {

        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse error = new ErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(System.currentTimeMillis());

        if (e.getParameter().getParameterType().equals(AnimeStatus.class)) {
            error.setMessage("Invalid status: " + e.getValue());
        } else {
            error.setMessage("Bad request: " + e.getValue());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
