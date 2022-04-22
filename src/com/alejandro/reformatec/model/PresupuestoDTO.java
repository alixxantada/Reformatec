package com.alejandro.reformatec.model;

import java.util.Date;
import java.util.List;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class PresupuestoDTO extends AbstractValueObject{

	private Long idPresupuesto;
	private String titulo;
	private String descripcion;
	private Date fechaHoraCreacion;
	private Double importeTotal;

	private int idTipoEstadoPresupuesto;
	private String nombreTipoEstadoPresupuesto;

	private Long idUsuarioCreadorPresupuesto;
	private String nombrePerfilUsuarioCreadorPresupuesto;

	private Long idProyecto;
	private String tituloProyecto;

	private Long idUsuarioCreadorProyecto;
	private String nombrePerfilUsuarioCreadorProyecto;

	private List<LineaPresupuestoDTO> lineas = null;


	public PresupuestoDTO() {

	}


	
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Date getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}



	public void setFechaHoraCreacion(Date fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}



	public Double getImporteTotal() {
		return importeTotal;
	}



	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}



	public int getIdTipoEstadoPresupuesto() {
		return idTipoEstadoPresupuesto;
	}



	public void setIdTipoEstadoPresupuesto(int idTipoEstadoPresupuesto) {
		this.idTipoEstadoPresupuesto = idTipoEstadoPresupuesto;
	}



	public String getNombreTipoEstadoPresupuesto() {
		return nombreTipoEstadoPresupuesto;
	}



	public void setNombreTipoEstadoPresupuesto(String nombreTipoEstadoPresupuesto) {
		this.nombreTipoEstadoPresupuesto = nombreTipoEstadoPresupuesto;
	}



	public Long getIdUsuarioCreadorPresupuesto() {
		return idUsuarioCreadorPresupuesto;
	}



	public void setIdUsuarioCreadorPresupuesto(Long idUsuarioCreadorPresupuesto) {
		this.idUsuarioCreadorPresupuesto = idUsuarioCreadorPresupuesto;
	}



	public String getNombrePerfilUsuarioCreadorPresupuesto() {
		return nombrePerfilUsuarioCreadorPresupuesto;
	}



	public void setNombrePerfilUsuarioCreadorPresupuesto(String nombrePerfilUsuarioCreadorPresupuesto) {
		this.nombrePerfilUsuarioCreadorPresupuesto = nombrePerfilUsuarioCreadorPresupuesto;
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



	public List<LineaPresupuestoDTO> getLineas() {
		return lineas;
	}



	public void setLineas(List<LineaPresupuestoDTO> lineas) {
		this.lineas = lineas;
	}
}