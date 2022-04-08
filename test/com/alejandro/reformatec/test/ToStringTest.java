package com.alejandro.reformatec.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.model.UsuarioDTO;

public class ToStringTest {
	private static Logger logger = LogManager.getLogger(ToStringTest.class);
	
	public static void main(String args[] ) {
		logger.trace("Begin...");
		UsuarioDTO p = new UsuarioDTO();
		p.setIdUsuario(2L);
		p.setNif("25252525L");
		p.setDireccionWeb("www.asfd.com");
		logger.info("Found: "+p);
		logger.trace("End");
	}
}
