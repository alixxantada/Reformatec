package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;

public interface ProyectoService {

	/**
	 * M�todo para sacar un proyecto concreto, sacar todos los proyectos o tambien podemos sacar los proyectos que tiene un usuario concreto.
	 * Adem�s tambien podemos realizar b�squedas de proyectos filtrando por (descripcion, idProvincia, PresupuestoMaxMaximo, PresupuestoMaxMinimo, idEspecializacion)
	 * Se pueden ordenar por fecha creacion asc(FC) o por el presupuestoMaxMaximo asc/desc.
	 * 
	 * @param pc: Par�metros de b�squeda para los proyectos con 8 opciones :
	 * Descripcion(String), idProvincia(Integer), PresupuestoMaxMinimo(Integer), PresupuestoMaxMaximo(Integer), idEspecializacion(Integer), OrderBy(String), idProyecto(Long), idUsuarioCreador(Long).
	 * Si enviamos todos los parametros a null tenemos el findbyall ordenado por fecha creacion desc.
	 * idProyecto : �nicamente vendr� relleno y los dem�s par�metros a null para poder buscar por id un proyecto concreto.
	 * idUsuarioCreador : �nicamente vendr� relleno y los dem�s par�metros a null para poder sacar los proyectos que tiene un usuario concreto.
	 * Los 6 par�metros restantes podremos enviarlos rellenos o a null seg�n queramos hacer una b�squeda de los proyectos.
	 * En cualquier caso el par�metro orderby tendr� un valor predefinido(FC) de los posibles : PMASC presupuesto maximo ascendente,PMDESC presupuesto maximo desdencente,FC fecha creacion.
	 * @param startIndex Donde empieza a contar la paginacion del results.
	 * @param pageSize De cuantos en cuantos va sacando el results para la paginacion.
	 * @return Devuelve el results de proyectos PAGINADOS(mirar pagesize) con orderby predefinido a fecha_creacion desc.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public Results<ProyectoDTO> findByCriteria(ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	
	/**
	 * M�todo para poder crear un proyecto.
	 * 
	 * @param p Se pasan los datos del proyecto a crear: titulo(String),descripcion(String),presupuestoMax(Integer),idPoblacion(Integer),idUsuarioCreadorProyecto(Long).
	 * @return Devuelve el id del proyecto que se cre�(Long).
	 * @throws ProyectoAlreadyExistsException el proyecto ya existia en bbdd.
	 * @throws MailException error al enviar el mail.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public long create(ProyectoDTO p, List<Integer> especializaciones) 
			throws ProyectoAlreadyExistsException, MailException, ServiceException, DataException;

	
	/**
	 * M�todo para poder actualizar un proyecto.
	 * 
	 * @param p Se pasan los siguientes datos del proyecto a actualizar:
	 * idProyecto(Long) para localizar el proyecto y la lista de especializaciones actualizada.
	 * titulo(String), descripcion(String), presupuestoMax(Integer), idPoblacion(Integer) son los datos que podemos actualizar de un proyecto.
	 * Se borrar�n las especializaciones que tenga anterioremente antes de hacer el update.
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void update(ProyectoDTO p, List<Integer> especializaciones) 
			throws DataException, ServiceException;

	
	/**
	 * M�todo para poder actualizar el estado o hacer borrado l�gico de un proyecto.
	 * 
	 * @param idProyecto : id del proyecto a actualizar el estado.
	 * @param idEstadoProyecto : id del estado que se quiere poner.(1 Activo, 2 Finalizado, 3 Borrado).
	 * @throws DataException Error en conexion BBDD o query DAOimpl.
	 * @throws ServiceException No contemplado.
	 */
	public void updateStatus(Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ServiceException;
}
