package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class ProyectoCriteria extends AbstractValueObject{

	// parámetro para filtrar, (lo que introduzca en el buscar si coincide algunas palabras en esta descripcion...)
	private String descripcion;

	// parámetro para filtrar el usuario
	private Integer presupuestoMaxMinimo;

	private Integer presupuestoMaxMaximo;

	// parámetro para filtrar el usuario
	private Integer idEspecializacion;

	// parámetro para filtrar el usuario
	private Integer idProvincia;

	//parametro para ordenar
	private String orderBy;

	private Long idProyecto;
	
	private Long idUsuarioCreador;
	

	public ProyectoCriteria() {

	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getPresupuestoMaxMinimo() {
		return presupuestoMaxMinimo;
	}


	public void setPresupuestoMaxMinimo(Integer presupuestoMaxMinimo) {
		this.presupuestoMaxMinimo = presupuestoMaxMinimo;
	}


	public Integer getPresupuestoMaxMaximo() {
		return presupuestoMaxMaximo;
	}


	public void setPresupuestoMaxMaximo(Integer presupuestoMaxMaximo) {
		this.presupuestoMaxMaximo = presupuestoMaxMaximo;
	}


	public Integer getIdEspecializacion() {
		return idEspecializacion;
	}


	public void setIdEspecializacion(Integer idEspecializacion) {
		this.idEspecializacion = idEspecializacion;
	}


	public Integer getIdProvincia() {
		return idProvincia;
	}


	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public Long getIdProyecto() {
		return idProyecto;
	}


	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}


	public Long getIdUsuarioCreador() {
		return idUsuarioCreador;
	}


	public void setIdUsuarioCreador(Long idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}
}