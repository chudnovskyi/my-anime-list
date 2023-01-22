package com.myanimelist.exception;

public class UserHasNoAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserHasNoAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserHasNoAccessException(String message) {
		super(message);
	}

	public UserHasNoAccessException(Throwable cause) {
		super(cause);
	}
}
