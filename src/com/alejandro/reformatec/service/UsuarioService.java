package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.CodeInvalidException;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.EmailPendienteValidacionException;
import com.alejandro.reformatec.exception.InvalidUserOrPasswordException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.exception.UserAlreadyExistsException;
import com.alejandro.reformatec.exception.UserLowInTheSystemException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;

public interface UsuarioService {




	/**
	 * M�todo para registrar un nuevo usuario.
	 * 
	 * @param u Usuario a registrar con los siguientes datos:
	 * nombre(String), apellido1 (String), apellido2 (String), nif (String), telefono1 (String), telefono2 (String) *opcional*, email (String), nombrePerfil (String), nombreCalle (String), 
	 * codPostal (String), password (String), idPoblacion (int), idProvincia (int), idTipoUsuario (int)
	 * En el registro de proveedores a mayores tenemos los siguientes datos:
	 * cif (String) *opcional*, descripcion(String), direccionWeb (String) *opcional*, servicio24 (Boolean) *opcional*
	 * Los proveedores adem�s pueden contener una lista de especializaciones ( List<Integer>) necesitando para ello el id de cada especializacion *opcional*.
	 * 
	 * @return ID del nueco usuario (Long).
	 * @throws UserAlreadyExistsException El usuario ya existe en la BBDD
	 * @throws UserLowInTheSystemException El usuario ya existe en la BBDD con ID_TIPO_ESTADO_CUENTA 3 (Cancelada) Usuario dado de baja(borrado l�gico)
	 * @throws EmailPendienteValidacionException El usuario todavia tiene pendiente de validar el correo pero ya esta registrado.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 * @throws MailException Error al enviar un email.
	 */
	public Long signUp(UsuarioDTO u, List<Integer> especializaciones, String url) 
			throws UserAlreadyExistsException, UserLowInTheSystemException, EmailPendienteValidacionException, MailException, ServiceException, DataException;


	/**
	 * M�todo para iniciar sesi�n un usuario.
	 * 
	 * @param email (String)
	 * @param password (String)
	 * @return Usuario Logueado.
	 * @throws InvalidUserOrPasswordException Usuario/Contrase�a distintas a las de BBDD.
	 * @throws EmailPendienteValidacionException El usuario todavia tiene pendiente de validar el correo pero ya esta registrado.
	 * @throws UserLowInTheSystemException Usuario con email existente en BBDD pero con ID_ESTADO_CUENTA 3 (Cancelada) El usuario esta dado de baja (borrado l�gico).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public UsuarioDTO login(String email, String password)
			throws InvalidUserOrPasswordException, UserLowInTheSystemException, EmailPendienteValidacionException, ServiceException, DataException;


	/**
	 * M�todo para sacar un usuario concreto, para sacar todos los proveedores, para sacar los favoritos que tiene un usuario,
	 *  o tambien podemos filtrar los proveedores por poblacion,provincia,especializacion, servicio24, y si estan verificados. 
	 * 
	 * @param pc Par�metros de b�squeda para los usuarios con 11 opciones:
	 * idUsuario(Long): nos devolvera el usuario concreto que busquemos. Si enviamos este parametro los demas deberan ir a null siempre.
	 * idUsuarioFavorito(Long): nos devolvera los usuarios favoritos de un usuario. Si enviamos este parametro los demas deberan ir a null siempre.
	 * Descripcion (String): filtramos proveedores que contenga en su descripcion o nombre de perfil.(Filtro Proveedor).
	 * IdProvincia(Integer): filtramos proveedores que tengan este id de provincia (Filtro Proveedor).
	 * IdPoblacion(Integer): filtramos proveedores que tengan este id de poblacion (Filtro Proveedor sin usar).
	 * Servicio24(Boolean): filtramos proveedores que tengan o no servicio24 (Filtro Proveedor).
	 * ProveedorVerificado(Boolean) : filtramos proveedores que estean o no verificados (Filtro Proveedor).
	 * IdEspecializacion(Integer): filtramos proveedores que tengan este id Especializacion (Filtro Proveedor).
	 * OrderBy(String) : Si enviamos este parametro (VAL/NV) y los demas a null obtendremos todos los proveedores con la cuenta activa (Filtro Proveedor).
	 * @param startIndex Donde empieza a contar la paginacion del results.
	 * @param pageSize De cuantos en cuantos va sacando el results para la paginacion.
	 * @return Devuelve el results de usuarios PAGINADOS(mirar pagesize) con orderby predifinido AVG(u.NUM_VISUALIZACION) DESC en caso de filtrar proveedores.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;




	/**
	 * M�todo necesario tanto para registro de un usuario como para el login.
	 * 
	 * @param uc Parametros de busqueda con 2 opciones:
	 * EmailActivo(String): nos devuelve un usuario concreto en BBDD que coincida con el email que enviamos, si tiene la cuenta activa.
	 * EmailExistente(String): nos devuelve un usuario concreto en BBDD que coincida con el email que enviamos tenga o no la cuenta activa.
	 * @return Devuelve el Usuario que corresponda con el email o null sino.
	 * @throws DataException
	 * @throws ServiceException
	 */
	public UsuarioDTO findByEmail(UsuarioCriteria uc)
			throws DataException, ServiceException;



	/**
	 * M�todo para que un usuario pueda a�adir un favorito.
	 * 
	 * @param IdCliente : id del usuario que quiere a�adir un favorito.
	 * @param idProveedor : id del proveedor que se a�ade como favorito a la lista de favoritos.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void anhadirFavorito(Long IdCliente, Long idProveedor)
			throws DataException, ServiceException;


	/**
	 * M�todo para que un usuario pueda eliminar un favorito.
	 * 
	 * @param idCliente : id del usuario que quiere borrar un favorito.
	 * @param idProveedor : id del proveedor favorito que se elimina de la lista favoritos.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void deleteFavorito(Long idCliente, Long idProveedor)
			throws DataException, ServiceException;	


	/**
	 * M�todo para a�adir 1 visualizacion
	 * 
	 * @param id : el id del usuario que recibe la visualizacion.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void visualizaUsuario(Long id)
			throws DataException, ServiceException;


	/**
	 * M�todo para poder actualizar un usuario.
	 * 
	 * @param u Se pasan los siguientes datos del usuario a actualizar:
	 * idUsuario(Long) para localizar al usuario y su lista de especializaciones actualizada.
	 * password(String), telefono1(String), telefono2(String), nombrePerfil(String), nombreCalle(String), codPostal(String), 
	 * idPoblacion(int),  son los datos que podemos actualizar de un usuario en general, de un proveedor, a mayores de �stos, podremos actualizar:
	 * descripcion(String), direccionWeb(String), servicio24(Boolean)
	 * Se borrara la lista de especializaciones que tenga anterioremente antes de hacer update, en caso de ser proveedor el tipo de usuario.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.(M�ltiples modificaciones a la vez)
	 * @throws ServiceException No contemplado.
	 */
	public void update(UsuarioDTO u, List<Integer> especializaciones) 
			throws DataException, UserNotFoundException, ServiceException;


	/**
	 * M�todo para poder actualziar el estado o hacer borrado l�gico de un usuario.
	 * 
	 * Si se hace borrado l�gico: a los trabajos, proyectos, presupuestos y valoraciones del usuario se les hace borrado logico auto.
	 * @param idUsuario : id del usuario a actualizar el estado.
	 * @param idEstadoCuenta: id del estado que se quiere poner (1 Activa 2 Validada 3 Cancelada).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.(M�ltiples modificaciones a la vez)
	 * @throws ServiceException No contemplado.
	 */
	public void updateStatus(Long idUsuario, Integer idEstadoCuenta, String url) 
			throws DataException, UserNotFoundException, CodeInvalidException, MailException, ServiceException;





	/**
	 * M�todo para garantizar la cuenta a la hora de resetear la password por olvidarse de ella.
	 * 
	 * @param idUsuario id del usuario a cambiar el code
	 * @param code codigo para recuperar pass
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.(M�ltiples modificaciones a la vez)
	 * @throws ServiceException No contemplado
	 */
	public void updateCode(Long idUsuario, String code)
			throws DataException, UserNotFoundException, ServiceException;





	/**
	 * M�todo para poder validar que el email del cliente corresponde a su email
	 * 
	 * @param email Parametro de email para comprobar con bbdd
	 * @param codRegistro Parametro codRegistro para comprobar con bbdd
	 * @throws DataException Error en conexion BBDD o query DAOimpl.	
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.
	 * @throws CodeInvalidException Error el codigo de registro no es correcto.
	 * @throws ServiceException No contemplado.
	 */
	public UsuarioDTO validaEmail(String email, String codRegistro) 
			throws DataException, UserNotFoundException, CodeInvalidException, ServiceException;




	/**
	 * M�todo para updatear contrase�a(Olvide contrase�a)
	 * 
	 * @param idUsuario Usuario que modifica contrase�a
	 * @param password Parametro de la contrase�a a modificar
	 * @throws DataException Error en conexion BBDD o query DAOimpl.	
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.
	 * @throws ServiceException No contemplado.
	 */
	public void updatePassword(Long idUsuario, String password)
			throws DataException, UserNotFoundException, ServiceException;




	/**
	 * M�todo para borrar un usuario.(Sin usar actualmente)
	 * 
	 * @param idUsuario
	 * @return Devuelve el n�mero de usuarios borrados(1) si fue todo bien sino UserNotFoundException
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws UserNotFoundException Error no se encuentra el usuario con por el id indicado.(M�ltiples modificaciones a la vez)
	 * @throws ServiceException No contemplado.
	 */
	public long deleteById(Long idUsuario) 
			throws DataException, UserNotFoundException, ServiceException;

}