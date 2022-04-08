package com.alejandro.reformatec.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;
import com.alejandro.reformatec.service.EspecializacionService;
import com.alejandro.reformatec.service.impl.EspecializacionServiceImpl;

public class EspecializacionServiceTest {

	private static Logger logger = LogManager.getLogger(EspecializacionServiceTest.class);

	private EspecializacionService especializacionservice = null;
	private EspecializacionCriteria ec = new EspecializacionCriteria();

	public EspecializacionServiceTest() {
		especializacionservice = new EspecializacionServiceImpl();
	}




	public void testFindByCriteria() {
		logger.trace("Begin...");

		//////////////////////////////////////
		ec.setIdEspecializacion(null);
		ec.setIdProyecto(null);
		ec.setIdTrabajoRealizado(null);
		ec.setIdUsuario(null);

		/////////////////////////////////////
		try {

			List<Especializacion> lista = especializacionservice.findByCriteria(ec);
			logger.info("Found "+lista.size());
			for (Especializacion e: lista) {
				logger.info(e);
			}

			logger.trace("End");
		} catch(DataException de) {
			logger.error(de);
		}catch (ServiceException se) {
			logger.error( se);
		}
	}



	public static void main (String args[])
			throws ServiceException, DataException {

		EspecializacionServiceTest test = new EspecializacionServiceTest();

		test.testFindByCriteria();
	}
}