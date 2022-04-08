package com.alejandro.reformatec.dao;

import java.sql.Connection;

import com.alejandro.reformatec.exception.DataException;

public interface ContadorDAO {
	
	public Integer cliente(Connection c)
			throws DataException;
	
	public Integer proveedor(Connection c)
			throws DataException;
	
	public Integer proyectoActivo(Connection c)
			throws DataException;
	
	public Integer proyectoFinalizado(Connection c)
			throws DataException;
	
	public Integer trabajoRealizado(Connection c)
			throws DataException;

}
