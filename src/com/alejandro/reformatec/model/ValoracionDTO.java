package com.alejandro.reformatec.model;

import java.util.Date;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class ValoracionDTO extends AbstractValueObject{

	private Long idValoracion;
	private String titulo;
	private String Descripcion;
	private Integer notaValoracion;
	private Date fechaHoraCreacion;

	private Long idUsuarioValora;
	private String nombrePerfilUsuarioValora;

	private Long idProveedorValorado;
	private String nombrePerfilProveedorValorado;

	private Long idTrabajoRealizadoValorado;
	private String tituloTrabajoRealizadoValorado;

	private int idTipoEstadoValoracion;
	private String nombreTipoEstadoValoracion;

	public ValoracionDTO() {

	}

	public Long getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(Long idValoracion) {
		this.idValoracion = idValoracion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Integer getNotaValoracion() {
		return notaValoracion;
	}

	public void setNotaValoracion(Integer notaValoracion) {
		this.notaValoracion = notaValoracion;
	}

	public Date getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}

	public void setFechaHoraCreacion(Date fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}

	public Long getIdUsuarioValora() {
		return idUsuarioValora;
	}

	public void setIdUsuarioValora(Long idUsuarioValora) {
		this.idUsuarioValora = idUsuarioValora;
	}

	public String getNombrePerfilUsuarioValora() {
		return nombrePerfilUsuarioValora;
	}

	public void setNombrePerfilUsuarioValora(String nombrePerfilUsuarioValora) {
		this.nombrePerfilUsuarioValora = nombrePerfilUsuarioValora;
	}

	public Long getIdProveedorValorado() {
		return idProveedorValorado;
	}

	public void setIdProveedorValorado(Long idProveedorValorado) {
		this.idProveedorValorado = idProveedorValorado;
	}

	public String getNombrePerfilProveedorValorado() {
		return nombrePerfilProveedorValorado;
	}

	public void setNombrePerfilProveedorValorado(String nombrePerfilProveedorValorado) {
		this.nombrePerfilProveedorValorado = nombrePerfilProveedorValorado;
	}

	public Long getIdTrabajoRealizadoValorado() {
		return idTrabajoRealizadoValorado;
	}

	public void setIdTrabajoRealizadoValorado(Long idTrabajoRealizadoValorado) {
		this.idTrabajoRealizadoValorado = idTrabajoRealizadoValorado;
	}

	public String getTituloTrabajoRealizadoValorado() {
		return tituloTrabajoRealizadoValorado;
	}

	public void setTituloTrabajoRealizadoValorado(String tituloTrabajoRealizadoValorado) {
		this.tituloTrabajoRealizadoValorado = tituloTrabajoRealizadoValorado;
	}

	public int getIdTipoEstadoValoracion() {
		return idTipoEstadoValoracion;
	}

	public void setIdTipoEstadoValoracion(int idTipoEstadoValoracion) {
		this.idTipoEstadoValoracion = idTipoEstadoValoracion;
	}

	public String getNombreTipoEstadoValoracion() {
		return nombreTipoEstadoValoracion;
	}

	public void setNombreTipoEstadoValoracion(String nombreTipoEstadoValoracion) {
		this.nombreTipoEstadoValoracion = nombreTipoEstadoValoracion;
	}
}