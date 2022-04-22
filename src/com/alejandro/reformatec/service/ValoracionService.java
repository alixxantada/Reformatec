package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;

public interface ValoracionService {

	
	
	/**
	 * M�todo para sacar una valoraci�n concreta, savar todas las valoraciones, sacar las valoraciones que tiene un trabajo_realizado,
	 * sacar las valoraciones que tiene un proveedor, sacar las valoraciones que realiza un usuario a trabajos_realizados o a proveedores.
	 * 
	 * @param vc: Par�metros de b�squeda para las valoraciones con 5 opciones:
	 * idValoracion(Long), idTrabajoRealizadoValorado(Long), idProveedorValorado(Long), idUsuarioValoraTrabajo(Long), idUsuarioValoraProveedor(Long)
	 * Si enviamos todo a null sacamos el total de valoraciones
	 * En cualquier otro caso solo debe ir 1 relleno y los dem�s a null d�ndose estas opciones:
	 * idValoracion: Sacamos una valoracion concreta por su id.
	 * idTrabajoRealizadoValorado : Sacamos las valoraciones que tenga un trabajo realizado concreto buscando por su id.
	 * idProveedorValorado: Sacamos las valoraciones que tenga un proveedor concreto buscando por su id.
	 * idUsuarioValoraTrabajo : Sacamos las valoraciones que realiz� a trabajos realizados un usuario concreto buscando por el id del usuario que valora.
	 * idUsuarioValoraProveedor : Sacamos las valoraciones que realiz� a proveedores un usuario concreto buscando por el id del usuario que valora.
	 * @param startIndex Donde empieza a contar la paginacion del results.
	 * @param pageSize De cuantos en cuantos va sacando el results para la paginacion.
	 * @return Devuelve el results de valoraciones PAGINADAS(mirar pagesize) con orderby predefinido a fecha_creacion desc.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Results<ValoracionDTO> findByCriteria(ValoracionCriteria vc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	
	/**
	 * M�todo para poder crear una valoraci�n.
	 * 
	 * @param v Par�metros para crear una valoracion: titulo(String), descripcion(String), notaValoracion(int), idUsuarioValora(Long), idProveedorValorado(Long) O idTrabajoRealizadoValorado(Long).
	 * Tengo constrain para que la valoraci�n tenga o idProveedorValorado o idTrabajoRealizado y nunca los 2 estando 1 de ellos siempre a null ya que una valoraci�n es de un trabajo o de un proveedor(usuario).
	 * @return Devuelve el id de la valoraci�n que se ha creado (Long).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public long create(ValoracionDTO v) 
			throws DataException, ServiceException;

	
	/**
	 * M�todo para poder actualizar el estado o hacer borrado l�gico a una valoraci�n.
	 * 
	 * @param idValoracion : id de la valoracion que queremos actualizar(Long)
	 * @param idEstadoValoracion: id del estado que se quiere poner a la valoracion (1 Activa, 2 Eliminada, 3 Denunciada).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void updateStatus(Long idValoracion, Integer idEstadoValoracion) 
			throws DataException, ServiceException;
}
