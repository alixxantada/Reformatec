package com.alejandro.reformatec.service.impl;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.util.ConfigurationManager;
import com.alejandro.reformatec.dao.util.ConstantConfigUtil;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.service.MailService;


public class MailServiceImpl implements MailService {

	private static final String CFGM_PFX = "service.mail.";
	private static final String SERVER = CFGM_PFX + "server";
	private static final String PORT = CFGM_PFX + "port";
	private static final String ACCOUNT = CFGM_PFX + "account";
	private static final String PASSWORD = CFGM_PFX + "password";


	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);

	public MailServiceImpl() {
	}

	@Override
	public void sendEmail(String from, String subject, String text, String...to)
			throws MailException {

		logger.traceEntry();
		
		ConfigurationManager cfgM = ConfigurationManager.getInstance();
		//cfgM.setKeyMap("Reformatec");
		
		
		try {

			if (logger.isDebugEnabled()) {
				logger.debug("Sending Email from "+from+" to "+to+"...");
			}
			
			Email email = new SimpleEmail();
			email.setHostName(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, SERVER));
			email.setSmtpPort(Integer.valueOf(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PORT)));
			email.setAuthenticator(new DefaultAuthenticator(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, ACCOUNT), cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PASSWORD)));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(text);
			email.addTo(to);
			email.send();
			
			logger.traceExit();

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error("Sending from "+from+" to "+to+"...", ee);
			}
			throw new MailException(ee.getMessage(), ee);
		}
	}


	public void sendHTML(String from, String subject, String htmlMessage, String... to)
			throws MailException{

		logger.traceEntry();

		ConfigurationManager cfgM = ConfigurationManager.getInstance();
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending HTML from "+from+" to "+to+"...");
			}
			HtmlEmail email = new HtmlEmail();
			email.setHostName(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, SERVER));
			email.setSmtpPort(Integer.valueOf(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PORT)));
			email.setAuthenticator(new DefaultAuthenticator(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, ACCOUNT),cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PASSWORD)));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setHtmlMsg(htmlMessage);
			email.addTo(to);
			email.send();

			logger.traceExit();

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error("Sending from "+from+" to "+to+"...", ee);
			}
			throw new MailException(ee.getMessage(), ee);
		}
	}
}