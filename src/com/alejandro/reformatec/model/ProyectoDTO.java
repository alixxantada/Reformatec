package com.alejandro.reformatec.model;

import java.util.Date;
import java.util.List;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class ProyectoDTO extends AbstractValueObject{

	private Long idProyecto;
	private String titulo;
	private Date fechaCreacion;
	private String descripcion;
	private Integer presupuestoMax;

	private Integer idPoblacion;
	private String nombrePoblacion;

	private Integer idProvincia;
	private String nombreProvincia;

	private int idTipoEstadoProyecto;
	private String nombreTipoEstadoProyecto;

	// no entiendo como hay que organizar las especializaciones, hago una lista de especializacion entonces me sobra el string de nombre especializacion
	private List<Especializacion> especializaciones;
	private List<Integer> idsEspecializaciones;

	private Long idUsuarioCreadorProyecto;
	private String nombrePerfilUsuarioCreadorProyecto;


	public ProyectoDTO() {
	}


	public Long getIdProyecto() {
		return idProyecto;
	}


	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getPresupuestoMax() {
		return presupuestoMax;
	}


	public void setPresupuestoMax(Integer presupuestoMax) {
		this.presupuestoMax = presupuestoMax;
	}


	public Integer getIdPoblacion() {
		return idPoblacion;
	}


	public void setIdPoblacion(Integer idPoblacion) {
		this.idPoblacion = idPoblacion;
	}


	public String getNombrePoblacion() {
		return nombrePoblacion;
	}


	public void setNombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
	}


	public Integer getIdProvincia() {
		return idProvincia;
	}


	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}


	public String getNombreProvincia() {
		return nombreProvincia;
	}


	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}


	public int getIdTipoEstadoProyecto() {
		return idTipoEstadoProyecto;
	}


	public void setIdTipoEstadoProyecto(int idTipoEstadoProyecto) {
		this.idTipoEstadoProyecto = idTipoEstadoProyecto;
	}


	public String getNombreTipoEstadoProyecto() {
		return nombreTipoEstadoProyecto;
	}


	public void setNombreTipoEstadoProyecto(String nombreTipoEstadoProyecto) {
		this.nombreTipoEstadoProyecto = nombreTipoEstadoProyecto;
	}


	public List<Especializacion> getEspecializaciones() {
		return especializaciones;
	}


	public void setEspecializaciones(List<Especializacion> especializaciones) {
		this.especializaciones = especializaciones;
	}


	public List<Integer> getIdsEspecializaciones() {
		return idsEspecializaciones;
	}


	public void setIdsEspecializaciones(List<Integer> idsEspecializaciones) {
		this.idsEspecializaciones = idsEspecializaciones;
	}


	public Long getIdUsuarioCreadorProyecto() {
		return idUsuarioCreadorProyecto;
	}


	public void setIdUsuarioCreadorProyecto(Long idUsuarioCreadorProyecto) {
		this.idUsuarioCreadorProyecto = idUsuarioCreadorProyecto;
	}


	public String getNombrePerfilUsuarioCreadorProyecto() {
		return nombrePerfilUsuarioCreadorProyecto;
	}


	public void setNombrePerfilUsuarioCreadorProyecto(String nombrePerfilUsuarioCreadorProyecto) {
		this.nombrePerfilUsuarioCreadorProyecto = nombrePerfilUsuarioCreadorProyecto;
	}
}