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
import com.alejandro.reformatec.dao.PresupuestoDAO;
import com.alejandro.reformatec.dao.ProyectoDAO;
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.impl.PresupuestoDAOImpl;
import com.alejandro.reformatec.dao.impl.ProyectoDAOImpl;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.EstadoPresupuesto;
import com.alejandro.reformatec.model.EstadoProyecto;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
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
	private PresupuestoDAO presupuestoDAO = null;
	private EspecializacionDAO especializacionDAO = null;

	public ProyectoServiceImpl() {
		proyectoDAO = new ProyectoDAOImpl();
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		presupuestoDAO = new PresupuestoDAOImpl();
		especializacionDAO = new EspecializacionDAOImpl();
	}


	@Override
	public Results<ProyectoDTO> findByCriteria(ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException{

		logger.traceEntry();

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
	public long create(ProyectoDTO proyecto, List<Integer> especializaciones) 
			throws ProyectoAlreadyExistsException, MailException, ServiceException, DataException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		Long idProyecto = null;
		Results<UsuarioDTO> usuarioCreador = new Results<UsuarioDTO>();
		UsuarioCriteria uc = new UsuarioCriteria();

		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// Aqui es donde se hace set de la hora que se crea, solo del dia y en formato date
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			proyecto.setFechaCreacion(date);
			if (logger.isTraceEnabled()) {
				logger.trace("AQUII  fecha hora: "+date);
			}
			proyecto.setIdTipoEstadoProyecto(EstadoProyecto.PROYECTO_ACTIVO);

			String titulo = proyecto.getTitulo().toLowerCase();
			proyecto.setTitulo(titulo);

			String descripcion = proyecto.getDescripcion().toLowerCase();
			proyecto.setDescripcion(descripcion);


			idProyecto = proyectoDAO.create(c, proyecto, especializaciones);	


			int startIndex = 1;
			int pageSize = 1;

			uc.setIdUsuario(proyecto.getIdUsuarioCreadorProyecto());

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

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(proyecto,sqle);
			}
			throw new DataException(sqle);

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error(proyecto, ee);
			}
			throw new MailException(ee);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return idProyecto;
	}



	@Override
	public void update(ProyectoDTO proyecto, List<Integer> especializaciones) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);
			
			//TODO Deberia meter otro campo para fecha modificacion en bbdd
			
			//Primero le borro las especializaciones que pueda tener antes del update
			especializacionDAO.deleteEspecializacionProyecto(c, proyecto.getIdProyecto());
			proyectoDAO.update(c, proyecto, especializaciones);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(proyecto,sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void updateStatus(Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			if (idEstadoProyecto==EstadoProyecto.PROYECTO_BORRADO) {
				PresupuestoCriteria prc = new PresupuestoCriteria();
				Results<PresupuestoDTO> presupuestos = new Results<PresupuestoDTO>();
				int startIndex = 1;
				int pageSize = 1;

				//Cambio estado de los presupuestos que tiene el proyecto pendiente de rechazar/aceptar
				prc.setIdProyecto(idProyecto);
				presupuestos = presupuestoDAO.findByCriteria(c, prc, startIndex, pageSize);
				for (PresupuestoDTO p : presupuestos.getData()) {
					if(p.getIdPresupuesto()!=null) {
						if (p.getIdTipoEstadoPresupuesto()==EstadoPresupuesto.PRESUPUESTO_ENVIADO) {
							int idEstadoPresupuesto = EstadoPresupuesto.PRESUPUESTO_RECHAZADO;
							presupuestoDAO.updateStatus(c, p.getIdPresupuesto(), idEstadoPresupuesto);
						}
					}
				}				
			}

			proyectoDAO.updateStatus(c, idProyecto, idEstadoProyecto);

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