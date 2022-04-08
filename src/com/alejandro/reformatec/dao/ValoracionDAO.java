package com.alejandro.reformatec.dao;

import java.sql.Connection;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ValoracionNotFoundException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;

public interface ValoracionDAO {


	public Results<ValoracionDTO> findByCriteria(Connection c, ValoracionCriteria vc, int startIndex, int pageSize)
			throws DataException;

	public long create(Connection c, ValoracionDTO valoracion)
			throws DataException;

	public int updateStatus(Connection c, Long idValoracion, Integer idEstadoValoracion) 
			throws DataException, ValoracionNotFoundException;
}
