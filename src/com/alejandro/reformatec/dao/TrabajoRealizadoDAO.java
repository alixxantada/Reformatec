package com.alejandro.reformatec.dao;

import java.sql.Connection;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.TrabajoRealizadoNotFoundException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;

public interface TrabajoRealizadoDAO {


	public Results<TrabajoRealizadoDTO> findByCriteria(Connection c, TrabajoRealizadoCriteria trc, int startIndex, int pageSize)
			throws DataException;

	//devuelve el id del usuario que se creó
	public long create(Connection c, TrabajoRealizadoDTO trabajoRealizado)
			throws DataException;

	// devuelve el numero de filas actualizadas
	public int update(Connection c, TrabajoRealizadoDTO trabajoRealizado)
			throws DataException, TrabajoRealizadoNotFoundException;

	public int updateStatus(Connection c, Long idTrabajoRealizado, Integer idEstadoTrabajoRealizado) 
			throws DataException, TrabajoRealizadoNotFoundException;
}

