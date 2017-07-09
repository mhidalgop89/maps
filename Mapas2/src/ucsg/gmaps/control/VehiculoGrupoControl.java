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
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;
import Util.cercas;

public class VehiculoGrupoControl  extends GenericForwardComposer{
	private List<Vehiculos> vehUsu= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	MapUsuarioSistema objUsuarioSistema = null;
	Listbox listveh;
	Window winvehgru;
	//componentes
	private Caption cptVehiculos;
	private Listheader lshNombreVehiculo;
	private Button btnSalir;
	
	private Grupo objGrupo=new Grupo();
	AnnotateDataBinder binder;
	

	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{

    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winvehusu",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objGrupo=(Grupo)arg.get("pGrupo");

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
			if(vehModel.get(i).getGr_id()== objGrupo.getGr_Id())
				vehUsu.add(vehModel.get(i));
		
			
		System.out.println("objUsuarioSistema.getUsu_id(): "+objUsuarioSistema.getUsu_id()+" vehUsu.size(): "+ vehUsu.size());
		binder.loadComponent(listveh);

	}
	catch(Exception e){e.printStackTrace();}
	
}


public void cargarComponentes()
{		
	cptVehiculos.setLabel( ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Vehiculos", objUsuarioSistema.getIdioma()));
	lshNombreVehiculo.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.nombreVehiculos", objUsuarioSistema.getIdioma()));
	btnSalir.setLabel( ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Salir", objUsuarioSistema.getIdioma()));
}

public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
}
public void desconectar()
{
	Executions.getCurrent().getSession().removeAttribute("usuario");
	Executions.sendRedirect("index.zul");	
}


public void cerrarVentana()
{
	winvehgru.detach();
}



public Grupo getObjGrupo() {
	return objGrupo;
}
public void setObjGrupo(Grupo objGrupo) {
	this.objGrupo = objGrupo;
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
public Listbox getListveh() {
	return listveh;
}
public void setListveh(Listbox listveh) {
	this.listveh = listveh;
}
public Window getWinvehgru() {
	return winvehgru;
}
public void setWinvehgru(Window winvehgru) {
	this.winvehgru = winvehgru;
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
