package com.alejandro.reformatec.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Provincia;
import com.alejandro.reformatec.service.ProvinciaService;
import com.alejandro.reformatec.service.impl.ProvinciaServiceImpl;

public class ProvinciaServiceTest {

	private static Logger logger = LogManager.getLogger(ProvinciaServiceTest.class);

	private ProvinciaService provinciaservice = null;

	public ProvinciaServiceTest() {
		provinciaservice = new ProvinciaServiceImpl();
	}


	public void testFindByCriteria() {

		logger.trace("Begin...");	
		/////////////////////////////////
		Integer idProvincia = null;
		/////////////////////////////////
		try {

			List<Provincia> results = provinciaservice.findByCriteria(idProvincia);
			logger.info("Found "+results.size());
			for (Provincia p: results) {
				logger.info(p);
			}

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(idProvincia, de);
		}catch (ServiceException se) {
			logger.error(idProvincia, se);
		}
	}

	public static void main (String args[])
			throws ServiceException, DataException {

		ProvinciaServiceTest test = new ProvinciaServiceTest();

		test.testFindByCriteria();
	}
}
