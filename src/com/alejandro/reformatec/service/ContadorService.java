package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;

public interface ContadorService {

	/**
	 * Metodo para sacar la cantidad de clientes.
	 * 
	 * @return Devuelve el número de usuarios de tipo cliente en Integer.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Integer cliente()
			throws DataException, ServiceException;
	
	
	/**
	 * Metodo para sacar la cantidad de proveedores.
	 * 
	 * @return Devuelve el número de usuarios de tipo proveedor en Integer.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Integer proveedor()
			throws DataException, ServiceException;
	
	
	/**
	 * Metodo para sacar la cantidad de proyectos activos.
	 * 
	 * @return Devuelve el número de proyectos activos en Integer.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Integer proyectoActivo()
			throws DataException, ServiceException;
	
	
	/**
	 * Metodo para sacar la cantidad de proyectos finalizados.
	 * 
	 * @return Devuelve el número de proyectos finalizados en Integer.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Integer proyectoFinalizado()
			throws DataException, ServiceException;
	
	
	/**
	 * Metodo para sacar la cantidad de trabajos realizados.
	 * 
	 * @return Devuelve el número de trabajos realizados en Integer.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Integer trabajoRealizado()
			throws DataException, ServiceException;

}
