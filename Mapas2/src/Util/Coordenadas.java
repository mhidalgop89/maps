package Util;

import java.util.Date;

public class Coordenadas {

	double latitud;
	double longitud;
	double altitud;
	Date fecha;
	int idVehiculo;
	String Vehiculo;
	String Comando;
	int idCoorenada;
	double velocidad;
	double co_recorrido;
	
	
	public double getCo_recorrido() {
		return co_recorrido;
	}
	public void setCo_recorrido(double co_recorrido) {
		this.co_recorrido = co_recorrido;
	}
	public double getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
	public int getIdCoorenada() {
		return idCoorenada;
	}
	public void setIdCoorenada(int idCoorenada) {
		this.idCoorenada = idCoorenada;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public double getAltitud() {
		return altitud;
	}
	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getIdVehiculo() {
		return idVehiculo;
	}
	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	public String getVehiculo() {
		return Vehiculo;
	}
	public void setVehiculo(String vehiculo) {
		Vehiculo = vehiculo;
	}
	public String getComando() {
		return Comando;
	}
	public void setComando(String comando) {
		Comando = comando;
	}
	
	
	
}
