package com.alejandro.reformatec.exception;

public class ProyectoNotFoundException extends ServiceException {

	public ProyectoNotFoundException() {
		
	}
	
	public ProyectoNotFoundException(String message) {
		super(message);
	}

	public ProyectoNotFoundException(Throwable cause) {
		super(cause);
	}

	public ProyectoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}