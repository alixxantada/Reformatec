package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;

public interface EspecializacionService {

	public List<Especializacion> findByCriteria(EspecializacionCriteria ec)
			throws DataException, ServiceException;

}
