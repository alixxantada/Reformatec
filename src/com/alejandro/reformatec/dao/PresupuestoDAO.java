package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.PresupuestoNotFoundException;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;

public interface PresupuestoDAO {

	
	public Results<PresupuestoDTO> findByCriteria(Connection c, PresupuestoCriteria pc, int startIndex, int pageSize)
			throws DataException;
	
	//devuelve el id del usuario que se creó
	public long create(Connection c, PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas)
			throws DataException;

	// devuelve el numero de filas actualizadas
	public int update(Connection c, PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas) 
			throws DataException, PresupuestoNotFoundException;

	public int updateStatus(Connection c, Long idPresupuesto, Integer idEstadoPresupuesto) 
			throws DataException, PresupuestoNotFoundException;
}
