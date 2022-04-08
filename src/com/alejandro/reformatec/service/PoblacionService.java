package com.alejandro.reformatec.service;

import java.util.List;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;

public interface PoblacionService {

	public List<PoblacionDTO> findByCriteria(PoblacionCriteria pc)
			throws DataException, ServiceException;

}
