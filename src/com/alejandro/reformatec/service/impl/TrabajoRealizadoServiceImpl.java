package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.TrabajoRealizadoDAO;
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.impl.TrabajoRealizadoDAOImpl;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.EstadoTrabajoRealizado;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;
import com.alejandro.reformatec.service.MailService;
import com.alejandro.reformatec.service.TrabajoRealizadoService;

public class TrabajoRealizadoServiceImpl implements TrabajoRealizadoService {

	private static Logger logger = LogManager.getLogger(TrabajoRealizadoServiceImpl.class);

	private MailService mailService = null;
	private TrabajoRealizadoDAO trabajoRealizadoDAO = null;
	private UsuarioDAO usuarioDAO = null;
	private EspecializacionDAO especializacionDAO = null;

	public TrabajoRealizadoServiceImpl() {
		trabajoRealizadoDAO = new TrabajoRealizadoDAOImpl();
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		especializacionDAO = new EspecializacionDAOImpl();
	}



	@Override
	public Results<TrabajoRealizadoDTO> findByCriteria(TrabajoRealizadoCriteria trc, int startIndex, int pageSize)
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		Results<TrabajoRealizadoDTO> results = new Results<TrabajoRealizadoDTO>();
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = trabajoRealizadoDAO.findByCriteria(c, trc, startIndex, pageSize);

			// fin de la transacción
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
		return results;
	}




	@Override
	public void visualizaTrabajo(Long id) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			trabajoRealizadoDAO.visualizaTrabajo(c, id);

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(usuario, sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public long create(TrabajoRealizadoDTO tr, List<Integer> especializaciones) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		// necesario para poder quitar el email de algun lado y poder enviar un mail
		Results<UsuarioDTO> usuarioCreador = new Results<UsuarioDTO>();;
		UsuarioCriteria uc = new UsuarioCriteria();
		//necesario para poder tener el id del trabajo que creamos y ponerle las especializaciones
		Long trabajoId=null;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// Aqui es donde se hace set de la hora que se crea (solo pone el dia en formato Date)
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			tr.setFechaCreacion(date);

			String titulo = tr.getTitulo().toLowerCase();
			tr.setTitulo(titulo);

			String descripcion = tr.getDescripcion().toLowerCase();
			tr.setDescripcion(descripcion);

			tr.setIdTipoEstadoTrabajoRealizado(EstadoTrabajoRealizado.TRABAJO_REALIZADO_ACTIVO);
			tr.setNumVisualizaciones(0L);

			if (logger.isTraceEnabled()) {
				logger.trace("Service envia trabajo a dao: "+tr+", idsEspecializaciones; "+especializaciones);
			}

			trabajoId=trabajoRealizadoDAO.create(c, tr, especializaciones);


			int startIndex = 1;
			int pageSize = 1;

			uc.setIdUsuario(tr.getIdUsuarioCreadorTrabajo());
			usuarioCreador = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize );

			for(UsuarioDTO u : usuarioCreador.getData()) {

				String msg = new StringBuilder("Hola ")
						.append(u.getNombrePerfil())
						.append("!!, Acabas de crear el trabajo ")
						.append(tr.getTitulo())
						.append(", muchas gracias por confiar en Reformatec.").toString();

				String asunto = new StringBuilder("Tu trabajo ")
						.append(tr.getTitulo())
						.append(" Se ha creado con éxito ").toString();

				mailService.sendEmail("no-reply@reformatec.com", 
						asunto,					
						msg,
						// le paso el mail que tengo en el objeto usuario que creo el proyecto
						u.getEmail());
			}


			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(tr,sqle);
			}
			throw new DataException(sqle);

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error(tr, ee);
			}
			throw new MailException(ee);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return trabajoId;
	}



	@Override
	public void update(TrabajoRealizadoDTO tr, List<Integer> especializaciones) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			//TODO Deberia meter nuevo dato en bbdd fecha modificacion.
			
			//Primero le borro las especializaciones que pueda tener antes del update
			especializacionDAO.deleteEspecializacionTrabajo(c, tr.getIdTrabajoRealizado());
			trabajoRealizadoDAO.update(c, tr, especializaciones);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(tr,sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void updateStatus(Long idTrabajoRealizado, Integer idEstadoTrabajoRealizado) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			trabajoRealizadoDAO.updateStatus(c, idTrabajoRealizado, idEstadoTrabajoRealizado);

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