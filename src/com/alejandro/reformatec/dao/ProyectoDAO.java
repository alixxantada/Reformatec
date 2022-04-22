package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ProyectoNotFoundException;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;


public interface ProyectoDAO {

	public Results<ProyectoDTO> findByCriteria(Connection c, ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException;

	// devuelve el long id que creo
	public long create(Connection c, ProyectoDTO proyecto, List<Integer> especializaciones) 
			throws DataException;

	// devuelve el numero de filas actualizadas
	public int update(Connection c, ProyectoDTO proyecto, List<Integer> especializaciones)
			throws DataException, ProyectoNotFoundException;

	public int updateStatus(Connection c, Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ProyectoNotFoundException;
}
