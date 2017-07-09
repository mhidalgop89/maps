package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Util.MapUsuarioSistema;
import Util.detallesCercas;

public class ServicioRutasControl extends GenericForwardComposer{
	
	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	List<detallesCercas> cerca= new ArrayList<detallesCercas>();
	private Gmaps mymap;
	private Window winRutas;
	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winuser",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
//    		cargarComponentes();
    		mymap = new Gmaps();
    		binder.loadAll();
    		mymap.invalidate();
    		winRutas.invalidate();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
	

public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");	
}
	
	
	public void llenaCoordenadas(double lat, double lng)
	{	
		System.out.println("ejecuta: - "+lat+"--"+lng);		
		detallesCercas dc = new detallesCercas();
		
		dc.setLatitud(lat);
		dc.setLongitud(lng);
		cerca.add(dc);
	}
	
	public void llamaRuta(){
		
		if(cerca.size()<2)
		{
			Messagebox.show("Se debe escoger 2 puntos sobre el mapa",
					"Atención!!!", Messagebox.YES,
					Messagebox.INFORMATION);
			return;
			
		}
		//https://www.google.com.ec/maps/dir/-2.2038822,-79.8906577/-2.1939841,-79.8810795/@-2.1992952,-79.8899155,16z
		String url="https://www.google.com.ec/maps/dir/"+cerca.get(0).getLatitud()+","+cerca.get(0).getLongitud()+"/"
		+cerca.get(1).getLatitud()+","+cerca.get(1).getLongitud();
		
		Executions.getCurrent().sendRedirect(url, "blank");
		
	}
	
	public void limpiar()
	{	
		Executions.sendRedirect("ServicioRutas.zul");
		
	}
	
	public void desconectar()
	{
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.sendRedirect("index.zul");
		
	}


	public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public List<detallesCercas> getCerca() {
		return cerca;
	}


	public void setCerca(List<detallesCercas> cerca) {
		this.cerca = cerca;
	}


	public Gmaps getMymap() {
		return mymap;
	}


	public void setMymap(Gmaps mymap) {
		this.mymap = mymap;
	}


	public Window getWinRutas() {
		return winRutas;
	}


	public void setWinRutas(Window winRutas) {
		this.winRutas = winRutas;
	}

}
