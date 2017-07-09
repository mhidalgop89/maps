package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clientes {
	private String nombre;
	private String apellido;
	private String cedula;
	private String mail;
	private String usuario;
	private String password;
	private int per_id;
	private int usu_id;
	private String perfil;
	private String usu_direccion;
	private String usu_telefono;
	private String usu_celular;
	private Date fecha_nacimiento;
	private String fecha_nacimientoSTR;
	private boolean selected;
	String estado;
	

	
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getUsu_direccion() {
		return usu_direccion;
	}
	public void setUsu_direccion(String usu_direccion) {
		this.usu_direccion = usu_direccion;
	}
	public String getUsu_telefono() {
		return usu_telefono;
	}
	public void setUsu_telefono(String usu_telefono) {
		this.usu_telefono = usu_telefono;
	}
	public String getUsu_celular() {
		return usu_celular;
	}
	public void setUsu_celular(String usu_celular) {
		this.usu_celular = usu_celular;
	}
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public int getUsu_id() {
		return usu_id;
	}
	public void setUsu_id(int usu_id) {
		this.usu_id = usu_id;
	}
	public int getPer_id() {
		return per_id;
	}
	public void setPer_id(int per_id) {
		this.per_id = per_id;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void Clientes()
	{
		nombre="sn";
		apellido="sa";
		cedula="sc";
		mail="sm";
		usuario="su";
		password="sp";
		
	}
	
	public void Clientes(String nom,String ape,String ced ,String mai,String usu,String pass)
	{
		nombre=nom;
		apellido=ape;
		cedula=ced;
		mail=mai;
		usuario=usu;
		password=pass;
		
	}
	public void setFecha_nacimientoSTR(String fecha_nacimientoSTR) {
		this.fecha_nacimientoSTR = fecha_nacimientoSTR;
	}
	public String getFecha_nacimientoSTR() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (this.fecha_nacimiento!=null){
		fecha_nacimientoSTR = sdf.format(this.fecha_nacimiento);
		}
		return fecha_nacimientoSTR;
	}
	
	

}
