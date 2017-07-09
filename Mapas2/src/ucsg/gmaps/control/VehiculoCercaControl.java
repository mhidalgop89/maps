package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import Util.CategoriaVehiculo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;
import Util.cercas;

public class VehiculoCercaControl  extends GenericForwardComposer{
	cercas objCerca= new cercas();
	private List<Vehiculos> vehUsu= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	private Caption cptVehiculos; 
	private Listheader lshNombreVehiculo;
	private Button btnSalir;
	MapUsuarioSistema objUsuarioSistema = null;
	Window winvehcer;
	Listbox listveh;
	AnnotateDataBinder binder;
	




public void doAfterCompose(Component cmp){  	
    	
    	try
    	{

    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winvehusu",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objCerca=(cercas)arg.get("pCerca");
    		getUserFromIndex();
    		
    		getUserFromIndex();    
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		cargarComponentes();
    		obtenerVehiculo();
    		binder.loadAll();

    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }



public void cargarComponentes()
{
	cptVehiculos.setLabel( ReadPropertiesUtil.obtenerProperty("control.VehiculoCercaControl.Vehiculos", objUsuarioSistema.getIdioma()));
	lshNombreVehiculo.setLabel( ReadPropertiesUtil.obtenerProperty("control.VehiculoCercaControl.NombreVehiculo", objUsuarioSistema.getIdioma()));
	btnSalir.setLabel( ReadPropertiesUtil.obtenerProperty("control.VehiculoCercaControl.Salir", objUsuarioSistema.getIdioma()));
	

}

public void desconectar()
{
	Executions.getCurrent().getSession().removeAttribute("usuario");
	Executions.sendRedirect("index.zul");	
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
			if(vehModel.get(i).getIdCerca()== objCerca.getIdCercas())
				vehUsu.add(vehModel.get(i));
		
			
		System.out.println("objUsuarioSistema.getUsu_id(): "+objUsuarioSistema.getUsu_id()+" vehUsu.size(): "+ vehUsu.size());
		binder.loadComponent(listveh);

	}
	catch(Exception e){e.printStackTrace();}
	
}


public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
}

public void cerrarVentana()
{
	winvehcer.detach();
}

public Listbox getListveh() {
	return listveh;
}
public void setListveh(Listbox listveh) {
	this.listveh = listveh;
}
public cercas getObjCerca() {
	return objCerca;
}
public void setObjCerca(cercas objCerca) {
	this.objCerca = objCerca;
}
public List<Vehiculos> getVehUsu() {
	return vehUsu;
}
public void setVehUsu(List<Vehiculos> vehUsu) {
	this.vehUsu = vehUsu;
}
public List<Vehiculos> getVehModel() {
	return vehModel;
}
public void setVehModel(List<Vehiculos> vehModel) {
	this.vehModel = vehModel;
}
public MapUsuarioSistema getObjUsuarioSistema() {
	return objUsuarioSistema;
}
public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
	this.objUsuarioSistema = objUsuarioSistema;
}
public Window getWinvehcer() {
	return winvehcer;
}
public void setWinvehcer(Window winvehcer) {
	this.winvehcer = winvehcer;
}
public AnnotateDataBinder getBinder() {
	return binder;
}
public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}

public Caption getCptVehiculos() {
	return cptVehiculos;
}
public void setCptVehiculos(Caption cptVehiculos) {
	this.cptVehiculos = cptVehiculos;
}




public Listheader getLshNombreVehiculo() {
	return lshNombreVehiculo;
}



public void setLshNombreVehiculo(Listheader lshNombreVehiculo) {
	this.lshNombreVehiculo = lshNombreVehiculo;
}



public Button getBtnSalir() {
	return btnSalir;
}



public void setBtnSalir(Button btnSalir) {
	this.btnSalir = btnSalir;
}


	
}
