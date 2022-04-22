package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;
import com.alejandro.reformatec.service.EspecializacionService;

public class EspecializacionServiceImpl implements EspecializacionService{

	private static Logger logger = LogManager.getLogger(EspecializacionServiceImpl.class);

	private EspecializacionDAO especializacionDAO = null;

	public EspecializacionServiceImpl() {
		especializacionDAO = new EspecializacionDAOImpl();
	}

	
	@Override
	public List<Especializacion> findByCriteria(EspecializacionCriteria ec)
			throws DataException, ServiceException{
		
		logger.traceEntry();

		Connection c = null;
		List<Especializacion> lista = new ArrayList<Especializacion>();
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			lista = especializacionDAO.findByCriteria(c, ec);

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