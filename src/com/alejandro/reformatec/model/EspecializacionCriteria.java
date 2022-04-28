package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class EspecializacionCriteria extends AbstractValueObject {

	private Integer idEspecializacion;
	
	private Long idProyecto;
	
	private Long idUsuario;
	
	private Long idTrabajoRealizado;
	
	private Long idUsuarioSin;

	
	public EspecializacionCriteria() {

	}
	
	public Integer getIdEspecializacion() {
		return idEspecializacion;
	}

	public void setIdEspecializacion(Integer idEspecializacion) {
		this.idEspecializacion = idEspecializacion;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdTrabajoRealizado() {
		return idTrabajoRealizado;
	}

	public void setIdTrabajoRealizado(Long idTrabajoRealizado) {
		this.idTrabajoRealizado = idTrabajoRealizado;
	}

	public Long getIdUsuarioSin() {
		return idUsuarioSin;
	}

	public void setIdUsuarioSin(Long idUsuarioSin) {
		this.idUsuarioSin = idUsuarioSin;
	}	
}