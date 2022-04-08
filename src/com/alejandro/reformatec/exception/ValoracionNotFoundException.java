package com.alejandro.reformatec.exception;

public class ValoracionNotFoundException extends ServiceException {

	public ValoracionNotFoundException() {

	}

	public ValoracionNotFoundException(String message) {
		super(message);
	}

	public ValoracionNotFoundException(Throwable cause) {
		super(cause);
	}

	public ValoracionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}