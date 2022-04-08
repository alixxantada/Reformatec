package com.alejandro.reformatec.exception;

public class FavoritoNotFoundException extends FavoritoException {

	public FavoritoNotFoundException() {

	}

	public FavoritoNotFoundException(Throwable cause) {
		super(cause);
	}

	public FavoritoNotFoundException(String mensaje) {
		super(mensaje);
	}

	public FavoritoNotFoundException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}