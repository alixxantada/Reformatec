package com.alejandro.reformatec.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.impl.UsuarioDAOImpl;
import com.alejandro.reformatec.dao.util.ConnectionManager;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.exception.UserAlreadyExistsException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioDTO;
import com.alejandro.reformatec.service.UsuarioService;
import com.alejandro.reformatec.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {

	private static Logger logger = LogManager.getLogger(UsuarioServiceTest.class);
	// lo primero que vamos hacer es un atributo para dar visibilidad a UsuarioService en vez de dar visibilidad de la implementacion del codigo (UsuarioServiceImpl)
	private UsuarioService usuarioservice = null;
	private UsuarioDAO usuariodao = null;
	private UsuarioCriteria uc = new UsuarioCriteria();
	private UsuarioDTO usuario = new UsuarioDTO();
	private Results<UsuarioDTO> results = new Results<UsuarioDTO>();

	public UsuarioServiceTest(){
		usuarioservice = new UsuarioServiceImpl();
		usuariodao = new UsuarioDAOImpl();
	}



	public void testFindByCriteria() {
		logger.trace("Begin...");

		////////////////////////////////////////////////////////
		uc.setDescripcion("omartinez");
		uc.setIdProvincia(null);
		uc.setExpertoNegocio(null);
		uc.setServicio24(true);
		uc.setProveedorVerificado(null);
		uc.setIdEspecializacion(null);
		uc.setOrderBy(null);
		uc.setEmail(null);
		uc.setIdUsuario(null);
		uc.setIdUsuarioFavorito(null);
		uc.setIdPoblacion(null);

		int startIndex = 1;
		int pageSize = 3;
		///////////////////////////////////////////////////
		// OrderBy opcion VAL -  ORDER BY AVG(v.NOTA_VALORACION) DESC)
		// OrderBy opcion NV -  ORDER BY u.NUM_VISUALIZACION DESC
		/////////////////////////////////////////////////////////
		try {
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			while(startIndex<results.getTotal()) {
				startIndex = startIndex+results.getData().size();
			}			
			logger.trace("Total resultados: "+results.getTotal());
			logger.trace("Resultados: "+results.getData());
			logger.trace("End!");
		} catch(DataException de) {
			logger.error(uc, de);
		} catch (ServiceException se) {
			logger.error(uc, se);
		}
	}
	


	


	public void testUsuarioLogin() {
		logger.trace("Begin...");

		////////////////////////////////////////////
		String email = "elportutatiss@gmail.com";
		String password = "Aa12345678xx";
		///////////////////////////////////////////
		try {

			results = usuarioservice.login(email, password);

			if (results != null) {
				logger.info("Bienvenido a Reformatec ");
			} else {
				logger.info("Usuario o contraseña incorrecta ");
			}

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(usuario, de);
		}  catch(ServiceException se){
			logger.error(usuario, se);
		}
	}



	public void testAnhadirFavorito() {
		logger.trace("Begin...");

		////////////////////////////
		Long idCliente = 1L;
		Long idProveedor = 14L;

		////////////////////////////
		try {

			usuarioservice.anhadirFavorito(idCliente, idProveedor);

			logger.trace("End! Cliente con id "+idCliente+" añade a favoritos al proveedor con id : "+idProveedor);
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}


	public void testDeleteFavorito() {
		logger.trace("Begin...");

		////////////////////////////
		Long idCliente = 1L;
		Long idProveedor = 14L;

		////////////////////////////
		try {

			usuarioservice.deleteFavorito(idCliente, idProveedor);

			logger.trace("End! Cliente con id "+idCliente+" borra de favoritos al proveedor con id : "+idProveedor);
		} catch(DataException de) {
			logger.error(de);
		}  catch(ServiceException se){
			logger.error(se);
		}
	}



	public void testCompruebaFavorito() {
		logger.trace("Begin...");
		Connection c = null;
		////////////////////////////
		Long idCliente = 13L;
		Long idProveedor = 15L;

		////////////////////////////
		try {
			c = ConnectionManager.getConnection();	
			usuariodao.compruebaFavorito(c, idCliente, idProveedor);

			logger.trace("End! Cliente con id "+idCliente+", proveedor con id : "+idProveedor+ " comprobado");
		} catch(DataException de) {
			logger.error(de);
		}  catch(SQLException sqle){
			logger.error(sqle);
		}
	}


	
	
	public void testVisualiza() {
		logger.trace("Begin...");
		Connection c = null;

		/////////////////////////////////////////
		Long idUsuario = 2L;
		/////////////////////////////////////////
		try {
			c = ConnectionManager.getConnection();	
			usuariodao.visualiza(c, idUsuario);

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(idUsuario, de);
		}  catch(SQLException sqle){
			logger.error(sqle);
		}  catch(UserNotFoundException unfe){
			logger.error(unfe);
		}finally {
			//JDBCUtils.closeConnection(c); Como cierro las conexiones en estos test,  me hago un metodo para eso o da igual? le hago comit/rollback al test?
		}
	}
	
	


	public void testSingUp() {
		logger.trace("Begin...");

		List<Integer> idsEspecializaciones = new ArrayList<Integer>();

		Long num = System.currentTimeMillis();
		/////////////////////////////////////////////////////////////
		String nombre = "PEDRO";
		String apellido1 = "pruebape1";
		String apellido2 = "pruebape2";
		String nif = "11288856J";
		String telefono1 = "999999999";
		String telefono2 = null;
		String email = "prueba"+num+"2222@hotmail.com";
		String nombrePerfil = "afuegoo";
		String nombreCalle = "Cl/ de la ilusion nº1 bj";
		String codigoPostal = "27500";
		String Password = "Proband0";
		int idPoblacion = 1;
		int idProvincia = 2;
		int idTipoUsuario = 1;
		String cif = null;
		String descripcion = null;
		String direccionWeb = null;
		Boolean servicio24 = null;
		Boolean expertoNegocio = null;

		idsEspecializaciones.add(1);
		idsEspecializaciones.add(2);
		idsEspecializaciones.add(3);

		///////////////////////////////////////////////////////////////
		usuario.setNombre(nombre);
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setNif(nif);
		usuario.setTelefono1(telefono1);
		usuario.setTelefono2(telefono2);
		usuario.setEmail(email);
		usuario.setNombrePerfil(nombrePerfil);
		usuario.setNombreCalle(nombreCalle);
		usuario.setCodigoPostal(codigoPostal);
		usuario.setPassword(Password);
		usuario.setIdPoblacion(idPoblacion);
		usuario.setIdProvincia(idProvincia);
		usuario.setIdTipoUsuario(idTipoUsuario);
		usuario.setCif(cif);
		usuario.setDescripcion(descripcion);
		usuario.setDireccionWeb(direccionWeb);
		usuario.setServicio24(servicio24);
		usuario.setExpertoNegocio(expertoNegocio);
		usuario.setIdsEspecializaciones(idsEspecializaciones);

		try {

			usuarioservice.signUp(usuario);

			int startIndex = 1;
			int pageSize = 1;

			uc.setIdUsuario(usuario.getIdUsuario());
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			logger.trace("Usuario Creado!: "+results.getData());

			logger.trace("End!");
		} catch (UserAlreadyExistsException uae) {		
			logger.error(usuario, uae);
		} catch (MailException me) {
			logger.error(usuario, me);
		} catch(DataException de) {
			logger.error(usuario, de);			
		} catch(ServiceException se){
			logger.error(usuario, se);
		}
	}



	public void testUpdate() {
		logger.trace("Begin...");

		//////////////////////////////////////////
		Long idUsuario = 4L;
		String password = "Aa1234567899";
		String telefono1 = "999999999";
		String telefono2 = null;
		String nombrePerfil = "Pepepruebas";
		String nombreCalle = "Cl/ de la ilusion nº1 bj";
		String codPostal = "27500";
		int idPoblacion = 1;
		String descripcion =null;
		String direccionWeb =null;
		Boolean servicio24 = null;
		Boolean expertoNegocio = null;
		//////faltan las especializaciones...
		///////////////////////////////////////////
		try {
			
			int startIndex = 1;
			int pageSize = 1;
			
			uc.setIdUsuario(idUsuario);
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			logger.info("Usuario sin actualizar: "+results.getData());

			usuario.setPassword(password);
			usuario.setTelefono1(telefono1);
			usuario.setTelefono2(telefono2);
			usuario.setNombrePerfil(nombrePerfil);
			usuario.setNombreCalle(nombreCalle);
			usuario.setCodigoPostal(codPostal);
			usuario.setIdPoblacion(idPoblacion);
			usuario.setDescripcion(descripcion);
			usuario.setDireccionWeb(direccionWeb);
			usuario.setServicio24(servicio24);
			usuario.setExpertoNegocio(expertoNegocio);
			//tengo lio con la lista de especializaciones por todos lados
			//usuario.setEspecializaciones(null);

			usuarioservice.update(usuario);

			uc.setIdUsuario(idUsuario);
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			logger.info("Usuario actualizado!: "+results.getData());


			logger.trace("End!");
		} catch(DataException de) {
			logger.error(usuario, de);
		}  catch(ServiceException se){
			logger.error(usuario, se);
		}
	}




	public void testUpdateStatus() {
		logger.trace("Begin...");

		///////////////////////////////
		Long idUsuario = 1L;
		int idEstado = 2;
		///////////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;
			
			uc.setIdUsuario(idUsuario);
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			logger.info("Usuario sin actualizar: "+results.getData());

			usuarioservice.updateStatus(idUsuario, idEstado);

			uc.setIdUsuario(idUsuario);
			results = usuarioservice.findByCriteria(uc, startIndex, pageSize);
			logger.info("Usuario sin actualizar: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(usuario, de);
		}  catch(ServiceException se){
			logger.error(usuario, se);
		}
	}






	public static void main (String args[])
			throws ServiceException, DataException {

		UsuarioServiceTest test = new UsuarioServiceTest();

		test.testFindByCriteria();
		//test.testVisualiza();
		//test.testCompruebaFavorito();
		//test.testAnhadirFavorito();
		//test.testDeleteFavorito();
		//test.testUsuarioLogin();
		//test.testSingUp();
		//test.testUpdate();
		//test.testValidaProveedor(); (sin hacer ni implementar)
		//test.testUpdateStatus();
	}
}