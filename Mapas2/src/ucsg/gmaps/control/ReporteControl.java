package ucsg.gmaps.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;

import ucsg.gmaps.dao.PerfilesDao;
import ucsg.gmaps.dao.RelPerfilPermisoDAO;
import Util.Perfiles;

public class ReporteControl extends GenericForwardComposer{
	
	AnnotateDataBinder binder;
	String _user="";
	
public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public String get_user() {
		return _user;
	}

	public void set_user(String _user) {
		this._user = _user;
	}

public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winuser",this,true);

    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		binder.loadAll();

    		//binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }

public void getUserFromIndex()
{
	_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
	if (_user==null)
		Executions.sendRedirect("DesconectaUsuario.zul");
		//desconectar();
		
		
	System.out.println("_user: "+_user);
	
}

public void cargaFrameDinamico(String ruta,String prm_valor) throws InterruptedException
{
	Perfiles perfil = new Perfiles();
	
	//Messagebox.show("Estas Seguro de Ver la Opción","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	//Executions.getCurrent().getSession().setAttribute("_userControlPrincipal",_user);
	PerfilesDao per= new PerfilesDao(); 
			
	perfil = per.buscarPerfPorUsuario(_user);
	
	RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();

	System.out.println("prm_valor: "+prm_valor+ "--"+ perfil.getPerId());
	if(rpp.buscarPermisosDePerfilporIdPermiso(perfil, prm_valor))
			{
		//westPrincipal.setOpen(false);// 777
		//frmPrincipal.setSrc(ruta);
			Executions.sendRedirect(ruta);
			}
	else
		{
		//westPrincipal.setOpen(true);
		Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		}
}



}
