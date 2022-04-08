package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class LineaPresupuestoCriteria extends AbstractValueObject {

	private Long idLineaPresupuesto;

	private Long idPresupuesto;

	private Long idProyecto;

	public Long getIdLineaPresupuesto() {
		return idLineaPresupuesto;
	}

	public void setIdLineaPresupuesto(Long idLineaPresupuesto) {
		this.idLineaPresupuesto = idLineaPresupuesto;
	}

	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
}