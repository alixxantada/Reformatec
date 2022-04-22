package com.alejandro.reformatec.exception;

public class EmailPendienteValidacionException extends ServiceException {

	public EmailPendienteValidacionException() {

	}

	public EmailPendienteValidacionException(String mensaje) {
		super(mensaje);
	}

	public EmailPendienteValidacionException(Throwable cause) {
		super(cause);
	}

	public EmailPendienteValidacionException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}

}
