package com.alejandro.reformatec.service;

import org.apache.commons.mail.EmailException;

import com.alejandro.reformatec.exception.ServiceException;

public interface MailService {

	/**
	 * M�todo para enviar un email
	 * 
	 * @param from: Direccion de correo electronico desde la que se env�a el mail(String).
	 * @param subject: El asunto del mail(String).
	 * @param text: El cuerpo del mail(String).
	 * @param to: A quien se envia el mail, indicar direcciones de correos electr�nico(String).
	 * @throws ServiceException No contemplado.
	 * @throws EmailException Error al generar el mail.
	 */
	public void sendEmail(String from, String subject, String text, String...to)
			throws ServiceException, EmailException;

	/**
	 * M�todo para enviar un email por html
	 * 
	 * @param from: Direccion de correo electronico desde la que se env�a el mail(String).
	 * @param subject: El asunto del mail(String).
	 * @param html: Message El cuerpo del mail(Html).
	 * @param to: A quien se envia el mail, indicar direcciones de correos electr�nico(String).
	 * @throws ServiceException No contemplado.
	 * @throws EmailException Error al generar el mail.
	 */
	public void sendHTML(String from, String subject, String htmlMessage, String... to)
			throws ServiceException, EmailException;
}
