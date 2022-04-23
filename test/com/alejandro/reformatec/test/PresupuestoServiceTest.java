package com.alejandro.reformatec.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.service.PresupuestoService;
import com.alejandro.reformatec.service.impl.PresupuestoServiceImpl;

public class PresupuestoServiceTest {

	private static Logger logger = LogManager.getLogger(PresupuestoServiceTest.class);

	private PresupuestoService presupuestoservice = null;
	private PresupuestoDTO presupuesto = new PresupuestoDTO();
	private PresupuestoCriteria pc = new PresupuestoCriteria();
	private Results<PresupuestoDTO> results = new Results<PresupuestoDTO>();

	private PresupuestoServiceTest() {
		presupuestoservice = new PresupuestoServiceImpl();
	}



	public void testFindByCriteria() {
		logger.trace("Begin...");

		int startIndex = 1;
		int pageSize = 3;
		////////////////////////////////////////////////////////
		pc.setIdPresupuesto(null);
		pc.setIdProveedor(null);
		pc.setIdProyecto(null);
		pc.setIdTipoEstadoPresupuesto(null);


		///////////////////////////////////////////////////////
		try {
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			while(startIndex<results.getTotal()) {
				startIndex = startIndex+results.getData().size();
			}			
			logger.trace("Total resultados: "+results.getTotal());
			logger.trace("Resultados: "+results.getData());
			logger.trace("End!");
		} catch(DataException de) {
			logger.error(pc, de);
		} catch (ServiceException se) {
			logger.error(pc, se);
		}
	}


	public void testCreate() {
		logger.trace("Begin...");	

		PresupuestoDTO presupuesto = null;
		presupuesto = new PresupuestoDTO();
		List<LineaPresupuestoDTO> lineas = new ArrayList<LineaPresupuestoDTO>();
		
		/////////////////////////////////////////////////
		String titulo = " Presupuesto de pruebas88";
		String descripcion = " probando a crear un presupuesto desde service";
		
		//Estos 2 los recibo en el servlet con input hidden en la jsp.
		Long idProyecto = 1L;
		Long idUsuarioCreadorPresupuesto = 1L;
		
		LineaPresupuestoDTO linea = new LineaPresupuestoDTO();

		linea.setImporte(500D);
		linea.setDescripcion("probando linea presupuesto descripcion");
		lineas.add(linea);

		linea.setImporte(500D);
		linea.setDescripcion("probando linea presupuesto descripcion 22222");
		lineas.add(linea);
		////////////////////////////////////////////////
		presupuesto.setTitulo(titulo);
		presupuesto.setDescripcion(descripcion);
		presupuesto.setIdProyecto(idProyecto);
		presupuesto.setIdUsuarioCreadorPresupuesto(idUsuarioCreadorPresupuesto);

		try {

			presupuestoservice.create(presupuesto, lineas);
			// creo que aqui viene la movida toda de la linea presupuesto...

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdPresupuesto(presupuesto.getIdPresupuesto());
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Presupuesto Creado!: "+results.getData());

			logger.trace("End!");
		} catch (ProyectoAlreadyExistsException uae) {		
			logger.error(presupuesto,uae);
		} catch(DataException de) {
			logger.error(presupuesto,de);
		}  catch(ServiceException se){
			logger.error(presupuesto,se);
		}
	}




	public void testUpdate() {
		logger.trace("Begin...");	

		presupuesto = new PresupuestoDTO();
		List<LineaPresupuestoDTO> lineas = new ArrayList<LineaPresupuestoDTO>();
		
		////////////////////////////////////
		Long idPresupuesto = 1L;
		String titulo = "cambiando titulo de presupuesto22";
		String descripcion = "probando a cambiar la descripcion del presupuesto";
		
		LineaPresupuestoDTO linea = new LineaPresupuestoDTO();

		linea.setImporte(600D);
		linea.setDescripcion("probando linea presupuesto descripcion3366");
		lineas.add(linea);

		linea.setImporte(600D);
		linea.setDescripcion("probando linea presupuesto descripcion 22222");
		lineas.add(linea);
		///////////////////////////////////
		presupuesto.setTitulo(titulo);
		presupuesto.setDescripcion(descripcion);
		presupuesto.setIdPresupuesto(idPresupuesto);
		
		try {

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdPresupuesto(idPresupuesto);
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Presupuesto antes de actualizar!: "+results.getData());

			
			//TODO Esto lo recibo desde el servlet con input hidden.(idUsuarioCreadorProyecto/idUsuarioCreadorPresupuesto/idProyecto)
			for(PresupuestoDTO pres : results.getData()) {

				Long idUsuarioCreadorPresupuesto = pres.getIdUsuarioCreadorPresupuesto();
				presupuesto.setIdUsuarioCreadorPresupuesto(idUsuarioCreadorPresupuesto);
				
				Long idProyecto = pres.getIdProyecto();
				presupuesto.setIdProyecto(idProyecto);
			}	

			presupuestoservice.update(presupuesto,lineas);

			pc.setIdPresupuesto(idPresupuesto);
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Presupuesto Actualizado!: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(presupuesto,de);
		}  catch(ServiceException se){
			logger.error(presupuesto,se);
		}
	}



	public void testupdateStatus() {
		logger.trace("Begin...");	

		presupuesto = new PresupuestoDTO();
		/////////////////////////////////////
		Long idpresupuesto = 1L;
		int idestado = 1;
		/////////////////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdPresupuesto(idpresupuesto);
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Presupuesto antes de actualizar estado!: "+results.getData());


			presupuestoservice.updateStatus(idpresupuesto, idestado);


			pc.setIdPresupuesto(idpresupuesto);
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Estado del presupuesto actualizado!: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(presupuesto,de);
		}  catch(ServiceException se){
			logger.error(presupuesto,se);
		}
	}





	public static void main (String args[])
			throws ServiceException, DataException {

		PresupuestoServiceTest test = new PresupuestoServiceTest();

		//test.testFindByCriteria();
		//test.testCreate();
		test.testUpdate();
		//test.testupdateStatus();
	}
}