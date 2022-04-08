package com.alejandro.reformatec.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PoblacionDTO;
import com.alejandro.reformatec.service.PoblacionService;
import com.alejandro.reformatec.service.impl.PoblacionServiceImpl;

public class PoblacionServiceTest {

	private static Logger logger = LogManager.getLogger(PoblacionServiceTest.class);

	private PoblacionService poblacionservice = null;

	public PoblacionServiceTest() {
		poblacionservice = new PoblacionServiceImpl();
	}



	

	public void testfindByCriteria() {
		logger.trace("Begin...");	
		/////////////////////////////////
		Integer idProvincia = 1;
		/////////////////////////////////
		try {

			List<PoblacionDTO> results = poblacionservice.findByCriteria(idProvincia);
			logger.info("Found "+results.size());
			for (PoblacionDTO p: results) {
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

		PoblacionServiceTest test = new PoblacionServiceTest();

		test.testfindByCriteria();
	}
}