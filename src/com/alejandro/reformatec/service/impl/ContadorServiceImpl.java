package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.ContadorDAO;
import com.alejandro.reformatec.dao.impl.ContadorDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.service.ContadorService;

public class ContadorServiceImpl implements ContadorService{
	private static Logger logger = LogManager.getLogger(ContadorServiceImpl.class);

	private ContadorDAO contadorDAO = null;

	public ContadorServiceImpl() {
		contadorDAO = new ContadorDAOImpl();
	}

	
	
	public Integer cliente()
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Integer clientes = null;		
		boolean commitOrRollback = false;
		
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			clientes = contadorDAO.cliente(c);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");
			
		} catch (SQLException sqle) {
			logger.error(clientes,sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return clientes;
	}

	
	
	public Integer proveedor()
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Integer proveedores = null;		
		boolean commitOrRollback = false;
		
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			proveedores = contadorDAO.proveedor(c);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");
			
		} catch (SQLException sqle) {
			logger.error(proveedores,sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return proveedores;
	}

	
	
	public Integer proyectoActivo()
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Integer proyectos = null;		
		boolean commitOrRollback = false;
		
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			proyectos = contadorDAO.proyectoActivo(c);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");
			
		} catch (SQLException sqle) {
			logger.error(proyectos,sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return proyectos;
	}

	
	
	public Integer proyectoFinalizado()
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Integer proyectos = null;		
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			proyectos = contadorDAO.proyectoFinalizado(c);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");
			
		} catch (SQLException sqle) {
			logger.error(proyectos,sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return proyectos;
	}

	
	
	public Integer trabajoRealizado()
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Integer trabajosRealizados = null;		
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			trabajosRealizados = contadorDAO.trabajoRealizado(c);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");
			
		} catch (SQLException sqle) {
			logger.error(trabajosRealizados,sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return trabajosRealizados;
	}
}