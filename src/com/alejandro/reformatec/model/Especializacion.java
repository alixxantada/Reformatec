package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class Especializacion  extends AbstractValueObject{

	private Integer idEspecializacion;
	private String nombre;


	public Especializacion() {

	}


	public Integer getIdEspecializacion() {
		return idEspecializacion;
	}


	public void setIdEspecializacion(Integer idEspecializacion) {
		this.idEspecializacion = idEspecializacion;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}