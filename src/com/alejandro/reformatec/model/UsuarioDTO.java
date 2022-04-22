package com.alejandro.reformatec.model;

import java.util.List;

import com.alejandro.reformatec.dao.util.AbstractValueObject;

public class UsuarioDTO extends AbstractValueObject {

	private Long idUsuario;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nif;
	private String telefono1;
	private String telefono2;
	private String email;
	private String nombrePerfil;
	private String nombreCalle;
	private String codigoPostal;
	private String encryptedPassword;
	private String Password;
	private String codigoRegistro;

	private Integer idPoblacion;
	private String nombrePoblacion;

	private Integer idProvincia;
	private String nombreProvincia;

	private int idTipoUsuario;
	private String nombreTipoUsuario;

	private int idTipoEstadoCuenta;
	private String nombreTipoEstadoCuenta;


	// Atributos de Proveedor a continuacion.

	// TODO La lista de especializaciones para el findby y la de integers para el create :S
	private List<Especializacion> especializaciones;
	private List<Integer> idsEspecializaciones;
	
	private Double valoracionMedia;
	private Integer numeroValoraciones;
	
	private String cif;
	private String descripcion;
	private String direccionWeb;
	// importante, debe ser Boolean y no boolean para poder darle null de valor a los de este tipo
	private Boolean servicio24;
	private Long numeroVisualizaciones;
	private Boolean proveedorVerificado;


	public UsuarioDTO() {
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getTelefono1() {
		return telefono1;
	}


	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}


	public String getTelefono2() {
		return telefono2;
	}


	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNombrePerfil() {
		return nombrePerfil;
	}


	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}


	public String getNombreCalle() {
		return nombreCalle;
	}


	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public String getEncryptedPassword() {
		return encryptedPassword;
	}


	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public String getCodigoRegistro() {
		return codigoRegistro;
	}


	public void setCodigoRegistro(String codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
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


	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}


	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}


	public String getNombreTipoUsuario() {
		return nombreTipoUsuario;
	}


	public void setNombreTipoUsuario(String nombreTipoUsuario) {
		this.nombreTipoUsuario = nombreTipoUsuario;
	}


	public int getIdTipoEstadoCuenta() {
		return idTipoEstadoCuenta;
	}


	public void setIdTipoEstadoCuenta(int idTipoEstadoCuenta) {
		this.idTipoEstadoCuenta = idTipoEstadoCuenta;
	}


	public String getNombreTipoEstadoCuenta() {
		return nombreTipoEstadoCuenta;
	}


	public void setNombreTipoEstadoCuenta(String nombreTipoEstadoCuenta) {
		this.nombreTipoEstadoCuenta = nombreTipoEstadoCuenta;
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


	public Double getValoracionMedia() {
		return valoracionMedia;
	}


	public void setValoracionMedia(Double valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}


	public Integer getNumeroValoraciones() {
		return numeroValoraciones;
	}


	public void setNumeroValoraciones(Integer numeroValoraciones) {
		this.numeroValoraciones = numeroValoraciones;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getDireccionWeb() {
		return direccionWeb;
	}


	public void setDireccionWeb(String direccionWeb) {
		this.direccionWeb = direccionWeb;
	}


	public Boolean getServicio24() {
		return servicio24;
	}


	public void setServicio24(Boolean servicio24) {
		this.servicio24 = servicio24;
	}


	public Long getNumeroVisualizaciones() {
		return numeroVisualizaciones;
	}


	public void setNumeroVisualizaciones(Long numeroVisualizaciones) {
		this.numeroVisualizaciones = numeroVisualizaciones;
	}


	public Boolean getProveedorVerificado() {
		return proveedorVerificado;
	}


	public void setProveedorVerificado(Boolean proveedorVerificado) {
		this.proveedorVerificado = proveedorVerificado;
	}

}