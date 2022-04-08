package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class PoblacionCriteria extends AbstractValueObject {

	
	private Integer idPoblacion;
	private Integer idProvincia;
	
	public Integer getIdPoblacion() {
		return idPoblacion;
	}
	public void setIdPoblacion(Integer idPoblacion) {
		this.idPoblacion = idPoblacion;
	}
	public Integer getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	
}
