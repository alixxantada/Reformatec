package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.PoblacionDAO;
import com.alejandro.reformatec.dao.impl.PoblacionDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;
import com.alejandro.reformatec.service.PoblacionService;


public class PoblacionServiceImpl implements PoblacionService {
	
	private static Logger logger = LogManager.getLogger(PoblacionServiceImpl.class);

	private PoblacionDAO poblacionDAO = null;

	public PoblacionServiceImpl() {
		poblacionDAO = new PoblacionDAOImpl();
	}

	
	
	

	@Override
	public List<PoblacionDTO> findByCriteria(PoblacionCriteria pc)
			throws DataException, ServiceException{
		
		logger.traceEntry();
		
		Connection c = null;
		List<PoblacionDTO> lista = new ArrayList<PoblacionDTO>();
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			lista = poblacionDAO.findByCriteria(c, pc);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(lista,sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return lista;
	}
}