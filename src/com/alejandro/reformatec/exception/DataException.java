package com.alejandro.reformatec.exception;

public class DataException extends ReformatecException {

	public DataException() {

	}
	
	public DataException(Throwable cause) {
		super(cause);
	}

	public DataException(String message) {
		super(message);
	}


	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

}

