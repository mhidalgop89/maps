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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import ucsg.gmaps.dao.PerfilesDao;
import ucsg.gmaps.dao.RelPerfilPermisoDAO;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Coordenadas;
import Util.Perfiles;

public class PerfilControl extends GenericForwardComposer{
	
	List<Perfiles> infoPer = new ArrayList<Perfiles>();
	List<Perfiles> infoPerModel = new ArrayList<Perfiles>();
	Perfiles objPerfil;// = new Perfiles();
	Listbox listPerfil;
	Textbox txtFilPer;
	Textbox txtFilEst;
	String _user;
	AnnotateDataBinder binder;
	
	
public String get_user() {
		return _user;
	}


	public void set_user(String _user) {
		this._user = _user;
	}


public Textbox getTxtFilPer() {
		return txtFilPer;
	}


	public void setTxtFilPer(Textbox txtFilPer) {
		this.txtFilPer = txtFilPer;
	}


	public Textbox getTxtFilEst() {
		return txtFilEst;
	}


	public void setTxtFilEst(Textbox txtFilEst) {
		this.txtFilEst = txtFilEst;
	}


public List<Perfiles> getInfoPer() {
		return infoPer;
	}


	public void setInfoPer(List<Perfiles> infoPer) {
		this.infoPer = infoPer;
	}


	public List<Perfiles> getInfoPerModel() {
		return infoPerModel;
	}


	public void setInfoPerModel(List<Perfiles> infoPerModel) {
		this.infoPerModel = infoPerModel;
	}


	public Perfiles getObjPerfil() {
		return objPerfil;
	}


	public void setObjPerfil(Perfiles objPerfil) {
		this.objPerfil = objPerfil;
	}


	public Listbox getListPerfil() {
		return listPerfil;
	}


	public void setListPerfil(Listbox listPerfil) {
		this.listPerfil = listPerfil;
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
    		cmp.setAttribute("winPerfil",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		getPerfil();
    		txtFilEst.setText("A");
    		filtraPerfil();
    		binder.loadComponent(listPerfil);
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

public void eliminarPerfil()
{
	
	int respuesta=Messagebox.show("¿Esta seguro que desea eliminar?","Atención!!!",Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
//System.out.println("respuesta: "+respuesta);
if(respuesta ==16)
{				
	DatosDao dat= new DatosDao();
	int cantPerfilConUsuario=0;
	boolean usuExist=false;
	
	try {
		/*
	Statement st=null;
	ResultSet rs1=null;
	ResultSet rs=null;
	ResultSet rsExiste=null;
	ResultSet rsAct=null; 
	
	boolean flagDelete=false;
	boolean flagDeleteRel=false;
	
	Connection conn= ConexionUtil.getConnection();
	
		st=conn.createStatement();*/
	
	
	if ( objPerfil != null )
	{
		
		
		cantPerfilConUsuario= dat.existePerfilConUsuarioDao((int) objPerfil.getPerId() );
		if(cantPerfilConUsuario>0)
			usuExist=true;
		/*rsExiste=st.executeQuery("select * from usuarios where per_id = "+ objPerfil.getPerId() +";");
		while(rsExiste.next())
		{					
			usuExist=true;
		}*/
		
		
		if(!usuExist)
		{	
		
			dat.eliminaRelacionPerfilPermiso(objPerfil.getPerId());
			dat.eliminaPerfilDao((int)objPerfil.getPerId());
			Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			getPerfil();
    		binder.loadComponent(listPerfil);
			//flagDeleteRel = st.execute("delete from rel_perfil_permiso where per_id = "+objPerfil.getPerId());
			//flagDelete = st.execute("delete from perfiles where per_id = "+objPerfil.getPerId());
		/*
		if (!flagDelete &&  !flagDeleteRel )
			{
			Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			getPerfil();
    		binder.loadComponent(listPerfil);
			}*/
		}
		else
			Messagebox.show("Error al eliminar \n Existen Usuarios utilizando este perfil","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		
		
	}
	

	else
	{
	//valida si user existe	
			
			Messagebox.show("No se ha seleccionado Usuario","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	}
	
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		//Messagebox.show(e.printStackTrace(),Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	}
}




}

public void filtraPerfil()
{
	if(!txtFilPer.equals("")|| !txtFilEst.equals(""))
	{
		infoPer = new ArrayList<Perfiles>();
		for(int i=0; i< infoPerModel.size();i++)
		{
			if(infoPerModel.get(i).getPerNombre().toLowerCase().contains(txtFilPer.getText().toLowerCase()) &&
					infoPerModel.get(i).getPerEstado().toLowerCase().contains(txtFilEst.getText().toLowerCase())	)
				infoPer.add(infoPerModel.get(i));
		}
		
		binder.loadComponent(listPerfil);
		
	}

}



public void cargarDataModal2(){
	try{
		
		

   		//lista doblemente enlazada
		Map<String,Perfiles> maPerfiles = new HashMap<String,Perfiles>();
		maPerfiles.put("pPerfil", objPerfil);
		
		
		final Window registro = (Window) Executions.createComponents(
				"UsuarioPerfil.zul",null, maPerfiles);
		
		registro.doModal();
		//objCategoria=null;
		//objCategoria= new CategoriaVehiculo();
		objPerfil=null;
		getPerfil();
		txtFilPer.setText("");
		txtFilEst.setText("");
		binder.loadComponent(listPerfil);
		
		

	}catch(Exception e){
		e.printStackTrace();
	}

}


public void cargarDataModal(){
	try{
		
		Perfiles perfil = new Perfiles();
		PerfilesDao per= new PerfilesDao();
		perfil = per.buscarPerfPorUsuario(_user);
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
		
		
		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "10.1")&& objPerfil==null)|| 
			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "10.2")&& objPerfil!=null ))
		{
		int id_usuario=0;
		/*String user=null;
		Statement st=null;
		ResultSet rs=null;
		Connection conn= ConexionUtil.getConnection();
		
		st=conn.createStatement();
		//rs=st.executeQuery("select id_categoria_vehiculo, descripcion, identificador from categoria_vehiculo ;");
		
		rs=st.executeQuery("  select usu_id,user from usuarios where User = '"+_user+"'");
		while(rs.next())
		{
			id_usuario = rs.getInt(1);
			user=rs.getString(2);
		}
		
		objCategoria.setIdUser(id_usuario);
		objCategoria.setUser(user);
		
		System.out.println("objCategoria->>viaja: "+objCategoria.getIdUser()+"-"+objCategoria.getUser());*/
   		//lista doblemente enlazada
		Map<String,Perfiles> maPerfiles = new HashMap<String,Perfiles>();
		maPerfiles.put("pPerfil", objPerfil);
		
		
		final Window registro = (Window) Executions.createComponents(
				"RegistroPerfil.zul",null, maPerfiles);
		
		registro.doModal();
		//objCategoria=null;
		//objCategoria= new CategoriaVehiculo();
		objPerfil=null;
		getPerfil();
		txtFilPer.setText("");
		txtFilEst.setText("");
		binder.loadComponent(listPerfil);
		
		}
		else
			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);

	}catch(Exception e){
		e.printStackTrace();
	}

}


public void cargarDataModal3(){
	try{
		
		Perfiles perfil = new Perfiles();
		PerfilesDao per= new PerfilesDao();
		perfil = per.buscarPerfPorUsuario(_user);
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
		
		
		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "10.1")&& objPerfil==null)|| 
			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "10.2")&& objPerfil!=null ))
		{
		int id_usuario=0;
		/*String user=null;
		Statement st=null;
		ResultSet rs=null;
		Connection conn= ConexionUtil.getConnection();
		
		st=conn.createStatement();
		//rs=st.executeQuery("select id_categoria_vehiculo, descripcion, identificador from categoria_vehiculo ;");
		
		rs=st.executeQuery("  select usu_id,user from usuarios where User = '"+_user+"'");
		while(rs.next())
		{
			id_usuario = rs.getInt(1);
			user=rs.getString(2);
		}
		
		objCategoria.setIdUser(id_usuario);
		objCategoria.setUser(user);
		
		System.out.println("objCategoria->>viaja: "+objCategoria.getIdUser()+"-"+objCategoria.getUser());*/
   		//lista doblemente enlazada
		Map<String,Perfiles> maPerfiles = new HashMap<String,Perfiles>();
		objPerfil=null;
		maPerfiles.put("pPerfil", objPerfil);
		
		
		final Window registro = (Window) Executions.createComponents(
				"RegistroPerfil.zul",null, maPerfiles);
		
		registro.doModal();
		//objCategoria=null;
		//objCategoria= new CategoriaVehiculo();
		objPerfil=null;
		getPerfil();
		txtFilPer.setText("");
		txtFilEst.setText("");
		binder.loadComponent(listPerfil);
		
		}
		else
			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);

	}catch(Exception e){
		e.printStackTrace();
	}

}

public void getPerfil()
{
	DatosDao dat = new DatosDao();
	int usu_id=0;
	infoPerModel= new ArrayList<Perfiles>();		
	try{
		
		infoPerModel = dat.ConsultaPefilDao();
		infoPer = infoPerModel;
		binder.loadComponent(listPerfil);			
	}
	catch(Exception e){e.printStackTrace();}
	
	
	
	/*
	
	Perfiles perfil= new Perfiles();

	Statement st=null;
	ResultSet rs=null;
	Connection conn= ConexionUtil.getConnection();
	infoPerModel=  new ArrayList<Perfiles>();
	infoPer=  new ArrayList<Perfiles>();
	
	try{
		System.out.println("llena perfil");
		st=conn.createStatement();

			rs=st.executeQuery("  select per_id, per_nombre, per_estado  from perfiles where per_estado='A'");
		
	
		while(rs.next())
		{
			perfil.setPerId(rs.getLong("per_id"));
			perfil.setPerNombre(rs.getString("per_nombre"));
			perfil.setPerEstado(rs.getString("per_estado"));
			
			
			infoPerModel.add(perfil);
			perfil=new Perfiles();
			
			
		}
		infoPer = infoPerModel;
		binder.loadComponent(listPerfil);
		
		
		
	}
	catch(Exception e){e.printStackTrace();}*/
}


}
