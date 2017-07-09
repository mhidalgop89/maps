package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import Util.Clientes;
import Util.MapUsuarioSistema;
import Util.Vehiculos;

public class RegistroVehiculoUsuarioControl extends GenericForwardComposer {
	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<Vehiculos> vehUsu= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	Listbox listveh;
	Window winvehusu;
	private Clientes objUsuario = new Clientes();
	Textbox txtFilNomVeh;
	
public Window getWinvehusu() {
		return winvehusu;
	}
	public void setWinvehusu(Window winvehusu) {
		this.winvehusu = winvehusu;
	}
	public Clientes getObjUsuario() {
		return objUsuario;
	}
	public void setObjUsuario(Clientes objUsuario) {
		this.objUsuario = objUsuario;
	}
public Textbox getTxtFilNomVeh() {
		return txtFilNomVeh;
	}
	public void setTxtFilNomVeh(Textbox txtFilNomVeh) {
		this.txtFilNomVeh = txtFilNomVeh;
	}
public Listbox getListveh() {
		return listveh;
	}
	public void setListveh(Listbox listveh) {
		this.listveh = listveh;
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
	public List<Vehiculos> getVehModel() {
		return vehModel;
	}
	public void setVehModel(List<Vehiculos> vehModel) {
		this.vehModel = vehModel;
	}
public List<Vehiculos> getVehUsu() {
		return vehUsu;
	}
	public void setVehUsu(List<Vehiculos> vehUsu) {
		this.vehUsu = vehUsu;
	}
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winvehusu",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objUsuario  =(Clientes)arg.get("pClientes");
    		getUserFromIndex();
    		obtenerVehiculo();
    		binder.loadAll();

    		
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

public void obtenerVehiculo()
{		
	DatosDao dat = new DatosDao();
	int per_id=0;
	int usu_id=0;

	vehUsu=  new ArrayList<Vehiculos>();
	vehModel=  new ArrayList<Vehiculos>();
	
	try{
		
		per_id = objUsuarioSistema.getPer_id(); 
		usu_id = objUsuarioSistema.getUsu_id();
		
		vehModel = dat.obtieneVehiculoDao(usu_id);
		for (int i=0;i<vehModel.size();i++)
			if(vehModel.get(i).getIdUsu()== objUsuario.getUsu_id())
				vehUsu.add(vehModel.get(i));
		
			
		System.out.println("objUsuarioSistema.getUsu_id(): "+objUsuarioSistema.getUsu_id()+" vehUsu.size(): "+ vehUsu.size()+" objUsuario.getUsu_id(): "+objUsuario.getUsu_id());
		binder.loadComponent(listveh);

	}
	catch(Exception e){e.printStackTrace();}
	
}

public void cerrarVentana()
{
	winvehusu.detach();
}

public void filtraVehiculo()
{
	
	if(!txtFilNomVeh.equals(""))
	{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		vehUsu = new ArrayList<Vehiculos>();
		
		for(int i=0; i< vehModel.size();i++)
		{
			if(		vehModel.get(i).getNombre().toLowerCase().contains(txtFilNomVeh.getText().toLowerCase()))	
				vehUsu.add(vehModel.get(i));
		}
		
		binder.loadComponent(listveh);
		
	}
}
	
}
