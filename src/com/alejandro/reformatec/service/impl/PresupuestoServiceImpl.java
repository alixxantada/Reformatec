package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
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
import com.alejandro.reformatec.model.EstadoPresupuesto;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.service.PresupuestoService;

public class PresupuestoServiceImpl implements PresupuestoService {

	private static Logger logger = LogManager.getLogger(PresupuestoServiceImpl.class);

	private PresupuestoDAO presupuestoDAO = null;
	private LineaPresupuestoDAO lineaPresupuestoDAO = null;

	public PresupuestoServiceImpl() {
		presupuestoDAO = new PresupuestoDAOImpl();
		lineaPresupuestoDAO = new LineaPresupuestoDAOImpl();
	}

	@Override
	public Results<PresupuestoDTO> findByCriteria(PresupuestoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException {

		if (logger.isTraceEnabled()) {
			logger.trace("Begin parametros de búsqueda: " + pc);
		}

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

			if (logger.isTraceEnabled()) {
				logger.trace("End lista presupuestos: " + results);
			}

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(results, sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}

	@Override
	public long create(PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			// Aqui es donde se hace set de la hora que se crea
			presupuesto.setFechaHoraCreacion(new Date());
			presupuesto.setFechaHoraModificacion(null);
			if (logger.isTraceEnabled()) {
				logger.trace("Presupuesto de "+presupuesto.getFechaHoraCreacion());
			}

			presupuesto.setIdTipoEstadoPresupuesto(EstadoPresupuesto.PRESUPUESTO_ENVIADO);

			// Calculo el total del presupuesto en base al total de sus lineas.
			Double importeTotal = 0D;
			for (LineaPresupuestoDTO lp : lineas) {
				importeTotal = importeTotal + lp.getImporte();
			}
			presupuesto.setImporteTotal(importeTotal);

			presupuestoDAO.create(c, presupuesto, lineas);

			// fin de la transacción  a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(presupuesto,sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return presupuesto.getIdPresupuesto();
	}

	@Override
	public void update(PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas)
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// Meto fecha de actualizacion
			presupuesto.setFechaHoraModificacion(new Date());

			presupuesto.setIdTipoEstadoPresupuesto(EstadoPresupuesto.PRESUPUESTO_ENVIADO);

			Double importeTotal = 0D;
			for (LineaPresupuestoDTO lp : lineas) {
				importeTotal = importeTotal + lp.getImporte();
			}
			presupuesto.setImporteTotal(importeTotal);


			lineaPresupuestoDAO.deleteByIdPresupuesto(c, presupuesto.getIdPresupuesto());


			// Envio a update los parametros de presupuesto y las lineas de presupuesto nuevas
			presupuestoDAO.update(c, presupuesto, lineas);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(presupuesto, sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	@Override
	public void updateStatus(Long idPresupuesto, Integer idEstadoPresupuesto) throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			if (idEstadoPresupuesto == EstadoPresupuesto.PRESUPUESTO_ELIMINADO) {

				lineaPresupuestoDAO.deleteByIdPresupuesto(c, idPresupuesto);

			}

			presupuestoDAO.updateStatus(c, idPresupuesto, idEstadoPresupuesto);

			// PresupuestoDTO presupuesto = presupuestoDAO.findById(c, idPresupuesto);
			// if (presupuesto.getIdTipoEstadoPresupuesto()==3) {

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}
}