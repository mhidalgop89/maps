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
import Util.Clientes;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;

public class VehiculoCategoriaControl  extends GenericForwardComposer{
	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<Vehiculos> vehUsu= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	private CategoriaVehiculo objCategoria= new CategoriaVehiculo();
	Listbox listveh;
	Window winvehcat;
	private Clientes objUsuario = new Clientes();
	
	//component
	private Caption cptVehiculo;
	private Listheader lshNombreVeh;
	private Button btnSalir;


public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winvehusu",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objCategoria=(CategoriaVehiculo)arg.get("pUsuario");
    		getUserFromIndex();
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		obtenerVehiculo();
    		cargarComponentes();
    		binder.loadAll();

    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

public void cargarComponentes()
{
	cptVehiculo.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoCategoriaControl.Vehiculos", objUsuarioSistema.getIdioma()));
	lshNombreVeh.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoCategoriaControl.NombreVehiculo", objUsuarioSistema.getIdioma()));
	btnSalir.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoCategoriaControl.Salir", objUsuarioSistema.getIdioma()));
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
			if(vehModel.get(i).getCatVeh()== objCategoria.getId_categoria() )
				vehUsu.add(vehModel.get(i));
		
			
		System.out.println("objUsuarioSistema.getUsu_id(): "+objUsuarioSistema.getUsu_id()+" vehUsu.size(): "+ vehUsu.size()+" objUsuario.getUsu_id(): "+objUsuario.getUsu_id());
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
	winvehcat.detach();
}


public CategoriaVehiculo getObjCategoria() {
	return objCategoria;
}

public void setObjCategoria(CategoriaVehiculo objCategoria) {
	this.objCategoria = objCategoria;
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

public Listbox getListveh() {
	return listveh;
}

public void setListveh(Listbox listveh) {
	this.listveh = listveh;
}

public Window getWinvehcat() {
	return winvehcat;
}

public void setWinvehcat(Window winvehcat) {
	this.winvehcat = winvehcat;
}

public Clientes getObjUsuario() {
	return objUsuario;
}

public void setObjUsuario(Clientes objUsuario) {
	this.objUsuario = objUsuario;
}

public Caption getCptVehiculo() {
	return cptVehiculo;
}

public void setCptVehiculo(Caption cptVehiculo) {
	this.cptVehiculo = cptVehiculo;
}

public Listheader getLshNombreVeh() {
	return lshNombreVeh;
}

public void setLshNombreVeh(Listheader lshNombreVeh) {
	this.lshNombreVeh = lshNombreVeh;
}

public Button getBtnSalir() {
	return btnSalir;
}

public void setBtnSalir(Button btnSalir) {
	this.btnSalir = btnSalir;
}

}
