package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Provincia;

public interface ProvinciaService {

	public List<Provincia> findByCriteria(Integer idProvincia)
			throws DataException, ServiceException;	
}
