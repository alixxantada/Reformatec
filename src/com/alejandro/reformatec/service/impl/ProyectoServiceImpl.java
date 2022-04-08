package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.ProyectoDAO;
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.impl.ProyectoDAOImpl;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;
import com.alejandro.reformatec.service.MailService;
import com.alejandro.reformatec.service.ProyectoService;

public class ProyectoServiceImpl implements ProyectoService {

	private static Logger logger = LogManager.getLogger(ProyectoServiceImpl.class);

	private MailService mailService = null;
	private ProyectoDAO proyectoDAO = null;
	private UsuarioDAO usuarioDAO = null;
	private EspecializacionDAO especializacionDAO = null;

	public ProyectoServiceImpl() {
		proyectoDAO = new ProyectoDAOImpl();
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		especializacionDAO = new EspecializacionDAOImpl();
	}


	@Override
	public Results<ProyectoDTO> findByCriteria(ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		Results<ProyectoDTO> results = new Results<ProyectoDTO>();
		boolean commitOrRollback = false;

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			results = proyectoDAO.findByCriteria(c, pc, startIndex, pageSize);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(results,sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}



	@Override
	public long create(ProyectoDTO proyecto) 
			throws ProyectoAlreadyExistsException, MailException, ServiceException, DataException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;
		Long idProyecto = null;
		Results<UsuarioDTO> usuarioCreador = new Results<UsuarioDTO>();
		UsuarioCriteria uc = new UsuarioCriteria();

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// Aqui es donde se hace set de la hora que se crea
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			proyecto.setFechaCreacion(date);

			proyecto.setIdTipoEstadoProyecto(1);

			String titulo = proyecto.getTitulo().toLowerCase();
			proyecto.setTitulo(titulo);

			String descripcion = proyecto.getDescripcion().toLowerCase();
			proyecto.setDescripcion(descripcion);


			proyectoDAO.create(c, proyecto);	

			if (proyecto.getIdsEspecializaciones()!=null) {

				especializacionDAO.crearEspecializacionProyecto(c, idProyecto, proyecto.getIdsEspecializaciones());
			}

			int startIndex = 1;
			int pageSize = 1;

			uc.setIdUsuario(proyecto.getIdUsuarioCreadorProyecto());
			// despues de crear el proyecto, busco el id del creador del mismo para ponder guardarlo en una variable (usuarioCreador), antes no lo podia hacer porque el proyecto todavia no estaba creado, por lo tanto imposible saber el id del que lo creo porque aun no existe
			usuarioCreador = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);

			for(UsuarioDTO u : usuarioCreador.getData()) {

				String msg = new StringBuilder("Hola ")
						.append(u.getNombrePerfil())
						.append("!!, Acabas de crear el proyecto ")
						.append(proyecto.getTitulo())
						.append(", muchas gracias por confiar en Reformatec.").toString();

				String asunto = new StringBuilder("Tu proyecto ")
						.append(proyecto.getTitulo())
						.append(" Se ha creado con éxito ").toString();

				mailService.sendEmail("no-reply@reformatec.com", 
						asunto,					
						msg,
						// le paso el mail que tengo en el objeto usuario que creo el proyecto
						u.getEmail());

			}


			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(proyecto,sqle);
			throw new DataException(sqle);

		} catch (EmailException ee) {
			logger.error(proyecto, ee);
			throw new MailException(ee);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return proyecto.getIdProyecto();
	}



	@Override
	public void update(ProyectoDTO proyecto) 
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			proyectoDAO.update(c, proyecto);

			// fin de la transacción a continuacio
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(proyecto,sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void updateStatus(Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ServiceException{
		logger.trace("Begin");

		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			proyectoDAO.updateStatus(c, idProyecto, idEstadoProyecto);

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