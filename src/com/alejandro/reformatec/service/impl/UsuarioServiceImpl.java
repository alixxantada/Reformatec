package com.alejandro.reformatec.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.impl.EspecializacionDAOImpl;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.dao.util.PasswordEncryptionUtil;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.FavoritoAlreadyExistsException;
import com.alejandro.reformatec.exception.InvalidUserOrPasswordException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.exception.UserAlreadyExistsException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.EstadoCuenta;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;
import com.alejandro.reformatec.service.MailService;
import com.alejandro.reformatec.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	private MailService mailService = null;
	private UsuarioDAO usuarioDAO = null;
	private EspecializacionDAO especializacionDAO = null;

	public UsuarioServiceImpl() {
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		especializacionDAO = new EspecializacionDAOImpl();
	}

	
	
	@Override
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria pc, int startIndex, int pageSize) throws DataException, ServiceException {
		logger.trace("Begin parametros de búsqueda: " + pc);

		Connection c = null;
		Results<UsuarioDTO> results = new Results<UsuarioDTO>();
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			results = usuarioDAO.findByCriteria(c, pc, startIndex, pageSize);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End lista proveedores: " + results);

		} catch (SQLException sqle) {
			logger.error(results,sqle);
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}
	
	
	@Override
	// estos es el service del create
	public Long signUp(UsuarioDTO u)
			throws UserAlreadyExistsException, MailException, ServiceException, DataException {
		logger.trace("Begin usuario: " + u);

		Connection c = null;
		boolean commitOrRollback = false;
		Long userId = null;
		Results<UsuarioDTO> usuario = new Results<UsuarioDTO>();
		UsuarioCriteria uc = new UsuarioCriteria();
		
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			int startIndex = 1;
			int pageSize = 1;
			
			uc.setEmail(u.getEmail());
			usuario = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);
			
			if (usuario.getData() != null) {
				throw new UserAlreadyExistsException(u.getEmail());
			}

			// encriptamos la password y la seteamos ya encriptada
			u.setEncryptedPassword(PasswordEncryptionUtil.encryptPassword(u.getPassword()));
			// le ponemos ya el estado en el que se creará la cuenta
			u.setIdTipoEstadoCuenta(EstadoCuenta.CUENTA_VALIDADA);


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


			logger.trace("Service envia usuario a dao: "+u);
			userId = usuarioDAO.create(c, u);

			if(u.getIdsEspecializaciones()!=null) {

				especializacionDAO.crearEspecializacionUsuario(c,userId,u.getIdsEspecializaciones());
			}


			StringBuilder msgSb = new StringBuilder("Hola ").append(u.getNombre())
					.append(", Bienvenid@ a Reformatec...");

			String msg = msgSb.toString();

			mailService.sendEmail("no-reply@reformatec.com", "bienvenido a Reformatec", msg, u.getEmail());

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.trace("End id del usuario: " + userId);

		} catch (SQLException sqle) {
			logger.error(usuario, sqle);
			throw new DataException(sqle);

		} catch (EmailException ee) {
			logger.error(usuario, ee);
			throw new MailException(u.getEmail(), ee);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return userId;
	}


	@Override
	public Results<UsuarioDTO> login(String email, String password)
			throws InvalidUserOrPasswordException, DataException, ServiceException {
		logger.trace("Begin email: " + email);

		Connection c = null;
		boolean commitOrRollback = false;
		Results<UsuarioDTO> usuario = new Results<UsuarioDTO>();
		UsuarioCriteria uc = new UsuarioCriteria();
		
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);
			int startIndex = 1;
			int pageSize = 1;
			
			uc.setEmail(email);
			usuario = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);
			if (usuario.getData() != null) {
				// en el metodo de checkpassword le pasamos la variable "password" como la contraseña plana (la que escribe el usuario, sin encriptar) y la comparamos con la contraseña encriptada de la base de datos
				
				for(UsuarioDTO u : usuario.getData()) {
					boolean passwordOK = PasswordEncryptionUtil.checkPassword(password, u.getEncryptedPassword());
					
					if(passwordOK!=true) {
						throw new InvalidUserOrPasswordException(email);
					}
				}

			} else {
				throw new InvalidUserOrPasswordException(email);
			}

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.trace("End usuario: " + usuario);

		} catch (SQLException sqle) {
			logger.error(usuario, sqle);
			throw new DataException(email, sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}


	public void anhadirFavorito(Long idCliente, Long idProveedor) throws DataException, ServiceException {
		logger.trace("Begin id Cliente: " + idCliente + ", id Proveedor: " + idProveedor);

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

			logger.trace("End id Cliente: " + idCliente + ", id Proveedor: " + idProveedor);

		} catch (SQLException sqle) {
			logger.error("Id Cliente: " + idCliente, "Id Proveedor" + idProveedor, sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	public void deleteFavorito(Long idCliente, Long idProveedor) throws DataException, ServiceException {

		logger.trace("Begin id Cliente: " + idCliente + ", id Proveedor: " + idProveedor);

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

			logger.trace("End id Cliente: " + idCliente + ", id Proveedor: " + idProveedor);

		} catch (SQLException sqle) {
			logger.error("Id Cliente: " + idCliente, "Id Proveedor" + idProveedor, sqle);
			throw new DataException(sqle);	

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	


	@Override
	public void visualiza(Long id) throws DataException, ServiceException {
		logger.trace("Begin id: " + id);

		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;

		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);
			// buscamos al objeto "usuario" por su atributo email, nos devuelve el objeto
			// completo
			usuarioDAO.visualiza(c, id);

			// fin de la transaccion a continuacion
			commitOrRollback = true;

			logger.trace("End usuario: " + usuario);

		} catch (SQLException sqle) {
			logger.error(usuario, sqle);
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public void update(UsuarioDTO u) throws DataException, UserNotFoundException, ServiceException {
		logger.trace("Begin usuario: " + u);

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			// recibe la contraseña plana de arriba, y si es distinto de null la setea en la
			// actual contraseña encryptada(y encriptandola)
			if (u.getPassword() != null) {
				u.setEncryptedPassword(PasswordEncryptionUtil.encryptPassword(u.getPassword()));
			}

			usuarioDAO.update(c, u);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End usuario: " + u);

		} catch (UserNotFoundException unfe) {
			logger.error(u, unfe);
			throw unfe;

		} catch (SQLException sqle) {
			logger.error(u, sqle);
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public void updateStatus(Long idUsuario, Integer idEstadoCuenta) throws DataException, ServiceException {
		logger.trace("Begin id Usuario : " + idUsuario + ", id Estado Cuenta: " + idEstadoCuenta);

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuarioDAO.updateStatus(c, idUsuario, idEstadoCuenta);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(idUsuario,sqle);
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public long deleteById(Long idUsuario) throws DataException, ServiceException {
		logger.trace("Begin idUsuario: " + idUsuario);

		Connection c = null;
		boolean commitOrRollback = false;
		try {
			c = ConnectionManager.getConnection();

			// inicio de la transaccion, es como un beggin
			c.setAutoCommit(false);

			usuarioDAO.deleteById(c, idUsuario);

			// fin de la transacción a continuacion
			commitOrRollback = true;

			logger.trace("End");

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);		

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return idUsuario;
	}
}