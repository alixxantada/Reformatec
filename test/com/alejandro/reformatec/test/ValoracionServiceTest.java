package com.alejandro.reformatec.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;
import com.alejandro.reformatec.service.ValoracionService;
import com.alejandro.reformatec.service.impl.ValoracionServiceImpl;

public class ValoracionServiceTest {

	private static Logger logger = LogManager.getLogger(ValoracionServiceTest.class);
	// lo primero que vamos hacer es un atributo para dar visibilidad a UsuarioService en vez de dar visibilidad de la implementacion del codigo 
	private ValoracionService valoracionservice = null;
	private Results<ValoracionDTO> results = new Results<ValoracionDTO>();
	ValoracionCriteria vc = new ValoracionCriteria();

	public ValoracionServiceTest() {
		valoracionservice = new ValoracionServiceImpl();
	}


	public void testfindByCriteria() {
		logger.trace("Begin...");

		///////////////////////////////
		Long idValoracion = null;
		Long idTrabajoRealizadoValorado = null;
		Long idProveedorValorado = null;
		Long idUsuarioValoraTrabajo = null;
		Long idUsuarioValoraProveedor= null;
		///////////////////////////////
		try {
			
			int startIndex = 1;
			int pageSize = 3;
			
			vc.setIdValoracion(idValoracion);
			vc.setIdTrabajoRealizadoValorado(idTrabajoRealizadoValorado);
			vc.setIdProveedorValorado(idProveedorValorado);
			vc.setIdUsuarioValoraTrabajo(idUsuarioValoraTrabajo);
			vc.setIdUsuarioValoraProveedor(idUsuarioValoraProveedor);
			results = valoracionservice.findByCriteria(vc, startIndex, pageSize );
			
			logger.info("Found: "+(results.getTotal()));
			for (ValoracionDTO v: results.getData()) {
				logger.info(v);
			}
			
			logger.trace("End!");
		} catch(DataException de) {
			logger.error(idProveedorValorado, de);
		}catch (ServiceException se) {
			logger.error(idProveedorValorado, se);
		}
	}




	public void testCreate() 
			throws ServiceException, DataException {
		logger.trace("Begin...");

		ValoracionDTO valoracion = null;
		valoracion = new ValoracionDTO();
		///////////////////////////////
		String titulo = "buena valoracion";
		String descripcion = "parece que ha ido todo bien en la valoracion ";
		int notaValoracion = 5;
		Long idUsuarioValora = 1L;
		Long idProveedorValorado = 2L;
		Long idTrabajoRealizadoValorado = null;
		///////////////////////////////		
		valoracion.setTitulo(titulo);
		valoracion.setDescripcion(descripcion);
		valoracion.setNotaValoracion(notaValoracion);
		valoracion.setIdUsuarioValora(idUsuarioValora);
		valoracion.setIdProveedorValorado(idProveedorValorado);
		valoracion.setIdTrabajoRealizadoValorado(idTrabajoRealizadoValorado);

		try {

			valoracionservice.create(valoracion);

			int startIndex = 1;
			int pageSize = 1;

			vc.setIdValoracion(valoracion.getIdValoracion());
			logger.info("Valoracion Creada!: "+valoracionservice.findByCriteria(vc, startIndex, pageSize));

			logger.trace("End!");
		} catch (DataException de) { 
			logger.error(valoracion, de);	
		} catch (ServiceException se) {
			logger.error(valoracion, se);
		}
	}



	public void testupdateStatus() {
		logger.trace("Begin...");

		///////////////////////////////		
		Long idvaloracion = 1L;
		int idestado = 1;
		///////////////////////////////

		try {
			int startIndex = 1;
			int pageSize = 1;

			vc.setIdValoracion(idvaloracion);
			logger.info("Valoracion sin actualizar estado: "+valoracionservice.findByCriteria(vc, startIndex, pageSize));

			valoracionservice.updateStatus(idvaloracion, idestado);

			vc.setIdValoracion(idvaloracion);
			logger.info("Estado actualizado de la Valoracion: "+valoracionservice.findByCriteria(vc, startIndex, pageSize));

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);	
		}  catch(ServiceException se){
			logger.error(se);	
		}
	}



	public static void main (String args[])
			throws ServiceException, DataException {

		ValoracionServiceTest test = new ValoracionServiceTest();

		test.testfindByCriteria();
		//		test.testCreate();
		//		test.testupdateStatus();
	}
}