package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import Util.Clientes;
import Util.MapUsuarioSistema;
import Util.Perfiles;

public class UsuarioPerfilControl extends GenericForwardComposer{
	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<Clientes> usu= new ArrayList<Clientes>();
	private List<Clientes> usuModel= new ArrayList<Clientes>();
	Listbox listcli;
	Perfiles objPerfil = new Perfiles();
	Window winPerUsu;
	
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

	public Perfiles getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(Perfiles objPerfil) {
		this.objPerfil = objPerfil;
	}

public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winPerUsu",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objPerfil=(Perfiles)arg.get("pPerfil");
    		getUserFromIndex();
    		getUsuarios();
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
public void getUsuarios()
{
	
	DatosDao dat = new DatosDao();
	
	
	usu=  new ArrayList<Clientes>();
	usuModel =  new ArrayList<Clientes>();
	try{
		usuModel = dat.obtieneUsuariosDao();
		
		for(int i=0;i<usuModel.size();i++)
			if(usuModel.get(i).getPer_id()==objPerfil.getPerId())
				usu.add(usuModel.get(i));
				
		//usu = usuModel;
		binder.loadComponent(listcli);
	
	}
	catch(Exception e){e.printStackTrace();}	
	
}


public void cerrarVentana()
{
	winPerUsu.detach();
}
	
	
}
