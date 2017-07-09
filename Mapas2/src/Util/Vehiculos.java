package Util;

import java.sql.Date;



public class Vehiculos {
	
	String nombre;
	String usuario;
	String categoria;
	int catVeh;
	int idUsu;
	int vehId;
	String imei;
	double recorrido;
	double recorridoInicial;
	String mantenimiento;
	String cercaNombre;
	int idCerca;
	String Placa;
	int gr_id;
	String grupo;
	String ve_anio; 
	String ve_alias;
	String ve_notas;
	private String estado;
	private double variacion;
	
	double ve_horas_encendido;
	Date fecha;
	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getVariacion() {
		return variacion;
	}
	public void setVariacion(double variacion) {
		this.variacion = variacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public double getRecorridoInicial() {
		return recorridoInicial;
	}
	public void setRecorridoInicial(double recorridoInicial) {
		this.recorridoInicial = recorridoInicial;
	}
	public String getVe_anio() {
		return ve_anio;
	}
	public void setVe_anio(String ve_anio) {
		this.ve_anio = ve_anio;
	}
	public String getVe_alias() {
		return ve_alias;
	}
	public void setVe_alias(String ve_alias) {
		this.ve_alias = ve_alias;
	}
	public String getVe_notas() {
		return ve_notas;
	}
	public void setVe_notas(String ve_notas) {
		this.ve_notas = ve_notas;
	}
	public int getGr_id() {
		return gr_id;
	}
	public void setGr_id(int gr_id) {
		this.gr_id = gr_id;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}
	public double getVe_horas_encendido() {
		return ve_horas_encendido;
	}
	public void setVe_horas_encendido(double ve_horas_encendido) {
		this.ve_horas_encendido = ve_horas_encendido;
	}
	public int getIdCerca() {
		return idCerca;
	}
	public void setIdCerca(int idCerca) {
		this.idCerca = idCerca;
	}
	public String getCercaNombre() {
		return cercaNombre;
	}
	public void setCercaNombre(String cercaNombre) {
		this.cercaNombre = cercaNombre;
	}

	public String getMantenimiento() {
		return mantenimiento;
	}
	public void setMantenimiento(String mantenimiento) {
		this.mantenimiento = mantenimiento;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public double getRecorrido() {
		return recorrido;
	}
	public void setRecorrido(double recorrido) {
		this.recorrido = recorrido;
	}
	public int getVehId() {
		return vehId;
	}
	public void setVehId(int vehId) {
		this.vehId = vehId;
	}
	public int getCatVeh() {
		return catVeh;
	}
	public void setCatVeh(int catVeh) {
		this.catVeh = catVeh;
	}
	public int getIdUsu() {
		return idUsu;
	}
	public void setIdUsu(int idUsu) {
		this.idUsu = idUsu;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
public void Vehiculos()
{
	 nombre="s/n";
	usuario="s/u";
	categoria="s/c";
	catVeh=0;
	idUsu=0;
	vehId=0;	
}


}
