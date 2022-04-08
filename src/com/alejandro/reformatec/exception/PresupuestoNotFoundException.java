package com.alejandro.reformatec.exception;


public class PresupuestoNotFoundException extends ServiceException {

	public PresupuestoNotFoundException() {

	}

	public PresupuestoNotFoundException(String message) {
		super(message);
	}

	public PresupuestoNotFoundException(Throwable cause) {
		super(cause);
	}


	public PresupuestoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

