package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;

public interface PoblacionDAO {

	public List<PoblacionDTO> findByCriteria(Connection c, PoblacionCriteria pc)
			throws DataException;
}
