package com.alejandro.reformatec.exception;

public class FavoritoException extends ServiceException {

	public FavoritoException() {
	}


	public FavoritoException(String message) {
		super(message);
	}


	public FavoritoException(Throwable cause) {
		super(cause);
	}


	public FavoritoException(String message, Throwable cause) {
		super(message, cause);
	}
}