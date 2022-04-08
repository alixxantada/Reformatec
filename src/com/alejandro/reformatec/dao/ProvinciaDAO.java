package com.alejandro.reformatec.dao;


import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.Provincia;

public interface ProvinciaDAO {

	public List<Provincia> findByCriteria(Connection c, Integer id)
			throws DataException;
	
}