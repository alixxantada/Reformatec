package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.ValoracionDAO;
import com.alejandro.reformatec.dao.impl.ValoracionDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.EstadoValoracion;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;
import com.alejandro.reformatec.service.ValoracionService;

public class ValoracionServiceImpl implements ValoracionService{

	private static Logger logger = LogManager.getLogger(ValoracionServiceImpl.class);

	private ValoracionDAO valoracionDAO = null;

	public ValoracionServiceImpl() {
		valoracionDAO = new ValoracionDAOImpl();
	}

	
	
	@Override
	public Results<ValoracionDTO> findByCriteria(ValoracionCriteria vc, int startIndex, int pageSize)
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		Results<ValoracionDTO> results = new Results<ValoracionDTO>();
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = valoracionDAO.findByCriteria(c, vc, startIndex, pageSize);

			// fin de la transacción
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(results,sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}


	
	@Override
	public Results<ValoracionDTO> findByBuenaValoracionRamdom(int startIndex, int pageSize)
			throws DataException, ServiceException {
		
		logger.traceEntry();

		Connection c = null;
		Results<ValoracionDTO> results = new Results<ValoracionDTO>();
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = valoracionDAO.findByBuenaValoracionRamdom(c, startIndex, pageSize);

			// fin de la transacción
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(results,sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}
	
	
	
	@Override
	public long create(ValoracionDTO valoracion) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);
			Date fechaHora = new Date();
			valoracion.setFechaHoraCreacion(fechaHora);
			
			valoracion.setIdTipoEstadoValoracion(EstadoValoracion.VALORACION_ACTIVA);

			valoracionDAO.create(c, valoracion);

			// fin de la transacción
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(valoracion,sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return valoracion.getIdValoracion();
	}




	@Override
	public void updateStatus(Long idValoracion, Integer idEstadoValoracion) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			valoracionDAO.updateStatus(c, idValoracion, idEstadoValoracion);

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