package com.alejandro.reformatec.model;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class UsuarioCriteria extends AbstractValueObject{

	private Integer idEspecializacion;

	private String descripcion;

	private Boolean proveedorVerificado;

	private Integer idProvincia;

	private Boolean servicio24;

	private Boolean expertoNegocio;

	private String orderBy;
	
	private Long idUsuario;

	private Integer idPoblacion;
	
	//busca los favoritos que tiene un usuario
	private Long idUsuarioFavorito;
	
	private String email;
	
	
	
	public UsuarioCriteria() {

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdEspecializacion() {
		return idEspecializacion;
	}

	public void setIdEspecializacion(Integer idEspecializacion) {
		this.idEspecializacion = idEspecializacion;
	}

	public Boolean getProveedorVerificado() {
		return proveedorVerificado;
	}

	public void setProveedorVerificado(Boolean proveedorVerificado) {
		this.proveedorVerificado = proveedorVerificado;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Boolean getServicio24() {
		return servicio24;
	}

	public void setServicio24(Boolean servicio24) {
		this.servicio24 = servicio24;
	}

	public Boolean getExpertoNegocio() {
		return expertoNegocio;
	}

	public void setExpertoNegocio(Boolean expertoNegocio) {
		this.expertoNegocio = expertoNegocio;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(Integer idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public Long getIdUsuarioFavorito() {
		return idUsuarioFavorito;
	}

	public void setIdUsuarioFavorito(Long idUsuarioFavorito) {
		this.idUsuarioFavorito = idUsuarioFavorito;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}