package com.alejandro.reformatec.exception;

public class TrabajoRealizadoNotFoundException extends ServiceException {

	public TrabajoRealizadoNotFoundException() {

	}

	public TrabajoRealizadoNotFoundException(String message) {
		super(message);
	}

	public TrabajoRealizadoNotFoundException(Throwable cause) {
		super(cause);
	}


	public TrabajoRealizadoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}