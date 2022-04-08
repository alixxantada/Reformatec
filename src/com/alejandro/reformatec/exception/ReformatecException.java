package com.alejandro.reformatec.exception;

public class ReformatecException extends Exception {

	public ReformatecException() {
		super();
	}


	public ReformatecException(String message) {
		super(message);
	}


	public ReformatecException(Throwable cause) {
		super(cause);
	}


	public ReformatecException(String message, Throwable cause) {
		super(message, cause);
	}
}