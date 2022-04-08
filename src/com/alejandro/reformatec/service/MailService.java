package com.alejandro.reformatec.service;

import org.apache.commons.mail.EmailException;

import com.alejandro.reformatec.exception.ServiceException;

public interface MailService {

	public void sendEmail(String from, String subject, String text, String...to)
			throws ServiceException, EmailException;

}
