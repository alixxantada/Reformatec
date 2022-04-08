package com.alejandro.reformatec.exception;

public class InvalidUserOrPasswordException extends ServiceException {

	public InvalidUserOrPasswordException() {

	}

	public InvalidUserOrPasswordException(String mensaje) {
		super(mensaje);
	}
	
	public InvalidUserOrPasswordException(Throwable cause) {
		super(cause);
	}
	
	public InvalidUserOrPasswordException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}
