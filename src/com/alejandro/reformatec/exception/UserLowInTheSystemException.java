package com.alejandro.reformatec.exception;

public class UserLowInTheSystemException extends UserException {


	public UserLowInTheSystemException() {

	}

	public UserLowInTheSystemException(String message) {
		super(message);
	}

	public UserLowInTheSystemException(Throwable cause) {
		super(cause);
	}

	public UserLowInTheSystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
