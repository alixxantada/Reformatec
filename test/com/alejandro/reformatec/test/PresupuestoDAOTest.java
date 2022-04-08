package com.alejandro.reformatec.test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.PresupuestoDAO;
import com.alejandro.reformatec.dao.impl.PresupuestoDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.service.PresupuestoService;
import com.alejandro.reformatec.service.impl.PresupuestoServiceImpl;


public class PresupuestoDAOTest {

	private static Logger logger = LogManager.getLogger(PresupuestoDAOTest.class);	

	private PresupuestoDAO presupuestoDAO = null;
	private PresupuestoService presupuestoservice = null;
	private PresupuestoDTO presupuesto = new PresupuestoDTO();
	private PresupuestoCriteria pc = new PresupuestoCriteria();
	private Results<PresupuestoDTO> results = new Results<PresupuestoDTO>();

	public PresupuestoDAOTest() {

		presupuestoDAO = new PresupuestoDAOImpl();
		presupuestoservice = new PresupuestoServiceImpl();
	}


	public void testCreate()
			throws SQLException, DataException {
		logger.trace("Begin...");

		presupuesto = new PresupuestoDTO();

		Connection c = null;
		c = ConnectionManager.getConnection();

		Calendar cal=Calendar.getInstance();
		Date date=cal.getTime();
		/////////////////////////////////////////////////
		String titulo = " Presupuesto de pruebas";
		String descripcion = " probando a crear un presupuesto";
		Double importeTotal = 150D;
		int idTipoEstadoPresupuesto = 1;
		Long idProyecto = 1L;
		Long idUsuarioCreadorPresupuesto = 2L;
		////////////////////////////////////////////////
		presupuesto.setFechaHoraCreacion(date);
		presupuesto.setTitulo(titulo);
		presupuesto.setDescripcion(descripcion);
		presupuesto.setImporteTotal(importeTotal);
		presupuesto.setIdTipoEstadoPresupuesto(idTipoEstadoPresupuesto);
		presupuesto.setIdProyecto(idProyecto);
		presupuesto.setIdUsuarioCreadorPresupuesto(idUsuarioCreadorPresupuesto);


		try {

			presupuestoDAO.create(c, presupuesto);

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdPresupuesto(presupuesto.getIdPresupuesto());
			results = presupuestoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Usuario Creado!: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		} catch(ServiceException se) {
			logger.error(se);
		}
	}





	public static void main(String args[])
			throws SQLException, DataException {

		PresupuestoDAOTest test = new PresupuestoDAOTest();	 

		test.testCreate();
	}
}