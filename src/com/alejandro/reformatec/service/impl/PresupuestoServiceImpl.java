package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.LineaPresupuestoDAO;
import com.alejandro.reformatec.dao.PresupuestoDAO;
import com.alejandro.reformatec.dao.impl.LineaPresupuestoDAOImpl;
import com.alejandro.reformatec.dao.impl.PresupuestoDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.service.PresupuestoService;

public class PresupuestoServiceImpl implements PresupuestoService{

	private static Logger logger = LogManager.getLogger(PresupuestoServiceImpl.class);

	private PresupuestoDAO presupuestoDAO = null;
	private LineaPresupuestoDAO lineaPresupuestoDAO = null;

	public PresupuestoServiceImpl() {
		presupuestoDAO = new PresupuestoDAOImpl();
		lineaPresupuestoDAO = new LineaPresupuestoDAOImpl();
	}




	@Override
	public Results<PresupuestoDTO> findByCriteria(PresupuestoCriteria pc, int startIndex, int pageSize) throws DataException, ServiceException{
		logger.trace("Begin parametros de búsqueda: " + pc);

		Connection c = null;
		Results<PresupuestoDTO> results = new Results<PresupuestoDTO>();
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = presupuestoDAO.findByCriteria(c, pc, startIndex, pageSize);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End lista proveedores: " + results);

		} catch (SQLException sqle) {
			logger.error(results,sqle);
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}



	@Override
	public long create(PresupuestoDTO presupuesto,List<LineaPresupuestoDTO> lineas) 
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			// Aqui es donde se hace set de la hora que se crea
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			presupuesto.setFechaHoraCreacion(date);
			presupuestoDAO.create(c, presupuesto);

			/////////////// meterlo a presupuestoDAOImpl... en el create
			for (LineaPresupuestoDTO lineaPresupuestoDTO : lineas) {
				lineaPresupuestoDTO.setIdPresupuesto(presupuesto.getIdPresupuesto());
				lineaPresupuestoDAO.create(c,lineaPresupuestoDTO);
			}

			// fin de la transacción  a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(presupuesto,sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return presupuesto.getIdPresupuesto();
	}



	@Override
	public void update(PresupuestoDTO presupuesto) 
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			presupuestoDAO.update(c, presupuesto);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(presupuesto,sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void updateStatus(Long idPresupuesto, Integer idEstadoPresupuesto) 
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			presupuestoDAO.updateStatus(c, idPresupuesto, idEstadoPresupuesto);

			//PresupuestoDTO presupuesto = presupuestoDAO.findById(c, idPresupuesto);
			//if (presupuesto.getIdTipoEstadoPresupuesto()==3) {

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}
}