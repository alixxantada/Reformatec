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
		pc.setIdTipoEstadoPresupuesto(2);


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

		/////////////////////////////////////////////////
		String titulo = " Presupuesto de pruebas";
		String descripcion = " probando a crear un presupuesto desde service";
		Double importeTotal = 1700D;
		int idTipoEstadoPresupuesto = 1;
		Long idProyecto = 1L;
		Long idUsuarioCreadorPresupuesto = 1L;
		////////////////////////////////////////////////
		presupuesto.setTitulo(titulo);
		presupuesto.setDescripcion(descripcion);
		presupuesto.setImporteTotal(importeTotal);
		presupuesto.setIdTipoEstadoPresupuesto(idTipoEstadoPresupuesto);
		presupuesto.setIdProyecto(idProyecto);
		presupuesto.setIdUsuarioCreadorPresupuesto(idUsuarioCreadorPresupuesto);

		List<LineaPresupuestoDTO> lineas = new ArrayList<LineaPresupuestoDTO>();

		LineaPresupuestoDTO linea = new LineaPresupuestoDTO();

		linea.setImporte(150D);
		linea.setDescripcion("probando linea presupuesto descripcion");
		lineas.add(linea);

		linea.setImporte(250D);
		linea.setDescripcion("probando linea presupuesto descripcion 22222");
		lineas.add(linea);


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
		////////////////////////////////////
		Long idPresupuesto = 1L;
		String titulo = "cambiando titulo de presupuesto";
		String descripcion = "probando a cambiar la descripcion del presupuesto";
		Double importeTotal = 200D;
		///////////// faltan las lineas de presupuesto
		///////////////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdPresupuesto(idPresupuesto);
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Presupuesto antes de actualizar!: "+results.getData());

			presupuesto.setTitulo(titulo);
			presupuesto.setDescripcion(descripcion);
			presupuesto.setImporteTotal(importeTotal);
			//presupuesto.setLineas(null); nose como tratar con esto :S

			presupuestoservice.update(presupuesto);

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

		test.testFindByCriteria();
		//test.testCreate();
		//test.testUpdate();  TODO  (funciona, pero no updatea bien las lineas de presupuesto, y hay que actualizar la fecha/hora-- METER CAMPO NUEVO EN BBDD DE FECHA_HORA ACTUALIADA PARA PODER NOTIFICAR SI ALGO CAMBIA?)
		//test.testupdateStatus();
	}
}