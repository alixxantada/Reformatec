package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.LineaPresupuestoNotFoundException;
import com.alejandro.reformatec.model.LineaPresupuestoCriteria;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;

public interface LineaPresupuestoDAO {

	public List<LineaPresupuestoDTO> findByCriteria(Connection c, LineaPresupuestoCriteria pc)
			throws DataException;

	// devuelve el id del usuario que se creo
	public long create(Connection c, LineaPresupuestoDTO lineapresupuesto)
			throws DataException;

	//	public int update(Connection c, LineaPresupuestoDTO lineapresupuesto) 
	//			throws DataException, LineaPresupuestoNotFoundException;

	// devuelve el numero de filas borradas?
	public long deleteById(Connection c, Long idLineaPresupuesto) 
			throws DataException, LineaPresupuestoNotFoundException;
}
