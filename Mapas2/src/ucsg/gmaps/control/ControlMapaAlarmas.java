package ucsg.gmaps.control;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Util.Coordenadas;
import Util.cercas;

public class ControlMapaAlarmas extends GenericForwardComposer{
	private Gmaps mymap;
	private Coordenadas objAlarmas;
	private Window winMantAlarmas;
	private Gmarker mymark2;
	AnnotateDataBinder binder;
	
	public Window getWinMantAlarmas() {
		return winMantAlarmas;
	}

	public void setWinMantAlarmas(Window winMantAlarmas) {
		this.winMantAlarmas = winMantAlarmas;
	}

	public Gmarker getMymark2() {
		return mymark2;
	}

	public void setMymark2(Gmarker mymark2) {
		this.mymark2 = mymark2;
	}

	public Coordenadas getObjAlarmas() {
		return objAlarmas;
	}

	public void setObjAlarmas(Coordenadas objAlarmas) {
		this.objAlarmas = objAlarmas;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Gmaps getMymap() {
		return mymap;
	}

	public void setMymap(Gmaps mymap) {
		this.mymap = mymap;
	}
	
	
	public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winMantAlarmas",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objAlarmas=(Coordenadas)arg.get("pCoordenadas");
    		if(objAlarmas==null)
    		{
    			Messagebox.show("No Existen registros","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    		}
    		
    		cargaMapa();
    		binder.loadAll();
    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

	
	public void cargaMapa()
	{double lng=0;
	double ltd=0;
	lng = objAlarmas.getLongitud();
	ltd = objAlarmas.getLatitud();
	myMark(ltd, lng);
	Messagebox.show("Ubcación generada con exito!!","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);
		
	}
	public void myMark( double ltd, double lng)
	{
		System.out.println("myMark(ltd,lng): "+"ltd: "+ltd+" lng: "+ lng);
		mymark2.setLat(ltd);
		mymark2.setLng(lng);
		mymark2.setOpen(true);
		mymark2.setContent("Ubicacion");
		mymap.panTo(ltd, lng);
		mymap.appendChild(mymark2);
		
		binder.loadAll();
		
	}
	
	public void cerrar()
	{
		winMantAlarmas.detach();
	}

}
