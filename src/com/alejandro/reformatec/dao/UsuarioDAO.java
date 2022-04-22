package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;


public interface UsuarioDAO {


	public Results<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria uc, int startIndex, int pageSize)
			throws DataException;

	public UsuarioDTO findByEmail(Connection c, UsuarioCriteria uc)
			throws DataException;
	
	public void anhadirFavorito(Connection c , Long idCliente, Long idProveedor)
			throws DataException;

	public void deleteFavorito(Connection c, Long idCliente, Long idProveedor)
			throws DataException;

	public Boolean compruebaFavorito(Connection c, Long idCliente, Long idProveedor) 
			throws DataException;

	public void visualizaUsuario(Connection c, Long idUsuario)
			throws DataException;

	//devuelve el id del usuario que se creó
	public long create(Connection c, UsuarioDTO usuario, List<Integer> especializaciones) 
			throws DataException;

	// devuelve el numero de filas actualizadas
	public int update(Connection c, UsuarioDTO usuario, List<Integer> especializaciones)
			throws DataException, UserNotFoundException;

	public int updateStatus(Connection c, Long idUsuario, Integer idEstadoCuenta) 
			throws DataException, UserNotFoundException;

	// devuelve el numero de filas borradas
	public long deleteById(Connection c, Long idUsuario)
			throws DataException, UserNotFoundException;


}
