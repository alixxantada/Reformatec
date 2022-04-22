package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;

public interface PoblacionService {

	/**
	 * Método para poder sacar una poblacion concreta, sacar todas las poblaciones o sacar las poblaciones que tiene una provincia.
	 * 
	 * @param pc: 2 parámetros de búsqueda posibles: idPoblacion(Integer), idProvincia(Integer).Solo 1 relleno y el otro a null para filtrar o los 2 parametros a null para traer todas las poblaciones 
	 *  idPoblacion : devuelve la poblacion indicada por el id de la poblacion(Integer)
	 *  idProvincia : devuelve las poblaciones indicadas por el id de la provincia (Integer)
	 * @return  Devuelve la lista de poblaciones que se ha filtrado en el "pc" ordenada por el el idPoblacion asc
	 * @throws DataException Error en conexion BBDD o query DAOimpl
	 * @throws ServiceException No contemplado
	 */
	public List<PoblacionDTO> findByCriteria(PoblacionCriteria pc)
			throws DataException, ServiceException;

}
