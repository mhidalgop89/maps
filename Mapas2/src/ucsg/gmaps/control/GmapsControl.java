package ucsg.gmaps.control;

//import org.hibernate.Session;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Textbox;

//import com.bo.UsuarioBo;
//import com.session.HibernateSessionFactory;

public class GmapsControl extends GenericForwardComposer{
	Textbox lat;
	Gmaps mymap= new Gmaps();
	
	
	AnnotateDataBinder binder;
	
	
	
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


	public Textbox getLat() {
		return lat;
	}


	public void setLat(Textbox lat) {
		this.lat = lat;
	}

	 public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{
	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("gmap",this,true);
	    		controlMap();
	    		//Session sess = HibernateSessionFactory.getSession();
	    		//UsuarioBo bo = new UsuarioBo();
	    		//us = bo.obtenerUsuario(sess);    
	    		//Ejecución de Ajax
	    		//binder = new AnnotateDataBinder(cmp);
	    		//binder.loadComponent(listuser);
	    		//binder.loadAll();
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	
	 
	 public void onTimer() 
	 {	System.out.println("funk on timer");
		 binder= new AnnotateDataBinder();
		 mymap = new Gmaps();
		 binder.loadComponent(mymap);
		 
	 // Inside this method you can query your database for updates and then update the screen to show 
	 // The new values - since you are in the ZK event loop in here you can update screen components. 
	 } 
	 
	
	public void controlMap()
	{
		System.out.println("llega ");
		mymap.setLat(-2);
		mymap.setLng(-70);
		mymap.setZoom(5);
		//mymap.panTo(-2.12, -79.80);
		
		
		
	}
	

}
