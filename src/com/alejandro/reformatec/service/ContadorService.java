package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;

public interface ContadorService {

	public Integer cliente()
			throws DataException, ServiceException;
	
	public Integer proveedor()
			throws DataException, ServiceException;
	
	public Integer proyectoActivo()
			throws DataException, ServiceException;
	
	public Integer proyectoFinalizado()
			throws DataException, ServiceException;
	
	public Integer trabajoRealizado()
			throws DataException, ServiceException;

}
