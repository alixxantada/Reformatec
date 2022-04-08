package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.InvalidUserOrPasswordException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.exception.UserAlreadyExistsException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;

public interface UsuarioService {

	/**
	 * Registra un nuevo usuario.
	 * @param u Usuario a registrar, con todos sus datos obligatorios rellenos.
	 * @return Identificador en el sistema del nuevo usuario.
	 * @throws UserAlreadyExistsException Cuando el usuario ya existe en el sistema.
	 * @throws ServiceException En otro caso, por ejemplo, cuando alguno(s) de los datos
	 * obligatorios no está cumplimentado o su valor es incorrecto.
	 */

	// es el create 
	public Long signUp(UsuarioDTO u) 
			throws UserAlreadyExistsException, ServiceException, DataException, MailException;


	/**
	 * 
	 * @param email
	 * @param password
	 * @return Usuario
	 * @throws InvalidUserOrPasswordException
	 * @throws ServiceException
	 * @throws DataException
	 */
	public Results<UsuarioDTO> login(String email, String password)
			throws InvalidUserOrPasswordException, ServiceException, DataException;

	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	public void anhadirFavorito(Long IdCliente, Long idProveedor)
			throws DataException, ServiceException;

	public void deleteFavorito(Long idCliente, Long idProveedor)
			throws DataException, ServiceException;	

	public void visualiza(Long id)
			throws DataException, ServiceException;

	public void update(UsuarioDTO u) 
			throws DataException, ServiceException;

	public void updateStatus(Long idUsuario, Integer idEstadoCuenta) 
			throws DataException, ServiceException;

	public long deleteById(Long idUsuario) 
			throws DataException, ServiceException;
}