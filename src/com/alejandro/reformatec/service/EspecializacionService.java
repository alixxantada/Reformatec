package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;

public interface EspecializacionService {

	/**
	 * Metodo para sacar las especializaciones que tiene un usuario, para las de un trabajo realizado o las de un proyecto, además de sacar todas las especializaciones(enviando todo a null) o una concreta.
	 * 
	 * @param ec : 4 Parámetros de busqueda posibles,solo 1 relleno y los otros 3 a null: idEspecializacion(Integer), idProyecto(Long), idTrabajoRealizado(Long), idUsuario(Long).
	 *  idEspecializacion: devuelve la especializacion indicada por el id de la especializacion(Integer).
	 *  idProyecto: devuelve las especializaciones del proyecto indicado por el id del Proyecto(Long).
	 *  idTrabajoRealizado: devuelve las especializaciones del trabajo realizado indicado por el id del Trabajo Realziado(Long).
	 *  idUsuario:  devuelve las especializaciones de un proveedor indicado por el id del Proveedor(Long).
	 * @return Devuelve la lista de especializaciones que se ha filtrado en el "ec" ordenadas por el idEspecializacion asc.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public List<Especializacion> findByCriteria(EspecializacionCriteria ec)
			throws DataException, ServiceException;

}
