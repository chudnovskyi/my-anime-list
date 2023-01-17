package com.myanimelist.exception;

@SuppressWarnings("serial")
public class UsernameAlreadyRegistered extends RuntimeException {

	public UsernameAlreadyRegistered(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameAlreadyRegistered(String message) {
		super(message);
	}

	public UsernameAlreadyRegistered(Throwable cause) {
		super(cause);
	}
}
