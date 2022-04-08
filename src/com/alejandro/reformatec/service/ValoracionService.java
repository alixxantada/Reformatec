package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;

public interface ValoracionService {


	public Results<ValoracionDTO> findByCriteria(ValoracionCriteria vc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	public long create(ValoracionDTO v) 
			throws DataException, ServiceException;

	public void updateStatus(Long idValoracion, Integer idEstadoValoracion) 
			throws DataException, ServiceException;
}
