package com.alejandro.reformatec.exception;

public class CodeInvalidException extends UserException{

	public CodeInvalidException() {

	}

	public CodeInvalidException(String mensaje) {
		super(mensaje);
	}	

	public CodeInvalidException(Throwable cause) {
		super(cause);
	}

	public CodeInvalidException(String mensaje, Throwable cause) {
		super(mensaje, cause);
	}
}