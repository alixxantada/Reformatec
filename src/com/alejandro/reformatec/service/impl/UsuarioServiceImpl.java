package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.PresupuestoDAO;
import com.alejandro.reformatec.dao.ProyectoDAO;
import com.alejandro.reformatec.dao.TrabajoRealizadoDAO;
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.ValoracionDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.impl.PresupuestoDAOImpl;
import com.alejandro.reformatec.dao.impl.ProyectoDAOImpl;
import com.alejandro.reformatec.dao.impl.TrabajoRealizadoDAOImpl;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.impl.ValoracionDAOImpl;
import com.alejandro.reformatec.dao.util.ConfigurationManager;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.ConstantConfigUtil;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.dao.util.PasswordEncryptionUtil;
import com.alejandro.reformatec.exception.CodeInvalidException;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.EmailPendienteValidacionException;
import com.alejandro.reformatec.exception.FavoritoAlreadyExistsException;
import com.alejandro.reformatec.exception.InvalidUserOrPasswordException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.exception.UserAlreadyExistsException;
import com.alejandro.reformatec.exception.UserLowInTheSystemException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.EstadoCuenta;
import com.alejandro.reformatec.model.EstadoPresupuesto;
import com.alejandro.reformatec.model.EstadoProyecto;
import com.alejandro.reformatec.model.EstadoTrabajoRealizado;
import com.alejandro.reformatec.model.EstadoValoracion;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TipoUsuario;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;
import com.alejandro.reformatec.service.MailService;
import com.alejandro.reformatec.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	private MailService mailService = null;
	private UsuarioDAO usuarioDAO = null;
	private ValoracionDAO valoracionDAO = null;
	private TrabajoRealizadoDAO trabajoRealizadoDAO = null;
	private ProyectoDAO proyectoDAO = null;
	private PresupuestoDAO presupuestoDAO = null;
	private EspecializacionDAO especializacionDAO = null;

	public UsuarioServiceImpl() {
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		valoracionDAO = new ValoracionDAOImpl();
		trabajoRealizadoDAO = new TrabajoRealizadoDAOImpl();
		proyectoDAO = new ProyectoDAOImpl();
		presupuestoDAO = new PresupuestoDAOImpl();
		especializacionDAO = new EspecializacionDAOImpl();
	}


	private static final String CFGM_PFX = "service.usuario.";
	private static final String START_INDEX = CFGM_PFX + "startIndex";
	private static final String PAGE_SIZE = CFGM_PFX + "pageSize";
	private static final String MAIL = "service.mail.account";
	
	private ConfigurationManager cfgM = ConfigurationManager.getInstance();
	
	@Override
	public UsuarioDTO findByEmail(UsuarioCriteria uc)
			throws DataException, ServiceException{

		logger.traceEntry();

		Connection c = null;
		UsuarioDTO usuario = new UsuarioDTO();
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, uc);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(usuario,sqle);
			}
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}



	@Override
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria uc, int startIndex, int pageSize) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		Results<UsuarioDTO> results = new Results<UsuarioDTO>();
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);

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
	public Long signUp(UsuarioDTO u, List<Integer> especializaciones, String url)
			throws UserAlreadyExistsException, UserLowInTheSystemException, EmailPendienteValidacionException, MailException, ServiceException, DataException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		Long userId = null;
		UsuarioDTO usuario = new UsuarioDTO();
		UsuarioCriteria uc = new UsuarioCriteria();

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			//Comprobamos si el usuario existe en bbdd, si existe comprobamos si tiene la cuenta activa, si esta pendiente de validar el mail o se dio de baja.
			uc.setEmailExistente(u.getEmail());
			usuario = usuarioDAO.findByEmail(c, uc);
			if (usuario!=null) {

				if(usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_ACTIVA) {
					throw new EmailPendienteValidacionException(usuario.getEmail());

				} else if (usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_VALIDADA) {					
					throw new UserAlreadyExistsException(usuario.getEmail());

				} else if (usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_CANCELADA){
					throw new UserLowInTheSystemException(usuario.getEmail());

				}
			}


			// encriptamos la password y la seteamos ya encriptada
			u.setEncryptedPassword(PasswordEncryptionUtil.encryptPassword(u.getPassword()));
			
			u.setIdTipoEstadoCuenta(EstadoCuenta.CUENTA_ACTIVA);

			// Pongo las visualizaciones a 0 a los proveedores y a null a los clientes
			if (u.getIdTipoUsuario()==1) {
				u.setNumeroVisualizaciones(null);
				u.setProveedorVerificado(null);
			} else if (u.getIdTipoUsuario()==2) {
				u.setNumeroVisualizaciones(0L);
				//TODO Faltaria implementar la verificacion de documentacion de los proveedores, mientras lo pongo a true.
				u.setProveedorVerificado(true);
			}


			String nombrePerfil=u.getNombrePerfil().toLowerCase();
			u.setNombrePerfil(nombrePerfil);

			String email = u.getEmail().toLowerCase();
			u.setEmail(email);

			String dni = u.getNif().toLowerCase();
			u.setNif(dni);

			String primerApellido = u.getApellido1().toLowerCase();
			u.setApellido1(primerApellido);

			String segundoApeellido = u.getApellido2().toLowerCase();
			u.setApellido2(segundoApeellido);

			String direccion = u.getNombreCalle().toLowerCase();			
			u.setNombreCalle(direccion);


			if(!StringUtils.isBlank(u.getCif())) {
				String cif = u.getCif().toLowerCase();
				u.setCif(cif);
			}

			if(!StringUtils.isBlank(u.getDireccionWeb())) {
				String direccionWeb = u.getDireccionWeb().toLowerCase();
				u.setDireccionWeb(direccionWeb);
			}


			if (logger.isTraceEnabled()) {
				logger.trace("Service envia usuario a dao: "+u+", idsEspecializaciones; "+especializaciones);
			} 


			userId = usuarioDAO.create(c, u, especializaciones);


			StringBuilder msgSb = new StringBuilder("<html><body><h1> Hola ").append(u.getNombre())
					.append(",</h1><h2> Bienvenid@ a Reformatec!</h2>")
					.append("<p>Por seguridad, para poder usar tu cuenta necesitamos que valides tu email en el siguiente enlace: <a href='"+url+"'>Valida tu Cuenta!</a></p></body></html>");

			String msg = msgSb.toString();

			mailService.sendHTML(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, MAIL), "bienvenido a Reformatec", msg, u.getEmail());

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(usuario, sqle);
			}
			throw new DataException(sqle);

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error(usuario, ee);
			}
			throw new MailException(u.getEmail(), ee);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return userId;
	}


	@Override
	public UsuarioDTO login(String email, String password)
			throws InvalidUserOrPasswordException, UserLowInTheSystemException, EmailPendienteValidacionException, DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioCriteria uc = new UsuarioCriteria();
		UsuarioDTO usuario = new UsuarioDTO();

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			//Primero intento hacer login contando que todo ok, sino miro haver que pasa y saco excepciones segun.
			uc.setEmailActivo(email);
			usuario = usuarioDAO.findByEmail(c, uc);			
			if(usuario.getEmail()!=null) {

				// en el metodo de checkpassword le pasamos la variable "password" como la contraseña plana (la que escribe el usuario, sin encriptar) y la comparamos con la contraseña encriptada de la base de datos
				boolean passwordOK = PasswordEncryptionUtil.checkPassword(password, usuario.getEncryptedPassword());

				if(passwordOK!=true) {
					throw new InvalidUserOrPasswordException(email);
				}

			} else {

				uc.setEmailActivo(null);
				uc.setEmailExistente(email);
				usuario = usuarioDAO.findByEmail(c, uc);				
				if (usuario.getEmail()!=null) {					

					if (usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_ACTIVA) {
						throw new EmailPendienteValidacionException(email);
					} else if (usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_CANCELADA) {
						throw new UserLowInTheSystemException(email);
					}

				}
				throw new InvalidUserOrPasswordException(email);
			}

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(usuario, sqle);
			}
			throw new DataException(email, sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}


	public void anhadirFavorito(Long idCliente, Long idProveedor) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		Boolean favorito = null;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			favorito = usuarioDAO.compruebaFavorito(c, idCliente, idProveedor);
			if (favorito != null) {
				throw new FavoritoAlreadyExistsException("El usuario con id: " + idCliente
						+ " ya tiene de favorito al proveedor con id: " + idProveedor);
			}

			usuarioDAO.anhadirFavorito(c, idCliente, idProveedor);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error("Id Cliente: " + idCliente, "Id Proveedor" + idProveedor, sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	
	
	
	public Set<Long> findFavorito(Long idUsuario) 
			throws DataException, ServiceException{
		logger.traceEntry();
	
		Connection c = null;
		Set<Long> usuariosIds = new HashSet<Long>();
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuariosIds = usuarioDAO.findFavorito(c, idUsuario);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(usuariosIds,sqle);
			}
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuariosIds;
	}
	
	
	
	
	public void deleteFavorito(Long idCliente, Long idProveedor) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		Boolean favorito = null;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			favorito = usuarioDAO.compruebaFavorito(c, idCliente, idProveedor);
			if (favorito == null) {
				throw new FavoritoAlreadyExistsException("El usuario con id: " + idCliente
						+ " NO tiene de favorito al proveedor con id: " + idProveedor);
			}

			usuarioDAO.deleteFavorito(c, idCliente, idProveedor);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error("Id Cliente: " + idCliente, "Id Proveedor" + idProveedor, sqle);
			}
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}





	@Override
	public void visualizaUsuario(Long id) 
			throws DataException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuarioDAO.visualizaUsuario(c, id);

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
	public void updateCode(Long idUsuario, String code)
			throws DataException, UserNotFoundException, ServiceException {
		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);


			usuarioDAO.updateCode(c, idUsuario, code);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (UserNotFoundException unfe) {
			if (logger.isErrorEnabled()) {
				logger.error(idUsuario, unfe);
			}
			throw unfe;

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}
	
	
	
	
	
	@Override
	public void update(UsuarioDTO u, List<Integer> especializaciones) 
			throws DataException, UserNotFoundException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// recibe la contraseña plana de arriba, y si es distinto de null la setea en la actual contraseña encryptada(y encriptandola)
			if (u.getPassword() != null) {
				u.setEncryptedPassword(PasswordEncryptionUtil.encryptPassword(u.getPassword()));
			}


			if (u.getIdTipoUsuario()==TipoUsuario.USUARIO_PROVEEDOR) {
				//Primero le borro las especializaciones que pueda tener antes del update
				especializacionDAO.deleteEspecializacionUsuario(c, u.getIdUsuario());
			}

			usuarioDAO.update(c, u, especializaciones);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (UserNotFoundException unfe) {
			if (logger.isErrorEnabled()) {
				logger.error(u, unfe);
			}
			throw unfe;

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(u, sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public UsuarioDTO validaEmail(String email, String codRegistro) 
			throws DataException, UserLowInTheSystemException, CodeInvalidException, UserNotFoundException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioCriteria uc = new UsuarioCriteria();
		UsuarioDTO usuario = new UsuarioDTO();

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			uc.setEmailExistente(email);
			usuario = usuarioDAO.findByEmail(c, uc);
			if(usuario.getIdUsuario()!=null) {
				if (usuario.getIdTipoEstadoCuenta()==EstadoCuenta.CUENTA_CANCELADA) {
					throw new UserLowInTheSystemException();

				} else {
					if (usuario.getCodigoRegistro().equals(codRegistro)) {
						Integer idEstadoCuenta = EstadoCuenta.CUENTA_VALIDADA;
						Long idUsuario = usuario.getIdUsuario();
						usuarioDAO.updateStatus(c, idUsuario, idEstadoCuenta);

					} else {
						throw new CodeInvalidException();
					}
				}

			} else {
				throw new UserNotFoundException();
			}

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (UserNotFoundException unfe) {
			if (logger.isErrorEnabled()) {
				logger.error(unfe);
			}
			throw unfe;

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(email,sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}



	@Override
	public void updateStatus(Long idUsuario, Integer idEstadoCuenta, String url)
			throws DataException, UserNotFoundException, MailException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// Borrado Lógico
			if (idEstadoCuenta==EstadoCuenta.CUENTA_CANCELADA) {

				ValoracionCriteria vc = new ValoracionCriteria();
				Results<ValoracionDTO> valoraciones = new Results<ValoracionDTO>();
				int startIndex = 1;
				int pageSize = 1;

				//Cambio estado de las valoraciones que hizo este usuario a proveedores.
				vc.setIdUsuarioValoraProveedor(idUsuario);
				valoraciones = valoracionDAO.findByCriteria(c, vc, startIndex, pageSize);
				for (ValoracionDTO v : valoraciones.getData()) {
					if(v.getIdValoracion()!=null) {
						int idEstadoValoracion = EstadoValoracion.VALORACION_ELIMINADA;
						valoracionDAO.updateStatus(c, v.getIdValoracion(), idEstadoValoracion);
					}					
				}

				//Cambio estado de las valoraciones que hizo este usuario a Trabajos realizados.
				vc.setIdUsuarioValoraProveedor(null);
				vc.setIdUsuarioValoraTrabajo(idUsuario);
				valoraciones = valoracionDAO.findByCriteria(c, vc, startIndex, pageSize);
				for (ValoracionDTO v : valoraciones.getData()) {
					if(v.getIdValoracion()!=null) {
						int idEstadoValoracion = EstadoValoracion.VALORACION_ELIMINADA;
						valoracionDAO.updateStatus(c, v.getIdValoracion(), idEstadoValoracion);
					}
				}


				TrabajoRealizadoCriteria tc = new TrabajoRealizadoCriteria();
				Results<TrabajoRealizadoDTO> trabajos = new Results<TrabajoRealizadoDTO>();

				//Cambio estado de los trabajos que hizo este usuario.
				tc.setIdProveedor(idUsuario);
				trabajos = trabajoRealizadoDAO.findByCriteria(c, tc, startIndex, pageSize);
				for (TrabajoRealizadoDTO t : trabajos.getData()) {
					if(t.getIdTrabajoRealizado()!=null) {
						int idEstadoTrabajo = EstadoTrabajoRealizado.TRABAJO_REALIZADO_ELIMINADO;
						trabajoRealizadoDAO.updateStatus(c, t.getIdTrabajoRealizado(), idEstadoTrabajo);
					}
				}


				ProyectoCriteria pc = new ProyectoCriteria();
				Results<ProyectoDTO> proyectos = new Results<ProyectoDTO>();

				//Cambio estado de los proyectos que hizo este usuario
				pc.setIdUsuarioCreador(idUsuario);
				proyectos = proyectoDAO.findByCriteria(c, pc, startIndex, pageSize);
				for (ProyectoDTO p : proyectos.getData()) {
					if (p.getIdProyecto()!=null) {
						int idEstadoProyecto = EstadoProyecto.PROYECTO_BORRADO;
						proyectoDAO.updateStatus(c, p.getIdProyecto(), idEstadoProyecto);
					}
				}


				PresupuestoCriteria prc = new PresupuestoCriteria();
				Results<PresupuestoDTO> presupuestos = new Results<PresupuestoDTO>();

				// Cambio estado de los presupuestos del usuario en estado "enviado" a estado "eliminado".
				prc.setIdProveedor(idUsuario);
				presupuestos = presupuestoDAO.findByCriteria(c, prc, startIndex, pageSize);
				for (PresupuestoDTO p : presupuestos.getData()) {
					if(p.getIdPresupuesto()!=null) {
						if (p.getIdTipoEstadoPresupuesto()==EstadoPresupuesto.PRESUPUESTO_ENVIADO) {
							int idEstadoPresupuesto = EstadoPresupuesto.PRESUPUESTO_ELIMINADO;
							presupuestoDAO.updateStatus(c, p.getIdPresupuesto(), idEstadoPresupuesto);
						}
					}
				}
			}

			usuarioDAO.updateStatus(c, idUsuario, idEstadoCuenta);


			if (idEstadoCuenta==EstadoCuenta.CUENTA_CANCELADA) {
				
		
				Results<UsuarioDTO> results = new Results<UsuarioDTO>();
				UsuarioCriteria uc = new UsuarioCriteria();
				uc.setIdUsuario(idUsuario);
				results = usuarioDAO.findByCriteria(c, uc, Integer.valueOf(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, START_INDEX)), Integer.valueOf(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PAGE_SIZE)));
				if(results.getData()!=null) {

					for (UsuarioDTO u : results.getData()) {
						StringBuilder msgSb = new StringBuilder("<html><body><h1> Hola ").append(u.getNombre())
								.append(",</h1><h2> Sentimos que te vayas!!</h2>")
								.append("<p>Siempre puedes reactivar tu cuenta en el siguiente enlace: <a href='"+url+"'>Reactivar Cuenta</a></p></body></html>");

						String msg = msgSb.toString();

						mailService.sendHTML(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, MAIL), "Vuelve con nosotros cuando quieras", msg, u.getEmail());
					}


				}
			}
			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (UserNotFoundException unfe) {
			if (logger.isErrorEnabled()) {
				logger.error(unfe);
			}
			throw unfe;

		} catch (EmailException ee) {
			if (logger.isErrorEnabled()) {
				logger.error(idUsuario, ee);
			}
			throw new MailException(ee);

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(idUsuario,sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	
	

	public void updatePassword(Long idUsuario, String password)
			throws DataException, UserNotFoundException, ServiceException{

			logger.traceEntry();

			Connection c = null;
			boolean commitOrRollback = false;
			try {
				c = ConnectionManager.getConnection();

				// inicio de la transaccion, es como un beggin
				c.setAutoCommit(false);

				
				//Encripto la pass
				password = PasswordEncryptionUtil.encryptPassword(password);

				usuarioDAO.updatePassword(c, idUsuario, password);

				// fin de la transacción a continuacion
				commitOrRollback = true;

				logger.traceExit();

			} catch (UserNotFoundException unfe) {
				if (logger.isErrorEnabled()) {
					logger.error(idUsuario, unfe);
				}
				throw unfe;

			} catch (SQLException sqle) {
				if (logger.isErrorEnabled()) {
					logger.error(idUsuario, sqle);
				}
				throw new DataException(sqle);		

			} finally {
				JDBCUtils.closeConnection(c, commitOrRollback);
			}
		}
	
	
	
	
	@Override
	public long deleteById(Long idUsuario)
			throws DataException, UserNotFoundException, ServiceException {

		logger.traceEntry();

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuarioDAO.deleteById(c, idUsuario);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.traceExit();

		} catch (UserNotFoundException unfe) {
			if (logger.isErrorEnabled()) {
				logger.error(unfe);
			}
			throw unfe;

		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(sqle);
			}
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return idUsuario;
	}
}