package com.alejandro.reformatec.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.service.MailService;
import com.alejandro.reformatec.service.impl.MailServiceImpl;

public class MailServiceTest {

	private static Logger logger = LogManager.getLogger(MailServiceTest.class);	
	
	private MailService mailService = null;

	public MailServiceTest() {
		this.mailService = new MailServiceImpl();
	}



	public void testSendPlain() {
		logger.trace("Begin...");

		try {
			String from = "no-reply@gmail.com";
			String to = "alixxantada@gmail.com";
			mailService.sendEmail(from , "Test", "Testeando el mail service", to);

			logger.trace("Mail a "+to+": Enviado con éxito.");
		}catch (Exception e) {
			logger.error(e);			
		}
	}


	
	public static void main(String[] args) {
		MailServiceTest test = new MailServiceTest();
		test.testSendPlain();

	}
}


