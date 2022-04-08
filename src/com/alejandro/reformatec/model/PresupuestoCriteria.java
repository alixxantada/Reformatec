package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class PresupuestoCriteria extends AbstractValueObject {

	private Long idPresupuesto;

	private Long idProyecto;

	private Long idProveedor;

	// para cuando queiro buscar todos mis presupuestos o quiero enseñar los presupuestos pendientes de aceptar...
	private Integer idTipoEstadoPresupuesto;

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

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdTipoEstadoPresupuesto() {
		return idTipoEstadoPresupuesto;
	}

	public void setIdTipoEstadoPresupuesto(Integer idTipoEstadoPresupuesto) {
		this.idTipoEstadoPresupuesto = idTipoEstadoPresupuesto;
	}
}