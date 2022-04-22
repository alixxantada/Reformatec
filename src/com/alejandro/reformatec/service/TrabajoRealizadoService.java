package com.alejandro.reformatec.service;


import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;


public interface TrabajoRealizadoService {



	/**
	 * M�todo para sacar un trabajo realizado concreto, sacar todos los trabajos realizados o tambien podemos sacar los trabajos realizados que tiene un proveedor(usuario) concreto.
	 * Adem�s tambien podemos realizar b�squedas de trabajos realizados filtrando por (descripcion, idProvincia, idEspecializacion)
	 * Se pueden ordenar en el parameto "OrderBy" por valoracion media desc(VAL), fecha de creacion desc(FC) o numero de visualizaciones desc(NV).
	 * 
	 * @param trc Par�metros de b�squeda para los trabajos realizados con 6 opciones:
	 * descripcion(String), idProvincia(Integer), idProveedor(Long), idTrabajoRealizado(Long), idEspecializacion(Integer), Orderby(String).
	 * Si enviamos todos los par�metros a null tenemos el findbyall ordenador por num_visualizacion desc.
	 * idProveedor : Unicamente vendr� relleno y los dem�s par�metros a null para poder sacar los trabajos realizados que tiene un proveedor.
	 * idTrabajoRealizado : �nicamente vendr� relleno y los dem�s par�metros null para poder buscar un trabajo realizado concreto por su id.
	 * @param startIndex Donde empieza a contar la paginacion del results.
	 * @param pageSize De cuantos en cuantos va sacando el results para la paginacion.
	 * @return Devuelve el results de trabajos realizados PAGINADOS(mirar pagesize) con orderby predefinido a num_visualizaciones desc.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Results<TrabajoRealizadoDTO> findByCriteria(TrabajoRealizadoCriteria trc, int startIndex, int pageSize)
			throws DataException, ServiceException;



	/**
	 * M�todo para a�adir 1 visualizacion
	 * 
	 * @param id : el id del trabajo que recibe la visualizacion.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void visualizaTrabajo(Long id)
			throws DataException, ServiceException;

	/**
	 * M�todo para crear un Trabajo Realizado.
	 * 
	 * @param tr Par�metros para crear un trabajo realizado: titulo(String), descripcion(String), idPobacion(Integer), idUsuarioCreador(Long).
	 * @return Devuelve el id del trabajoRealizado que se cre� (Long).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public long create(TrabajoRealizadoDTO tr, List<Integer> especializaciones) 
			throws DataException, ServiceException;


	/**
	 * M�todo para poder actualizar un trabajo realizado.
	 * 
	 * @param tr Se pasan los siguientes datos del trabajo a actualizar:
	 * idTrabajoRealizado(Long) para poder localizar el trabajo realizado y la lista de especializaciones actualizada.
	 * titulo(String), descripcion(String), idPoblacion(Integer), idProyecto(Long)
	 * Se borrar�n las especializaciones que tenga el trabajo previamente a hacer update.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void update(TrabajoRealizadoDTO tr, List<Integer> especializaciones) 
			throws DataException, ServiceException;


	/**
	 * M�todo para poder actualizar el estado o hacer borrado l�gico a un trabajo realizado.
	 * 
	 * @param idTrabajoRealizado : id del trabajo realizado a actualizar(Long).
	 * @param idEstadoTrabajoRealizado : id del estado que se quiere poner al trabajo realizado (1 Activo, 2 Eliminado).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void updateStatus(Long idTrabajoRealizado, Integer idEstadoTrabajoRealizado) 
			throws DataException, ServiceException;
}