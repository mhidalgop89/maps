package ucsg.gmaps.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolyline;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.detallesCercas;

public class MapaGoogle4Control extends GenericForwardComposer{
	Gmaps mymap;
	Gmaps mymap2;
	Gmarker mymark;
	Gpolyline mypoly ;
	Gpolyline mypoly2 ;
	Label lbl;
	Window gmap4;
	private Button btnGuardar;
	private Button btnFinalizar;
	private Button btnLimpiar;
	private Button btnVolver;
	
	//String _user;
	MapUsuarioSistema objUsuarioSistema = null;
	
	List<detallesCercas> cerca= new ArrayList<detallesCercas>();
	
	
	AnnotateDataBinder binder;
	
	
	public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("gmap4",this,true);
    		//Ejecución de Ajax
    		btnGuardar.setDisabled(true);
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		cargarComponentes();
    		
    		binder.loadAll();
    
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
	
	public void cargarComponentes()
	{
		btnFinalizar.setLabel( ReadPropertiesUtil.obtenerProperty("control.MapaGoogle4.Finalizar", objUsuarioSistema.getIdioma()));
		btnGuardar.setLabel( ReadPropertiesUtil.obtenerProperty("control.MapaGoogle4.Guardar", objUsuarioSistema.getIdioma()));
		btnLimpiar.setLabel( ReadPropertiesUtil.obtenerProperty("control.MapaGoogle4.Limpiar", objUsuarioSistema.getIdioma()));
		btnVolver.setLabel( ReadPropertiesUtil.obtenerProperty("control.MapaGoogle4.Volver", objUsuarioSistema.getIdioma()));
		
	}
	
	
	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
//		if (objUsuarioSistema==null)
//			Executions.sendRedirect("DesconectaUsuario.zul");
		
	}
	
	public void desconectar()
	{
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.sendRedirect("index.zul");	
	}
	
	public void trazar()
	{
	
	double lat = mymap.getLat();
    double lng = mymap.getLng();
   
    
    
    new Gmarker("", lat, lng).setParent(mymap);
		
    mypoly.addPoint(lat, lng, 3); 
    System.out.println(lbl.getValue() + lat + ","+lng+";\n" );
           lbl.setValue( lbl.getValue() + lat + ","+lng+";\n" );
           
		
	}
	
	public void limpiar()
	{	
		Executions.sendRedirect("MapaGoogle4.zul");
	}
	
	
	public void finCerca()
	{try{
		if(cerca.size()>=3)
		{mypoly.addPoint(cerca.get(0).getLatitud(), cerca.get(0).getLongitud(), 3);
		btnGuardar.setDisabled(false);
		//mymap.removeAttribute("onMapDoubleClick");
		rutaMapa();
		//mymap.getChildren().clear();
		}
		else
			Messagebox.show("Debe de ingresar minimo 3 puntos en el mapa","Atencion",Messagebox.OK,Messagebox.INFORMATION);
	}catch(Exception e){e.printStackTrace();}
	
	
	
	}
	
	
	
	public void cargarDataModal(){
    	try{
       		//lista doblemente enlazada
    		Map<String,List<detallesCercas>> maCategoria = new HashMap<String,List<detallesCercas>>();
    		maCategoria.put("pCerca", cerca);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroCercas.zul",null, maCategoria);
    		
    		Executions.getCurrent().getSession().setAttribute("_userRegistroCercasModalControl",objUsuarioSistema.getUser());
    		
    		registro.doModal();
    		
    		/*objCategoria=null;
    		getCategoria();
    		binder.loadComponent(listuser);*/
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	public void rutaMapa()
	{
		mymap.setVisible(false);
		mymap2.setVisible(true);
		//gmap4.appendChild(mymap2);
			int sizeCerca = cerca.size();
			boolean primerR=true;
			double lng=0;
			double ltd=0;
			
			
			try{
				
				
				System.out.println("fin cerca: list");
				for(int i=0;i<sizeCerca;i++)
				{
					System.out.println("fin cerca: list2");
					
					if(primerR)
					{
						lng= cerca.get(i).getLongitud();
						ltd= cerca.get(i).getLatitud();
						mymap2.panTo(ltd, lng);
						//mymap2.setZoom(11);
						System.out.println("mymap2.setZoom(mymap2.getZoom()): "+mymap2.getZoom()+"<--------this is it!!");
						primerR=false;
					}
					mypoly2.addPoint(cerca.get(i).getLatitud(), cerca.get(i).getLongitud(), 3);

				}
				
				mypoly2.addPoint(ltd, lng, 3);
				btnGuardar.setDisabled(false);
				
				
			}catch(Exception e ){e.printStackTrace();}
			
		
		
	}
	public void volver()
	{
		
		Executions.sendRedirect("principal.zul");
		
	}
	
	public void guardar()
	{
		
		ResultSet rs; 
		Statement st;
		Connection conn= ConexionUtil.getConnection();
		int updateCerca=0;
		
		cargarDataModal();

	}
	
	public void llenaCoordenadas(double lat, double lng)
	{
		
		System.out.println("ejecuta: - "+lat+"--"+lng);		
		detallesCercas dc = new detallesCercas();
		
		dc.setLatitud(lat);
		dc.setLongitud(lng);
		cerca.add(dc);

	}
	
	

	public Button getBtnFinalizar() {
		return btnFinalizar;
	}

	public void setBtnFinalizar(Button btnFinalizar) {
		this.btnFinalizar = btnFinalizar;
	}

	public Button getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(Button btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public Button getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(Button btnVolver) {
		this.btnVolver = btnVolver;
	}

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Gmaps getMymap2() {
		return mymap2;
	}

	public void setMymap2(Gmaps mymap2) {
		this.mymap2 = mymap2;
	}

	public Gpolyline getMypoly2() {
		return mypoly2;
	}

	public void setMypoly2(Gpolyline mypoly2) {
		this.mypoly2 = mypoly2;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public List<detallesCercas> getCerca() {
		return cerca;
	}

	public void setCerca(List<detallesCercas> cerca) {
		this.cerca = cerca;
	}


	public Window getGmap4() {
		return gmap4;
	}

	public void setGmap4(Window gmap4) {
		this.gmap4 = gmap4;
	}

	public Label getLbl() {
		return lbl;
	}

	public void setLbl(Label lbl) {
		this.lbl = lbl;
	}

	public Gpolyline getMypoly() {
		return mypoly;
	}

	public void setMypoly(Gpolyline mypoly) {
		this.mypoly = mypoly;
	}

	public Gmaps getMymap() {
		return mymap;
	}

	public void setMymap(Gmaps mymap) {
		this.mymap = mymap;
	}

	public Gmarker getMymark() {
		return mymark;
	}

	public void setMymark(Gmarker mymark) {
		this.mymark = mymark;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}



	

}

