package com.alejandro.reformatec.model;

/**
 *  
 * @author Alejandro
 *
 */
public class ValoracionCriteria {

	//TODO Los campos tienen que venir todos a null menos 1 en cualquier caso... query incompatible entre si ¿En principio ningun problema?
	private Long idValoracion;
	
	private Long idProveedorValorado;
	
	private Long idTrabajoRealizadoValorado;

	//Si viene relleno saco los trabajos que valoró un usuario
	private Long idUsuarioValoraTrabajo;
	
	//Si viene relleno saco los proveedores que valoró un usuario
	private Long idUsuarioValoraProveedor;
	
	
	public Long getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(Long idValoracion) {
		this.idValoracion = idValoracion;
	}

	public Long getIdProveedorValorado() {
		return idProveedorValorado;
	}

	public void setIdProveedorValorado(Long idProveedorValorado) {
		this.idProveedorValorado = idProveedorValorado;
	}

	public Long getIdTrabajoRealizadoValorado() {
		return idTrabajoRealizadoValorado;
	}

	public void setIdTrabajoRealizadoValorado(Long idTrabajoRealizadoValorado) {
		this.idTrabajoRealizadoValorado = idTrabajoRealizadoValorado;
	}

	public Long getIdUsuarioValoraTrabajo() {
		return idUsuarioValoraTrabajo;
	}

	public void setIdUsuarioValoraTrabajo(Long idUsuarioValoraTrabajo) {
		this.idUsuarioValoraTrabajo = idUsuarioValoraTrabajo;
	}

	public Long getIdUsuarioValoraProveedor() {
		return idUsuarioValoraProveedor;
	}

	public void setIdUsuarioValoraProveedor(Long idUsuarioValoraProveedor) {
		this.idUsuarioValoraProveedor = idUsuarioValoraProveedor;
	}
	
}
