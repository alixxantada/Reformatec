package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Provincia;

public interface ProvinciaService {

	/**
	 * Método para sacar una provincia concreta o sacar todas las provincias.
	 * 
	 * @param idProvincia(Integer) indicamos que provincia queremos o enviamos a null para traer todas las provincias.
	 * @return Devuelve la lista de provincias  ordenadas por el idProvincia asc.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public List<Provincia> findByCriteria(Integer idProvincia)
			throws DataException, ServiceException;	
}
