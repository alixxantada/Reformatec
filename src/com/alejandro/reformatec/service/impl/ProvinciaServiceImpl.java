package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.ProvinciaDAO;
import com.alejandro.reformatec.dao.impl.ProvinciaDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Provincia;
import com.alejandro.reformatec.service.ProvinciaService;

public class ProvinciaServiceImpl implements ProvinciaService {

	private static Logger logger = LogManager.getLogger(ProvinciaServiceImpl.class);

	private ProvinciaDAO provinciaDAO = null;

	public ProvinciaServiceImpl() {
		provinciaDAO = new ProvinciaDAOImpl();
	}



	@Override
	public List<Provincia> findByCriteria(Integer idProvincia)
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		List<Provincia> lista = new ArrayList<Provincia>();
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			lista = provinciaDAO.findByCriteria(c, idProvincia);

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