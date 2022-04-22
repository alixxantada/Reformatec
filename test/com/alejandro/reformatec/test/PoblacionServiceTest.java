package com.alejandro.reformatec.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;
import com.alejandro.reformatec.service.PoblacionService;
import com.alejandro.reformatec.service.impl.PoblacionServiceImpl;

public class PoblacionServiceTest {

	private static Logger logger = LogManager.getLogger(PoblacionServiceTest.class);

	private PoblacionService poblacionservice = null;
	private PoblacionCriteria pc = new PoblacionCriteria();
	
	public PoblacionServiceTest() {
		poblacionservice = new PoblacionServiceImpl();
	}

	

	public void testfindByCriteria() {
		logger.trace("Begin...");	
		/////////////////////////////////
		pc.setIdPoblacion(null);
		pc.setIdProvincia(null);
		/////////////////////////////////
		try {

			List<PoblacionDTO> results = poblacionservice.findByCriteria(pc);
			logger.info("Found "+results.size());
			for (PoblacionDTO p: results) {
				logger.info(p);
			}

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(pc, de);
		}catch (ServiceException se) {
			logger.error(pc, se);
		}
	}



	public static void main (String args[])
			throws ServiceException, DataException {

		PoblacionServiceTest test = new PoblacionServiceTest();

		test.testfindByCriteria();
	}
}