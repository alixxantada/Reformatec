package com.alejandro.reformatec.exception;

public class UserException extends ServiceException {

	public UserException() {
	}


	public UserException(String message) {
		super(message);
	}


	public UserException(Throwable cause) {
		super(cause);
	}


	public UserException(String message, Throwable cause) {
		super(message, cause);
	}
}
