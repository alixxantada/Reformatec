package com.alejandro.reformatec.service;

import org.apache.commons.mail.EmailException;

import com.alejandro.reformatec.exception.ServiceException;

public interface MailService {

	/**
	 * Método para enviar un email
	 * 
	 * @param from: Direccion de correo electronico desde la que se envía el mail(String).
	 * @param subject: El asunto del mail(String).
	 * @param text: El cuerpo del mail(String).
	 * @param to: A quien se envia el mail, indicar direcciones de correos electrónico(String).
	 * @throws ServiceException No contemplado.
	 * @throws EmailException Error al generar el mail.
	 */
	public void sendEmail(String from, String subject, String text, String...to)
			throws ServiceException, EmailException;

}
