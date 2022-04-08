package com.alejandro.reformatec.model;


import java.util.Date;
import java.util.List;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class TrabajoRealizadoDTO extends AbstractValueObject{

	private Long idTrabajoRealizado;
	private String titulo;
	private String descripcion;
	private Date fechaCreacion;
	private Long NumVisualizaciones;

	private Long idUsuarioCreadorTrabajo;
	private String nombrePerfilUsuarioCreadorTrabajo;

	private Integer idPoblacion;
	private String nombrePoblacion;

	private Integer idProvincia;
	private String nombreProvincia;

	private int idTipoEstadoTrabajoRealizado;
	private String nombreTipoEstadoTrabajoRealizado;

	private List<Especializacion> especializaciones;
	private List<Integer> idsEspecializaciones;

	private Long idProyectoAsociado;
	private String tituloProyectoAsociado;

	private Integer notaValoracion;

	public TrabajoRealizadoDTO() {

	}

	public Long getIdTrabajoRealizado() {
		return idTrabajoRealizado;
	}

	public void setIdTrabajoRealizado(Long idTrabajoRealizado) {
		this.idTrabajoRealizado = idTrabajoRealizado;
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getNumVisualizaciones() {
		return NumVisualizaciones;
	}

	public void setNumVisualizaciones(Long numVisualizaciones) {
		NumVisualizaciones = numVisualizaciones;
	}

	public Long getIdUsuarioCreadorTrabajo() {
		return idUsuarioCreadorTrabajo;
	}

	public void setIdUsuarioCreadorTrabajo(Long idUsuarioCreadorTrabajo) {
		this.idUsuarioCreadorTrabajo = idUsuarioCreadorTrabajo;
	}

	public String getNombrePerfilUsuarioCreadorTrabajo() {
		return nombrePerfilUsuarioCreadorTrabajo;
	}

	public void setNombrePerfilUsuarioCreadorTrabajo(String nombrePerfilUsuarioCreadorTrabajo) {
		this.nombrePerfilUsuarioCreadorTrabajo = nombrePerfilUsuarioCreadorTrabajo;
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

	public int getIdTipoEstadoTrabajoRealizado() {
		return idTipoEstadoTrabajoRealizado;
	}

	public void setIdTipoEstadoTrabajoRealizado(int idTipoEstadoTrabajoRealizado) {
		this.idTipoEstadoTrabajoRealizado = idTipoEstadoTrabajoRealizado;
	}

	public String getNombreTipoEstadoTrabajoRealizado() {
		return nombreTipoEstadoTrabajoRealizado;
	}

	public void setNombreTipoEstadoTrabajoRealizado(String nombreTipoEstadoTrabajoRealizado) {
		this.nombreTipoEstadoTrabajoRealizado = nombreTipoEstadoTrabajoRealizado;
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

	public Long getIdProyectoAsociado() {
		return idProyectoAsociado;
	}

	public void setIdProyectoAsociado(Long idProyectoAsociado) {
		this.idProyectoAsociado = idProyectoAsociado;
	}

	public String getTituloProyectoAsociado() {
		return tituloProyectoAsociado;
	}

	public void setTituloProyectoAsociado(String tituloProyectoAsociado) {
		this.tituloProyectoAsociado = tituloProyectoAsociado;
	}

	public Integer getNotaValoracion() {
		return notaValoracion;
	}

	public void setNotaValoracion(Integer notaValoracion) {
		this.notaValoracion = notaValoracion;
	}
}