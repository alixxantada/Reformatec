package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class PoblacionDTO extends AbstractValueObject{

	private Integer idPoblacion;
	private String nombre;

	private Integer idProvincia;
	private String NombreProvincia;

	public PoblacionDTO() {

	}

	public Integer getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(Integer idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreProvincia() {
		return NombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		NombreProvincia = nombreProvincia;
	}

}