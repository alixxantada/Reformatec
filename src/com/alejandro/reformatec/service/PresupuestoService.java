package com.alejandro.reformatec.service;


import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;

public interface PresupuestoService {


	public Results<PresupuestoDTO> findByCriteria(PresupuestoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	public long create(PresupuestoDTO p,List<LineaPresupuestoDTO> lineas) 
			throws DataException, ServiceException;

	public void update(PresupuestoDTO p) 
			throws DataException, ServiceException;

	public void updateStatus(Long idPresupuesto, Integer idEstadoPresupuesto) 
			throws DataException, ServiceException;
}
