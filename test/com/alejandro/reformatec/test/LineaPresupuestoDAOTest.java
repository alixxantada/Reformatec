package com.alejandro.reformatec.test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.LineaPresupuestoDAO;
import com.alejandro.reformatec.dao.impl.LineaPresupuestoDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.LineaPresupuestoCriteria;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;


public class LineaPresupuestoDAOTest {

	private static Logger logger = LogManager.getLogger(LineaPresupuestoDAOTest.class);

	private LineaPresupuestoDAO lineaPresupuestoDAO = null;
	LineaPresupuestoDTO lineaPresupuesto = null;
	private LineaPresupuestoCriteria lpc = new LineaPresupuestoCriteria();

	public LineaPresupuestoDAOTest() {

		this.lineaPresupuestoDAO = new LineaPresupuestoDAOImpl();
	}




	public void testFindByCriteria() 
			throws SQLException, DataException {
		logger.trace("Begin...");
		
		Connection c = null;
		c = ConnectionManager.getConnection();
		////////////////////////////////////////////////////////
		lpc.setIdLineaPresupuesto(null);
		lpc.setIdPresupuesto(null);
		lpc.setIdProyecto(null);
		///////////////////////////////////////////////////
		// OrderBy opcion VAL -  ORDER BY AVG(v.NOTA_VALORACION) DESC)
		// OrderBy opcion NV -  ORDER BY u.NUM_VISUALIZACION DESC
		/////////////////////////////////////////////////////////
		try {
			
			List<LineaPresupuestoDTO> results = lineaPresupuestoDAO.findByCriteria(c, lpc);
			logger.info("Found "+results.size());
			for (LineaPresupuestoDTO lp: results) {
				logger.info(lp);
			}
			
			logger.trace("End!");
			
		} catch(DataException de) {
			logger.error(lpc, de);
		}
	}




	public void testCreate()
			throws SQLException, DataException {
		logger.trace("Begin...");	

		lineaPresupuesto = new LineaPresupuestoDTO();

		Connection c = null;
		c = ConnectionManager.getConnection();
		/////////////////////////////////////////////////
		String descripcion = " probando a crear una linea de presupuesto";
		Long idPresupuesto = 5L;
		Double importe = 125.55D;
		////////////////////////////////////////////////

		lineaPresupuesto.setDescripcion(descripcion);
		lineaPresupuesto.setIdPresupuesto(idPresupuesto);
		lineaPresupuesto.setImporte(importe);

		try {

			lineaPresupuestoDAO.create(c, lineaPresupuesto);

			lpc.setIdLineaPresupuesto(lineaPresupuesto.getIdLineaPresupuesto());
			logger.trace("Linea de presupuesto creada: "+lineaPresupuestoDAO.findByCriteria(c, lpc));

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(de);
		}
	}





	public static void main(String args[])
			throws SQLException, DataException {


		LineaPresupuestoDAOTest test = new LineaPresupuestoDAOTest();	

		test.testFindByCriteria();
		//test.testCreate();
		/* testFindBy();*/
	}
}