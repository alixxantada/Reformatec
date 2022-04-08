package com.alejandro.reformatec.service.impl;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.service.MailService;


public class MailServiceImpl implements MailService {

	//TODO donde ponia la constante
	private static final String PASSWORD = "probando";

	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);

	public MailServiceImpl() {
	}

	@Override
	public void sendEmail(String from, String subject, String text, String...to)
			throws MailException {
		logger.trace("Begin");
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending Email from "+from+" to "+to+"...");
			}
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("pruebafpalex@gmail.com",PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(text);
			email.addTo(to);
			email.send();
			logger.debug("Email sent");
			logger.trace("End");
			
		} catch (EmailException ee) {
			logger.error("Sending from "+from+" to "+to+"...", ee);
			throw new MailException(ee.getMessage(), ee);
		}
	}


	public void sendHTML(String from, String subject, String htmlMessage, String... to)
			throws MailException{
		logger.trace("Begin");
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending HTML from "+from+" to "+to+"...");
			}
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("pruebafpalex@gmail.com",PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setHtmlMsg(htmlMessage);
			email.addTo(to);
			email.send();
			logger.debug("Html sent");
			logger.trace("End");
	} catch (EmailException ee) {
		logger.error("Sending from "+from+" to "+to+"...", ee);
		throw new MailException(ee.getMessage(), ee);
	}
	}
}