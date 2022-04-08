package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class LineaPresupuestoDTO extends AbstractValueObject{

	private Long idLineaPresupuesto;
	private Double importe;
	private String descripcion;

	private Long idPresupuesto;
	private String tituloPresupuesto;

	private Long idProyecto;
	private String tituloProyecto;

	private int idTipoEstadoLineaPresupuesto;
	private String nombreTipoEstadoLineaPresupuesto;

	public LineaPresupuestoDTO() {

	}

	public Long getIdLineaPresupuesto() {
		return idLineaPresupuesto;
	}

	public void setIdLineaPresupuesto(Long idLineaPresupuesto) {
		this.idLineaPresupuesto = idLineaPresupuesto;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public String getTituloPresupuesto() {
		return tituloPresupuesto;
	}

	public void setTituloPresupuesto(String tituloPresupuesto) {
		this.tituloPresupuesto = tituloPresupuesto;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getTituloProyecto() {
		return tituloProyecto;
	}

	public void setTituloProyecto(String tituloProyecto) {
		this.tituloProyecto = tituloProyecto;
	}

	public int getIdTipoEstadoLineaPresupuesto() {
		return idTipoEstadoLineaPresupuesto;
	}

	public void setIdTipoEstadoLineaPresupuesto(int idTipoEstadoLineaPresupuesto) {
		this.idTipoEstadoLineaPresupuesto = idTipoEstadoLineaPresupuesto;
	}

	public String getNombreTipoEstadoLineaPresupuesto() {
		return nombreTipoEstadoLineaPresupuesto;
	}

	public void setNombreTipoEstadoLineaPresupuesto(String nombreTipoEstadoLineaPresupuesto) {
		this.nombreTipoEstadoLineaPresupuesto = nombreTipoEstadoLineaPresupuesto;
	}


}