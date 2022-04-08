package com.alejandro.reformatec.exception;

public class UserAlreadyExistsException extends UserException {

	public UserAlreadyExistsException() {

	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
