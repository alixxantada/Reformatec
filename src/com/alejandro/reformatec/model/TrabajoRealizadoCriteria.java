package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class TrabajoRealizadoCriteria extends AbstractValueObject {

	private Long idProveedor;
	
	private Long idTrabajoRealizado;
	
	private String descripcion;
	
	private Integer idProvincia;
	
	private Integer idEspecializacion;
	
	private String orderBy;


	public TrabajoRealizadoCriteria() {

	}


	public Long getIdProveedor() {
		return this.idProveedor;
	}
	
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	
	
	public Long getIdTrabajoRealizado() {
		return idTrabajoRealizado;
	}


	public void setIdTrabajoRealizado(Long idTrabajoRealizado) {
		this.idTrabajoRealizado = idTrabajoRealizado;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getIdProvincia() {
		return idProvincia;
	}


	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}


	public Integer getIdEspecializacion() {
		return idEspecializacion;
	}


	public void setIdEspecializacion(Integer idEspecializacion) {
		this.idEspecializacion = idEspecializacion;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}