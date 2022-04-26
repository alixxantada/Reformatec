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

	public long create(Connection c, UsuarioDTO usuario, List<Integer> especializaciones) 
			throws DataException;

	public int update(Connection c, UsuarioDTO usuario, List<Integer> especializaciones)
			throws DataException, UserNotFoundException;

	public int updateStatus(Connection c, Long idUsuario, Integer idEstadoCuenta) 
			throws DataException, UserNotFoundException;

	public long deleteById(Connection c, Long idUsuario)
			throws DataException, UserNotFoundException;

	public int updateCode(Connection c, Long idUsuario, String code)
			throws DataException, UserNotFoundException;
	
	public int updatePassword(Connection c, Long idUsuario, String password)
			throws DataException, UserNotFoundException;
	
}
