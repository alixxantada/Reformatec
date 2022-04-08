package com.alejandro.reformatec.dao;

import java.sql.Connection;
import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;

public interface EspecializacionDAO {

	public List<Especializacion> findByCriteria(Connection c, EspecializacionCriteria ec)
			throws DataException;

	public void crearEspecializacionUsuario (Connection c, Long idUsuario, List<Integer> ids)
			throws DataException;

	public void crearEspecializacionProyecto (Connection c, Long idProyecto, List<Integer> ids)
			throws DataException;

	public void crearEspecializacionTrabajo (Connection c, Long idTrabajo, List<Integer> ids)
			throws DataException;
}