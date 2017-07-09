package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



import ucsg.gmaps.dao.DatosDao;
import ucsg.gmaps.dao.PerfilesDao;
import ucsg.gmaps.dao.RelPerfilPermisoDAO;
import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.ReadPropertiesUtil;

public class GrupoControl extends GenericForwardComposer{
	
private Listbox listGrupo; 
private List<Grupo> infoGrupo = new ArrayList<Grupo>();
private List<Grupo> infoGrupoModal = new ArrayList<Grupo>();
private Grupo objGrupo;//= new Grupo();
//private String _user;

MapUsuarioSistema objUsuarioSistema = null;
AnnotateDataBinder binder;


private Fisheye febAgregar;
private Textbox txtFilDes;
private Textbox txtFilGrupo;
private Listheader lshEditar;
private Listheader lshGrupo;
private Listheader lshValor;
private Image imgBorrar;
private Image imgEditar;
private Image imgVerVehiculos;
private Image imgVerUsuarios;



	


public void doAfterCompose(Component cmp){  	
	
	try
	{
		super.doAfterCompose(cmp);
		cmp.setAttribute("winuser",this,true);
		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
		//Ejecución de Ajax
		binder = new AnnotateDataBinder(cmp);
		
		getUserFromIndex();
		
		if (objUsuarioSistema==null)
		{
				desconectar();
				return;
		}
		cargarComponentes();
		
		
		getGrupo();
		
		//binder.loadComponent(listuser);

		binder.loadAll();
	}
	catch(Exception e){
		System.out.println("doAfterCompose Exception");
		e.printStackTrace();
	}
}

public void cargarComponentes()
{
	System.out.println("--------->"+objUsuarioSistema.getIdioma());
	febAgregar.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.AgregarGrupo", objUsuarioSistema.getIdioma()));
	txtFilDes.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.FiltroGrupo", objUsuarioSistema.getIdioma()));
	txtFilGrupo.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.FiltroValor", objUsuarioSistema.getIdioma()));
	lshEditar.setLabel(ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.Editar", objUsuarioSistema.getIdioma()));
	lshGrupo.setLabel(ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.Grupo", objUsuarioSistema.getIdioma()));
	lshValor.setLabel(ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.Valor", objUsuarioSistema.getIdioma()));
	
	imgBorrar.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.Borrar", objUsuarioSistema.getIdioma()));
	imgEditar.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.Editar", objUsuarioSistema.getIdioma()));
	imgVerVehiculos.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.VerVehiculo", objUsuarioSistema.getIdioma()));
	imgVerUsuarios.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoGrupoControl.VerUsuario", objUsuarioSistema.getIdioma()));	
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



public void eliminarGrupo()
{
	int respuesta=Messagebox.show("¿Esta seguro que desea eliminar?","Atención!!!",Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
	//System.out.println("respuesta: "+respuesta);
	if(respuesta ==16)
	{				
		Statement st=null;
		
		ResultSet rs1=null;
		ResultSet rs=null;
		ResultSet rsVeh=null;
		ResultSet rsUsu=null; 
		boolean vehExist=false;
		boolean usuExist=false;
		boolean flagDelete=false;
		
		Connection conn= ConexionUtil.getConnection();
		try {
			st=conn.createStatement();
		
		
		//if ( objCategoria.getIdentificador() != null )
			if ( objGrupo != null )
		{
			
				rsVeh=st.executeQuery("select * from vehiculos where gr_id = "+ objGrupo.getGr_Id() +";");
			while(rsVeh.next())
			{					
				vehExist=true;
			}
			rsUsu=st.executeQuery("select * from rel_usu_grupo where gr_id = "+ objGrupo.getGr_Id() +";");
			while(rsUsu.next())
			{					
				usuExist=true;
			}
			
			if(!vehExist && !usuExist)
			{	
			
			//System.out.println("llega llena usu: ");
			flagDelete = st.execute("delete from grupo where gr_id = "+objGrupo.getGr_Id());
			//System.out.println("flagDelete: "+flagDelete);
			if (!flagDelete)
				{
				Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				getGrupo();
	    		binder.loadComponent(listGrupo);
				}
			}
			else
				Messagebox.show("Error al eliminar \n Existen vehiculos o Usuarios utilizando este grupo","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			
			
		}
		

		else
		{
		//valida si user existe	
				
				Messagebox.show("No se ha seleccionado Grupo","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Messagebox.show(e.printStackTrace(),Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		}
}	

}
//cargarDataModal2()
public void cargarDataModal3(){
	try{

		//lista doblemente enlazada
		Map<String,Grupo> maGrupo = new HashMap<String,Grupo>();
		maGrupo.put("pGrupo", objGrupo);
		
		final Window registro = (Window) Executions.createComponents(
				"UsuarioGrupoRel.zul",null, maGrupo);
		
		registro.doModal();
		objGrupo= null;
	
		
		getGrupo();
		binder.loadComponent(listGrupo);
		
		

	}catch(Exception e){
		e.printStackTrace();
	}

}

public void cargarDataModal2(){
	try{
		
	
		//lista doblemente enlazada
		Map<String,Grupo> maGrupo = new HashMap<String,Grupo>();
		maGrupo.put("pGrupo", objGrupo);
		
		final Window registro = (Window) Executions.createComponents(
				"VehiculoGrupo.zul",null, maGrupo);
		
		registro.doModal();
		objGrupo= null;
	
		
		getGrupo();
		binder.loadComponent(listGrupo);
		
		

	}catch(Exception e){
		e.printStackTrace();
	}

}

public void cargarDataModal(){
	try{
		
		Perfiles perfil = new Perfiles();
		PerfilesDao per= new PerfilesDao();
		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
		
		
		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "9.1")&& objGrupo ==null)|| 
			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "9.2")&& objGrupo !=null ))
		{
		//lista doblemente enlazada
		Map<String,Grupo> maGrupo = new HashMap<String,Grupo>();
		maGrupo.put("pGrupo", objGrupo);
		
		final Window registro = (Window) Executions.createComponents(
				"RegistroGrupo.zul",null, maGrupo);
		
		registro.doModal();
		objGrupo= null;
	
		
		getGrupo();
		binder.loadComponent(listGrupo);
		
		}
		else
			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);

	}catch(Exception e){
		e.printStackTrace();
	}

}

		
public void cargarDataModal4(){
	try{
		
		Perfiles perfil = new Perfiles();
		PerfilesDao per= new PerfilesDao();
		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
		
		
		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "9.1")&& objGrupo ==null)|| 
			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "9.2")&& objGrupo !=null ))
		{
		//lista doblemente enlazada
		Map<String,Grupo> maGrupo = new HashMap<String,Grupo>();
		objGrupo=null;
		maGrupo.put("pGrupo", objGrupo);
		
		final Window registro = (Window) Executions.createComponents(
				"RegistroGrupo.zul",null, maGrupo);
		
		registro.doModal();
		objGrupo= null;
	
		
		getGrupo();
		binder.loadComponent(listGrupo);
		
		}
		else
			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);

	}catch(Exception e){
		e.printStackTrace();
	}

}
			
		
public void filtraGrupo()
{
	if(!txtFilDes.equals("")|| !txtFilGrupo.equals(""))
	{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		infoGrupo = new ArrayList<Grupo>();
		
		for(int i=0; i< infoGrupoModal.size();i++)
		{
			if(infoGrupoModal.get(i).getGr_descripcion().toLowerCase().contains(txtFilDes.getText().toLowerCase()) &&
					String.valueOf(infoGrupoModal.get(i).getGr_value()).toLowerCase().contains(txtFilGrupo.getText().toLowerCase())  )	
				infoGrupo.add(infoGrupoModal.get(i));
		}
		
		binder.loadComponent(listGrupo);
		
	}
}	



public void getGrupo()
{
	DatosDao dat = new DatosDao();
	 infoGrupo = new ArrayList<Grupo>();
	 infoGrupoModal = new ArrayList<Grupo>();
	
	try{
		infoGrupoModal = dat.obtenerGruVehDao();	
		infoGrupo = infoGrupoModal;
		binder.loadComponent(listGrupo);
	
	}
	catch(Exception e){e.printStackTrace();}
	
}




public MapUsuarioSistema getObjUsuarioSistema() {
	return objUsuarioSistema;
}

public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
	this.objUsuarioSistema = objUsuarioSistema;
}

public Textbox getTxtFilGrupo() {
	return txtFilGrupo;
}

public void setTxtFilGrupo(Textbox txtFilGrupo) {
	this.txtFilGrupo = txtFilGrupo;
}

public Textbox getTxtFilDes() {
	return txtFilDes;
}

public void setTxtFilDes(Textbox txtFilDes) {
	this.txtFilDes = txtFilDes;
}

public List<Grupo> getInfoGrupoModal() {
	return infoGrupoModal;
}

public void setInfoGrupoModal(List<Grupo> infoGrupoModal) {
	this.infoGrupoModal = infoGrupoModal;
}

public Listbox getListGrupo() {
	return listGrupo;
}

public void setListGrupo(Listbox listGrupo) {
	this.listGrupo = listGrupo;
}

public List<Grupo> getInfoGrupo() {
	return infoGrupo;
}

public void setInfoGrupo(List<Grupo> infoGrupo) {
	this.infoGrupo = infoGrupo;
}

public Grupo getObjGrupo() {
	return objGrupo;
}

public void setObjGrupo(Grupo objGrupo) {
	this.objGrupo = objGrupo;
}

public AnnotateDataBinder getBinder() {
	return binder;
}

public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}



public Listheader getLshEditar() {
	return lshEditar;
}


public void setLshEditar(Listheader lshEditar) {
	this.lshEditar = lshEditar;
}


public Listheader getLshGrupo() {
	return lshGrupo;
}


public void setLshGrupo(Listheader lshGrupo) {
	this.lshGrupo = lshGrupo;
}


public Listheader getLshValor() {
	return lshValor;
}


public void setLshValor(Listheader lshValor) {
	this.lshValor = lshValor;
}


public Image getImgBorrar() {
	return imgBorrar;
}


public void setImgBorrar(Image imgBorrar) {
	this.imgBorrar = imgBorrar;
}


public Image getImgEditar() {
	return imgEditar;
}


public void setImgEditar(Image imgEditar) {
	this.imgEditar = imgEditar;
}


public Image getImgVerVehiculos() {
	return imgVerVehiculos;
}


public void setImgVerVehiculos(Image imgVerVehiculos) {
	this.imgVerVehiculos = imgVerVehiculos;
}


public Image getImgVerUsuarios() {
	return imgVerUsuarios;
}


public void setImgVerUsuarios(Image imgVerUsuarios) {
	this.imgVerUsuarios = imgVerUsuarios;
}

public Fisheye getFebAgregar() {
	return febAgregar;
}

public void setFebAgregar(Fisheye febAgregar) {
	this.febAgregar = febAgregar;
}



}
