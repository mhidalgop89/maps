package Util;

public class Marks {

	double ltd;
	double lng;
	String image;
	int id_vehiculo;
	String nombreVehiculo;
	int coordId;
	
	
	
	public int getCoordId() {
		return coordId;
	}

	public void setCoordId(int coordId) {
		this.coordId = coordId;
	}

	public int getId_vehiculo() {
		return id_vehiculo;
	}

	public void setId_vehiculo(int id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}

	public String getNombreVehiculo() {
		return nombreVehiculo;
	}

	public void setNombreVehiculo(String nombreVehiculo) {
		this.nombreVehiculo = nombreVehiculo;
	}

	public double getLtd() {
		return ltd;
	}

	public void setLtd(double ltd) {
		this.ltd = ltd;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	

	public void Marks()
	{
		ltd=-2;
		lng=-79.80;
		id_vehiculo=0;
		image="default";
		nombreVehiculo="Vehiculo sin Nombre";
		coordId=10;
		
	}
	
	public void Marks(double ltd, double lng, String img, int id_veh, String nombreVehiculo1, int coordenadas)
	{
		ltd=ltd;
		lng=lng;
		id_vehiculo=id_veh;
		image=img;
		nombreVehiculo=nombreVehiculo1;
		coordId=coordenadas;
	}
	
}
