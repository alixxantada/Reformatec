package com.alejandro.reformatec.exception;

public class ProyectoAlreadyExistsException extends ServiceException {

	public ProyectoAlreadyExistsException() {

	}

	public ProyectoAlreadyExistsException(String proyecto) {
		super(proyecto);
	}

	public ProyectoAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public ProyectoAlreadyExistsException(String proyecto, Throwable cause) {
		super(proyecto, cause);
	}
}
