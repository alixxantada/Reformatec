package com.alejandro.reformatec.service;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.MailException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;

public interface ProyectoService {

	public Results<ProyectoDTO> findByCriteria(ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException, ServiceException;

	public long create(ProyectoDTO p) 
			throws ProyectoAlreadyExistsException, MailException, ServiceException, DataException;

	public void update(ProyectoDTO p) 
			throws DataException, ServiceException;

	public void updateStatus(Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ServiceException;
}
