package com.alejandro.reformatec.service;


import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;


public interface TrabajoRealizadoService {


	public Results<TrabajoRealizadoDTO> findByCriteria(TrabajoRealizadoCriteria trc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	public long create(TrabajoRealizadoDTO tr) 
			throws DataException, ServiceException;

	public void update(TrabajoRealizadoDTO tr) 
			throws DataException, ServiceException;

	public void updateStatus(Long idTrabajoRealizado, Integer idEstadoTrabajoRealizado) 
			throws DataException, ServiceException;
}
