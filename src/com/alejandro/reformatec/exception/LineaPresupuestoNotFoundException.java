package com.alejandro.reformatec.exception;

public class LineaPresupuestoNotFoundException extends ServiceException {

	public LineaPresupuestoNotFoundException() {

	}

	public LineaPresupuestoNotFoundException(String message) {
		super(message);
	}

	public LineaPresupuestoNotFoundException(Throwable cause) {
		super(cause);
	}


	public LineaPresupuestoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
