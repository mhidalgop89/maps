package Util;

import java.util.Date;

public class CategoriaVehiculo {

	int id_categoria;
	String descripcion;
	String identificador;
	String nomVeh;
	int idUser;
	int idVeh;
	String user;
	String imaVeh;
	Date fechaMax;
	String imgAct;
	String estado;
	
	
	
	
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getImgAct() {
		return imgAct;
	}


	public void setImgAct(String imgAct) {
		this.imgAct = imgAct;
	}


	public Date getFechaMax() {
		return fechaMax;
	}


	public void setFechaMax(Date fechaMax) {
		this.fechaMax = fechaMax;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getImaVeh() {
		return imaVeh;
	}


	public void setImaVeh(String imaVeh) {
		this.imaVeh = imaVeh;
	}


	public int getIdVeh() {
		return idVeh;
	}


	public void setIdVeh(int idVeh) {
		this.idVeh = idVeh;
	}


	public String getNomVeh() {
		return nomVeh;
	}


	public void setNomVeh(String nomVeh) {
		this.nomVeh = nomVeh;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	public int getId_categoria() {
		return id_categoria;
	}


	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public void CategoriaVehiculo()
	{
		id_categoria=0;
		descripcion="S/CAT";
		identificador="SIN";
		
	}
	
	
	public void CategoriaVehiculo(int id_cat, String desc, String iden)
	{
	id_categoria=id_cat;
	descripcion=desc;
	identificador=iden;
		
		
	}
	
	
	
	
	
	
}
