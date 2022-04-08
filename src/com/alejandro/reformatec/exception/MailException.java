package com.alejandro.reformatec.exception;

public class MailException extends ServiceException {

	public MailException() {

	}

	public MailException(String mail) {
		super(mail);
	}

	public MailException(Throwable cause) {
		super(cause);
	}

	public MailException(String mail, Throwable cause) {
		super(mail, cause);
	}

}