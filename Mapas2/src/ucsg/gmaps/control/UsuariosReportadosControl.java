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
import Util.Clientes;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.VehiculoUsuario;
import Util.Vehiculos;

public class UsuariosReportadosControl  extends GenericForwardComposer{
	AnnotateDataBinder	binder;
	MapUsuarioSistema objUsuarioSistema = null;
	Vehiculos objVehiculo;
	private List<Clientes> usu= new ArrayList<Clientes>();
	private List<Clientes> usuModel= new ArrayList<Clientes>();
	Listbox listcli;
	private List<VehiculoUsuario> infoVehUsu= new ArrayList<VehiculoUsuario>();
	
	Window winVehUser;
	
	//components
	private Caption cptReportaVehUsuario;
	private Listheader lshUsuario;
	private Button btnSalir;
	

	
public void doAfterCompose(Component cmp){  	
    	
		try
		{
			
			
			super.doAfterCompose(cmp);
			cmp.setAttribute("winMantCli",this,true);
			//Ejecución de Ajax
			binder = new AnnotateDataBinder(cmp);
			getUserFromIndex();
			if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
			objVehiculo =(Vehiculos)arg.get("pVehiculo");
			getUsuarios();//VehiculoUsuario
			if(objVehiculo!=null)
			{	
				getSelectedUser();
				for (int i=0;i<usuModel.size();i++)
				{
					for(int j=0;j<infoVehUsu.size();j++)
					{
						if(usuModel.get(i).getUsu_id()== infoVehUsu.get(j).getUsuId())
							{
								//usu.get(i).setSelected(true);
								usu.add(usuModel.get(i));
							}
					}
				}
			}
			cargarComponentes();
			
			binder.loadAll();
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }


public void cargarComponentes()
{
	cptReportaVehUsuario.setLabel(ReadPropertiesUtil.obtenerProperty("control.UsuariosReportados.UsuariosReportados", objUsuarioSistema.getIdioma()));
	lshUsuario.setLabel(ReadPropertiesUtil.obtenerProperty("control.UsuariosReportados.Usuario", objUsuarioSistema.getIdioma()));
	btnSalir.setLabel(ReadPropertiesUtil.obtenerProperty("control.UsuariosReportados.Salir", objUsuarioSistema.getIdioma()));
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

public void getUsuarios()
{
	
	DatosDao dat = new DatosDao();
	
	
	//usu=  new ArrayList<Clientes>();
	usuModel =  new ArrayList<Clientes>();
	try{
		usuModel = dat.obtieneUsuariosDao();
		//usu = usuModel;
		//binder.loadComponent(listcli);
	
	}
	catch(Exception e){e.printStackTrace();}	

}

public void getSelectedUser()
{
	DatosDao dat = new DatosDao();

	infoVehUsu= new ArrayList<VehiculoUsuario>();
	try{
		infoVehUsu = dat.obtieneVehiculoUsuarioDao(objVehiculo);
		}
	catch(Exception e){e.printStackTrace();}	
	
}

public void cerrarVentana()
{
	winVehUser.detach();
}



public Window getWinVehUser() {
	return winVehUser;
}
public void setWinVehUser(Window winVehUser) {
	this.winVehUser = winVehUser;
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
public Vehiculos getObjVehiculo() {
	return objVehiculo;
}
public void setObjVehiculo(Vehiculos objVehiculo) {
	this.objVehiculo = objVehiculo;
}
public List<Clientes> getUsu() {
	return usu;
}
public void setUsu(List<Clientes> usu) {
	this.usu = usu;
}
public List<Clientes> getUsuModel() {
	return usuModel;
}
public void setUsuModel(List<Clientes> usuModel) {
	this.usuModel = usuModel;
}
public Listbox getListcli() {
	return listcli;
}
public void setListcli(Listbox listcli) {
	this.listcli = listcli;
}
public List<VehiculoUsuario> getInfoVehUsu() {
	return infoVehUsu;
}
public void setInfoVehUsu(List<VehiculoUsuario> infoVehUsu) {
	this.infoVehUsu = infoVehUsu;
}


public Caption getCptReportaVehUsuario() {
	return cptReportaVehUsuario;
}


public void setCptReportaVehUsuario(Caption cptReportaVehUsuario) {
	this.cptReportaVehUsuario = cptReportaVehUsuario;
}


public Listheader getLshUsuario() {
	return lshUsuario;
}


public void setLshUsuario(Listheader lshUsuario) {
	this.lshUsuario = lshUsuario;
}


public Button getBtnSalir() {
	return btnSalir;
}


public void setBtnSalir(Button btnSalir) {
	this.btnSalir = btnSalir;
}

}
