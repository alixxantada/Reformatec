package com.alejandro.reformatec.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.service.ContadorService;
import com.alejandro.reformatec.service.impl.ContadorServiceImpl;

public class ContadorServiceTest {

	private static Logger logger = LogManager.getLogger(ContadorServiceTest.class);

	private ContadorService contadorservice = null;

	public ContadorServiceTest() {
		contadorservice = new ContadorServiceImpl();
	}


	public void testCuentaClientes() {
		logger.trace("Begin...");	

		try {

			logger.info("Found: "+contadorservice.cliente());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}



	public void testCuentaProveedores() {
		logger.trace("Begin...");	

		try {

			logger.info("Found: "+contadorservice.proveedor());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}



	public void testCuentaProyectosActivos() {
		logger.trace("Begin...");	

		try {

			logger.info("Found: "+contadorservice.proyectoActivo());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}



	public void testCuentaProyectosFinalizados() {
		logger.trace("Begin...");	

		try {

			logger.info("Found: "+contadorservice.proyectoFinalizado());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}



	public void testCuentaTrabajosRealizados() {
		logger.trace("Begin...");	

		try {

			logger.info("Found: "+contadorservice.trabajoRealizado());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}
	


	public static void main (String args[])
			throws ServiceException, DataException {

		ContadorServiceTest test = new ContadorServiceTest();

		test.testCuentaClientes();
		test.testCuentaProveedores();
		test.testCuentaProyectosActivos();
		test.testCuentaProyectosFinalizados();
		test.testCuentaTrabajosRealizados();
	}
}
