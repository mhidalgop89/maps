package Util;

public class Permisos {
	
	private double prmId;
	private String prmNombre;
	private String prmValor;
	boolean selected;
	
	


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	public double getPrmId() {
		return prmId;
	}

	public void setPrmId(double prmId) {
		this.prmId = prmId;
	}

	public String getPrmNombre() {
		return prmNombre;
	}
	
	public void setPrmNombre(String prmNombre) {
		this.prmNombre = prmNombre;
	}
	
	public String getPrmValor() {
		return prmValor;
	}
	
	public void setPrmValor(String prmValor) {
		this.prmValor = prmValor;
	}
	
	
	

}
