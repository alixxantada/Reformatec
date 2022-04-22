package com.alejandro.reformatec.service;


import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;

public interface PresupuestoService {


	/**
	 * Método para sacar un presupuesto concreto, todos los presupuestos, los presupuestos que hizo un proveedor o los presupuestos que tiene un proyecto.
	 * 
	 * @param pc Parámetros de búsqueda para los presupuestos con 4 opciones:
	 * idPresupuesto (Long): devuelve el presupuesto concreto que busquemos por id.(Demás parametros a null)
	 * idProveedor (Long) : devuelve los presupuestos que tiene un proveedor concreto.(Se puede combinar con idTipoEstadoPresupuesto solamente,opcional)
	 * idProyecto (Long) : devuelve los presupuestos que tiene un proyecto concreto.(Se puede combinar con idTipoEstadoPresupuesto solamente,opcional)
	 * idTipoEstadoPresupuesto (Integer): Podemos filtrar los presupuestos a mostrar por el estado en el que se encuentran o si va a null enseña todos los presupuestos menos los que están borrados.(Borrado lógico)
	 * @param startIndex Donde empieza a contar la paginacion del results.
	 * @param pageSize De cuantos en cuantos va sacando el results para la paginacion.
	 * @return Devuelve el results de presupuestos PAGINADOS con orderBy predefinido p.FECHA_HORA_CREACION DESC.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Results<PresupuestoDTO> findByCriteria(PresupuestoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	
	/**
	 * Método para crear un presupuesto.
	 * 
	 * @param p Contiene los siguientes parametros para crear un presupuesto:
	 * titulo(String), descripcion(String), idProyecto(Long), idUsuarioCreadorPresupuesto(Long).
	 * El propio service calcula el total del presupuesto en base a los importes de sus lineas y le pone la hora de creacion.
	 * @param lineas Parametro donde le pasamos la lista de objetos LineaPresupuesto que pertenecen a este presupuesto a la hora de crearlo.Cada LineaPresupuestoDTO contiene el importe(Double) y la descripcion(String).
	 * @return Devuelve el id del presupuesto que se creo.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public long create(PresupuestoDTO p,List<LineaPresupuestoDTO> lineas) 
			throws DataException, ServiceException;

	
	/**
	 * Método para actualizar un presupuesto.
	 * 
	 * @param p Contiene los siguientes parametros para actualizar un presupuesto:
	 * titulo(String), descripcion(String), idProyecto(Long), idUsuarioCreadorPresupuesto(Long).
	 * El propio service borra las anteriores lineas de presupuesto, calcula el total del presupuesto en base a los importes de sus lineas actualizadas y le pone la hora de creacion actualizada.
	 * @param lineas Parametro donde le pasamos la lista de objetos LineaPresupuesto que pertenecen a este presupuesto a la hora de actualizarlo.Cada LineaPresupuestoDTO contiene el importe(Double) y la descripcion(String).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void update(PresupuestoDTO p, List<LineaPresupuestoDTO> lineas) 
			throws DataException, ServiceException;

	
	/**
	 * Método para poder actualizar el estado de un presupuesto o hacer borrado lógico.
	 * 
	 * @param idPresupuesto : id del presupuesto que se quiere actualizar el estado.
	 * @param idEstadoPresupuesto : id del estado que se quiere poner (1 Enviado, 2 Aceptado, 3 Rechazado, 4 Eliminado).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void updateStatus(Long idPresupuesto, Integer idEstadoPresupuesto) 
			throws DataException, ServiceException;
}
