package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import ucsg.gmaps.dao.PerfilesDao;
import ucsg.gmaps.dao.RelPerfilPermisoDAO;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Clientes;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.Vehiculos;
import Util.cercas;

public class MantenimientoUsuarioControl extends GenericForwardComposer{

	private List<Clientes> usu= new ArrayList<Clientes>();
	private List<Clientes> usuModel= new ArrayList<Clientes>();
	private List<Perfiles> infoPerfil= new ArrayList<Perfiles>();
	private List<Perfiles> infoPerfilModel= new ArrayList<Perfiles>();
	Perfiles objPer;
	Clientes objCliente;
	Listbox listcli;
	private Clientes objUsuario;//= new Clientes();
	//private String _user=null; 
	Textbox txtFilNom;
	Textbox txtFilApe;
	Textbox txtFilCed;
	Textbox txtFilMail;
	Textbox txtFilPer;
	Textbox txtFilUsu;
	Textbox txtFilEstado;
	Combobox cmbCli;
	Combobox cmbPer;
	Window winGrupo;
	List<Clientes> clientes= new ArrayList<Clientes>();
	List<Clientes> clientesModel= new ArrayList<Clientes>();
	List<Vehiculos> veh=  new ArrayList<Vehiculos>();
	List<Vehiculos> vehModel=  new ArrayList<Vehiculos>();
	Clientes objCli;
	MapUsuarioSistema objUsuarioSistema = null;
	AnnotateDataBinder binder;
	
	
	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}
	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}
	public List<Vehiculos> getVeh() {
		return veh;
	}
	public void setVeh(List<Vehiculos> veh) {
		this.veh = veh;
	}
	public List<Vehiculos> getVehModel() {
		return vehModel;
	}
	public void setVehModel(List<Vehiculos> vehModel) {
		this.vehModel = vehModel;
	}
	public Textbox getTxtFilEstado() {
		return txtFilEstado;
	}
	public void setTxtFilEstado(Textbox txtFilEstado) {
		this.txtFilEstado = txtFilEstado;
	}
	public Clientes getObjCliente() {
		return objCliente;
	}
	public void setObjCliente(Clientes objCliente) {
		this.objCliente = objCliente;
	}
	public Perfiles getObjPer() {
		return objPer;
	}
	public void setObjPer(Perfiles objPer) {
		this.objPer = objPer;
	}
	public List<Perfiles> getInfoPerfil() {
		return infoPerfil;
	}
	public void setInfoPerfil(List<Perfiles> infoPerfil) {
		this.infoPerfil = infoPerfil;
	}
	public List<Perfiles> getInfoPerfilModel() {
		return infoPerfilModel;
	}
	public void setInfoPerfilModel(List<Perfiles> infoPerfilModel) {
		this.infoPerfilModel = infoPerfilModel;
	}
	public Combobox getCmbPer() {
		return cmbPer;
	}
	public void setCmbPer(Combobox cmbPer) {
		this.cmbPer = cmbPer;
	}
	public Clientes getObjCli() {
		return objCli;
	}
	public void setObjCli(Clientes objCli) {
		this.objCli = objCli;
	}
	public Combobox getCmbCli() {
		return cmbCli;
	}
	public void setCmbCli(Combobox cmbCli) {
		this.cmbCli = cmbCli;
	}
	public List<Clientes> getClientes() {
		return clientes;
	}
	public void setClientes(List<Clientes> clientes) {
		this.clientes = clientes;
	}
	public List<Clientes> getClientesModel() {
		return clientesModel;
	}
	public void setClientesModel(List<Clientes> clientesModel) {
		this.clientesModel = clientesModel;
	}
	public Window getWinGrupo() {
		return winGrupo;
	}
	public void setWinGrupo(Window winGrupo) {
		this.winGrupo = winGrupo;
	}
	public Textbox getTxtFilNom() {
		return txtFilNom;
	}
	public void setTxtFilNom(Textbox txtFilNom) {
		this.txtFilNom = txtFilNom;
	}
	public Textbox getTxtFilApe() {
		return txtFilApe;
	}
	public void setTxtFilApe(Textbox txtFilApe) {
		this.txtFilApe = txtFilApe;
	}
	public Textbox getTxtFilCed() {
		return txtFilCed;
	}

	public void setTxtFilCed(Textbox txtFilCed) {
		this.txtFilCed = txtFilCed;
	}

	public Textbox getTxtFilMail() {
		return txtFilMail;
	}

	public void setTxtFilMail(Textbox txtFilMail) {
		this.txtFilMail = txtFilMail;
	}

	public Textbox getTxtFilPer() {
		return txtFilPer;
	}

	public void setTxtFilPer(Textbox txtFilPer) {
		this.txtFilPer = txtFilPer;
	}

	public Textbox getTxtFilUsu() {
		return txtFilUsu;
	}

	public void setTxtFilUsu(Textbox txtFilUsu) {
		this.txtFilUsu = txtFilUsu;
	}

	public List<Clientes> getUsuModel() {
		return usuModel;
	}

	public void setUsuModel(List<Clientes> usuModel) {
		this.usuModel = usuModel;
	}

	public Clientes getObjUsuario() {
		return objUsuario;
	}

	public void setObjUsuario(Clientes objUsuario) {
		this.objUsuario = objUsuario;
	}

	public List<Clientes> getUsu() {
		return usu;
	}

	public void setUsu(List<Clientes> usu) {
		this.usu = usu;
	}

	public Listbox getListcli() {
		return listcli;
	}

	public void setListcli(Listbox listcli) {
		this.listcli = listcli;
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
    		cmp.setAttribute("wincliente",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		getUsuarios();
    		FillClientes();
    		FillPerfil();
    		
    		txtFilEstado.setText("A");
    		filtraUsuario();
    		//mymark2.setOpen(true);
    		//binder.loadComponent(ComAmb);
    		//binder.loadComponent(cmbTramite);
    		binder.loadComponent(listcli);

    		//binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
	

public void FillPerfil()
{	
	DatosDao dat = new DatosDao();
	infoPerfil= new ArrayList<Perfiles>();
	infoPerfilModel=  new ArrayList<Perfiles>();
	
	try{
		infoPerfilModel = dat.ConsultaPefilDao();
		infoPerfil = infoPerfilModel;
		binder.loadComponent(cmbPer);
	
	}
	catch(Exception e){e.printStackTrace();}

}

public void FillClientes()
{	
	//DatosDao dat = new DatosDao();

	clientes= new ArrayList<Clientes>();
	clientesModel=  new ArrayList<Clientes>();
	
	try{
		clientesModel = usuModel;//dat.FillClientesDao();
		clientes = clientesModel;
		binder.loadComponent(cmbCli);
	
	}
	catch(Exception e){e.printStackTrace();}

}

public void filtraUsuario()
{
	if(!txtFilApe.equals("")|| !txtFilCed.equals("") || !txtFilMail.equals("") 
			||!txtFilEstado.getText().equals("")
			|| !txtFilNom.equals("") || !txtFilPer.equals("") || !txtFilUsu.equals(""))
	{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		String usuFil="";
		String perFil="";
		usu = new ArrayList<Clientes>();
		usuFil =(objCliente!=null)?objCliente.getUsuario():"";
		perFil =(objPer!=null)?objPer.getPerNombre():"";
		for(int i=0; i< usuModel.size();i++)
		{
			if(		usuModel.get(i).getApellido().toLowerCase().contains(txtFilApe.getText().toLowerCase()) &&
					usuModel.get(i).getCedula().toLowerCase().contains(txtFilCed.getText().toLowerCase()) &&
					usuModel.get(i).getMail().toLowerCase().contains(txtFilMail.getText().toLowerCase()) &&
					usuModel.get(i).getNombre().toLowerCase().contains(txtFilNom.getText().toLowerCase()) &&
					//usuModel.get(i).getPerfil().toLowerCase().contains(txtFilPer.getText().toLowerCase()) &&
					usuModel.get(i).getPerfil().toLowerCase().contains(perFil.toLowerCase()) &&
					//usuModel.get(i).getUsuario().toLowerCase().contains(txtFilUsu.getText().toLowerCase()) 
					usuModel.get(i).getUsuario().toLowerCase().contains(usuFil.toLowerCase())&&
					usuModel.get(i).getEstado().toLowerCase().contains(txtFilEstado.getText().toLowerCase())
				)	
				
				usu.add(usuModel.get(i));
		}
		
		binder.loadComponent(listcli);
		
	}
}


public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	
	if (objUsuarioSistema==null)
		Executions.sendRedirect("DesconectaUsuario.zul");
	
	/*_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
	if (_user==null)
		Executions.sendRedirect("DesconectaUsuario.zul");*/
		//desconectar();
}


	public void cargarDataModal(){
    	try{
       		//lista doblemente enlazada
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		//System.out.println("error operaror #1:"+objUsuario.getApellido() );
    		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.1")&&objUsuario /*.getApellido()*/ ==null)|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.2")&&objUsuario /*.getApellido()*/!=null ))
    		{    		
    		Map<String,Clientes> maCliente = new HashMap<String,Clientes>();
    		maCliente.put("pClientes", objUsuario);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroUsuarios.zul",null, maCliente);
    		
    		registro.doModal();
    		objUsuario=null;
    		getUsuarios();
    		filtraUsuario();
    		binder.loadComponent(listcli);
    		}
    		else
    			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	public void cargarDataModal4(){
    	try{
       		//lista doblemente enlazada
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		//System.out.println("error operaror #1:"+objUsuario.getApellido() );
    		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.1")&&objUsuario /*.getApellido()*/ ==null)|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.2")&&objUsuario /*.getApellido()*/!=null ))
    		{    
    			objUsuario=null;
    		Map<String,Clientes> maCliente = new HashMap<String,Clientes>();
    		maCliente.put("pClientes", objUsuario);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroUsuarios.zul",null, maCliente);
    		
    		registro.doModal();
    		objUsuario=null;
    		getUsuarios();
    		binder.loadComponent(listcli);
    		}
    		else
    			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	public void cargarDataModal2(){
    	try{
       		//lista doblemente enlazada
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		//System.out.println("error operaror #1:"+objUsuario.getApellido() );
    		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.1")&&objUsuario /*.getApellido()*/ ==null)|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "1.2")&&objUsuario /*.getApellido()*/!=null ))
    		{    		
    		Map<String,Clientes> maCliente = new HashMap<String,Clientes>();
    		maCliente.put("pClientes", objUsuario);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"UsuarioGrupo.zul",null, maCliente);
    		
    		registro.doModal();
    		objUsuario=null;
    		getUsuarios();
    		binder.loadComponent(listcli);
    		}
    		else
    			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	public void cargarDataModal3(){
    	try{
       		//lista doblemente enlazada
    		
    	  		
    		Map<String,Clientes> maCliente = new HashMap<String,Clientes>();
    		maCliente.put("pClientes", objUsuario);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"VehiculosUsuarios.zul",null, maCliente);
    		
    		registro.doModal();
    		objUsuario=null;
    		getUsuarios();
    		binder.loadComponent(listcli);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	public void obtenerVehiculo()
	{		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;

		veh=  new ArrayList<Vehiculos>();
		vehModel=  new ArrayList<Vehiculos>();
		
		try{
			
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			vehModel = dat.obtieneVehiculoDao(usu_id);
			veh = vehModel;			
				
			veh=new ArrayList<Vehiculos>();
		}
		catch(Exception e){e.printStackTrace();}
		
	}
		
	
	
	public void eliminarUsuario()
	{
		
		
		int respuesta=Messagebox.show("¿Esta seguro que desea eliminar?","Atención!!!",Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
		//System.out.println("respuesta: "+respuesta);
		if(respuesta ==16)
		{				
			DatosDao dat=new DatosDao();
			boolean vehExist=false;
			boolean flagDelete=false;
			boolean flagDeleteRel=false;
			try {
			/*Statement st=null;
			ResultSet rs1=null;
			ResultSet rs=null;
			ResultSet rsExiste=null;
			ResultSet rsAct=null; 
			
			
			Connection conn= ConexionUtil.getConnection();
			
				st=conn.createStatement();*/
			
			
			if ( objUsuario != null )
			{
				
				/*rsExiste=st.executeQuery("select * from vehiculos where usu_id = "+ objUsuario.getUsu_id() +";");
				while(rsExiste.next())
				{					
					vehExist=true;
				}*/
				
				for(int i=0;i<veh.size()&&!!vehExist;i++)
					if(veh.get(i).getIdUsu()==objUsuario.getUsu_id())
						vehExist=true;
				
				if(!vehExist)
				{	
					
					dat.eliminarUsuarioDao(objUsuario.getUsu_id());
					dat.eliminarRelacionUsuarioGrupoDao(objUsuario.getUsu_id());
					
				
				//flagDelete = st.execute("delete from usuarios where usu_id = "+objUsuario.getUsu_id());
				//flagDeleteRel = st.execute("delete from rel_usu_grupo where usu_id = "+objUsuario.getUsu_id());
				Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				getUsuarios();
	    		binder.loadComponent(listcli);
				
				//System.out.println("flagDelete: "+flagDelete);
				/*if (!flagDelete &&  !flagDeleteRel )
					{
					Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
					getUsuarios();
		    		binder.loadComponent(listcli);
					}*/
				}
				else
					Messagebox.show("Error al eliminar \n Existen vehiculos utilizando este usuario","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				
				
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
	
	public void getUsuarios()
	{
		
		DatosDao dat = new DatosDao();
		
		
		usu=  new ArrayList<Clientes>();
		usuModel =  new ArrayList<Clientes>();
		try{
			usuModel = dat.obtieneUsuariosDao();
			usu = usuModel;
			binder.loadComponent(listcli);
		
		}
		catch(Exception e){e.printStackTrace();}	
		/*
		 * Clientes cv= new Clientes();
		Date fNacimiento=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		Statement st=null;
		ResultSet rs=null;
		Connection conn= ConexionUtil.getConnection();
		usu=  new ArrayList<Clientes>();
		usuModel =  new ArrayList<Clientes>();
		
		try{
			System.out.println("llena vehiculo");
			st=conn.createStatement();
			//rs=st.executeQuery("select usu_nombre, usu_apellido, Usu_cedula, usu_email, user, password from usuarios;");
			rs=st.executeQuery("select u.usu_nombre, u.usu_apellido, u.Usu_cedula, u.usu_email, u.user, u.password, u.per_id, p.per_nombre,u.usu_id, " +
					" u.usu_direccion, u.usu_telefono, u.usu_celular, u.usu_fecha_nacimiento" +
					" from perfiles p, usuarios u " +
					" where p.per_id=u.per_id and u.estado='A'; ");
			
			while(rs.next())
			{
				cv.setNombre(rs.getString(1));
				cv.setApellido(rs.getString(2));
				cv.setCedula(rs.getString(3));
				cv.setMail(rs.getString(4));
				cv.setUsuario(rs.getString(5));
				cv.setPassword(rs.getString(6));
				cv.setPer_id(rs.getInt("per_id"));
				cv.setPerfil(rs.getString("per_nombre"));
				cv.setUsu_id(rs.getInt("usu_id"));
				cv.setUsu_direccion(rs.getString("usu_direccion"));
				cv.setUsu_telefono(rs.getString("usu_telefono"));
				cv.setUsu_celular(rs.getString("usu_celular"));
				
				//sdf.format(rs.getDate("usu_fecha_nacimiento"));
				
				cv.setFecha_nacimiento( rs.getDate("usu_fecha_nacimiento"));
				
				usuModel.add(cv);
				cv=new Clientes();
				
				
			}
			usu = usuModel;
			binder.loadComponent(listcli);
	
		}
		catch(Exception e){e.printStackTrace();}
		 * */
	}

	
	
	
	
	
	
}
