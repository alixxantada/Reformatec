package com.alejandro.reformatec.exception;

public class FavoritoAlreadyExistsException extends FavoritoException {

	public FavoritoAlreadyExistsException() {

	}

	public FavoritoAlreadyExistsException(String mensaje) {
		super(mensaje);
	}	

	public FavoritoAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public FavoritoAlreadyExistsException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}
