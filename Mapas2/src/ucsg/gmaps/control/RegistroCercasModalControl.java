package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.MapUsuarioSistema;
import Util.cercas;
import Util.detallesCercas;

public class RegistroCercasModalControl extends GenericForwardComposer{
	
	
	List<detallesCercas> infoDetCer= new ArrayList<detallesCercas>();
	Textbox nomCer;
	Textbox desCer;
	//String _user;
	Window winMantCercas;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<cercas> cercas= new ArrayList<cercas>();
	private List<cercas> cercasModel= new ArrayList<cercas>();
	AnnotateDataBinder binder; 
		
	
	 public List<cercas> getCercas() {
		return cercas;
	}


	public void setCercas(List<cercas> cercas) {
		this.cercas = cercas;
	}


	public List<cercas> getCercasModel() {
		return cercasModel;
	}


	public void setCercasModel(List<cercas> cercasModel) {
		this.cercasModel = cercasModel;
	}


	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public Window getWinMantCercas() {
		return winMantCercas;
	}


	public void setWinMantCercas(Window winMantCercas) {
		this.winMantCercas = winMantCercas;
	}


	public List<detallesCercas> getInfoDetCer() {
		return infoDetCer;
	}


	public void setInfoDetCer(List<detallesCercas> infoDetCer) {
		this.infoDetCer = infoDetCer;
	}

	public Textbox getNomCer() {
		return nomCer;
	}

	public void setNomCer(Textbox nomCer) {
		this.nomCer = nomCer;
	}

	public Textbox getDesCer() {
		return desCer;
	}

	public void setDesCer(Textbox desCer) {
		this.desCer = desCer;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{

	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("winMantCercas",this,true);
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		infoDetCer  =(List<detallesCercas>)arg.get("pCerca");
	    		getUserFromIndex();
				
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }

	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (objUsuarioSistema==null)
			Executions.sendRedirect("DesconectaUsuario.zul");

	}
	
	public void guardarCercas()
	{
		ResultSet rs;
		ResultSet rsUser;
		
		Statement st;
		String NOMBRE=null;
		String DESCRIPCION=null;
		//int idUsu=0;
		int updateCerca=0;
		int updateDetalleCerca=0;
		int ce_id=0;
		int orden=0;
		
		DatosDao dat = new DatosDao();
		
		//Connection conn= ConexionUtil.getConnection();
	
try{
System.out.println("nomCer.getText(): "+nomCer.getText()+ " desCer.getText(): "+ desCer.getText());	
if( nomCer.getText()==null || desCer.getText()==null || nomCer.getValue().equals("")|| desCer.getValue().equals("") )			
{
	Messagebox.show("Favor ingrese un nombre y una descripcion de la cerca.","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
}
else{
	NOMBRE=nomCer.getValue();
	DESCRIPCION= desCer.getValue();
			//st=conn.createStatement();
			dat.insertaCercaDao(NOMBRE, DESCRIPCION, objUsuarioSistema.getUsu_id());
			cercasModel = dat.fillCercasDao(objUsuarioSistema);
			cercas = cercasModel;
			
			for(int i=0;i<cercas.size();i++)
			{
				if(cercas.get(i).getIdCercas()>ce_id)
					ce_id = cercas.get(i).getIdCercas();
			}

			for(int indexCerca=0;indexCerca<=infoDetCer.size()-1;indexCerca++)
			{
				orden++;
				dat.insertaDetCercaDao(orden,  infoDetCer.get(indexCerca).getLongitud(), infoDetCer.get(indexCerca).getLatitud(), ce_id);				
			}
			
			Messagebox.show("La cerca fue almacenada con exito","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);	
			winMantCercas.detach();
		
}
			
			
		
}catch(Exception e){e.printStackTrace();}
		
	}
	
	public void cerrarVentana(){
		 try{
			 winMantCercas.detach();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	
	

}
