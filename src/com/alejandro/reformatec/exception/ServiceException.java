package com.alejandro.reformatec.exception;

public class ServiceException extends ReformatecException {

	public ServiceException() {
		
	}


	public ServiceException(String message) {
		super(message);
	}


	public ServiceException(Throwable cause) {
		super(cause);
	}


	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
