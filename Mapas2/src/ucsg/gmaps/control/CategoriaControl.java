package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.gmaps.Gmarker;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
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
import Util.MapUsuarioSistema;
import Util.Marks;
import Util.Perfiles;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;

public class CategoriaControl extends GenericForwardComposer{
	
	private List<CategoriaVehiculo> catVeh= new ArrayList<CategoriaVehiculo>();
	private List<CategoriaVehiculo> catVehModel= new ArrayList<CategoriaVehiculo>();
	Listbox listuser;
	String _user=null;
	private CategoriaVehiculo objCategoria;//= new CategoriaVehiculo();
	
	MapUsuarioSistema objUsuarioSistema = null;
	List<Vehiculos> veh= new ArrayList<Vehiculos>();
	List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	AnnotateDataBinder binder;
	///componentes
	Textbox txtFilDes;
	Textbox txtFilIden;
	Textbox txtFilEstado;
	private Listheader lshAcciones;
	private Listheader lshDescripcion;
	private Listheader lshIdentificador;
	private Listheader lshEstado;
	private Image imgEliminar;
	private Image imgEditar;
	private Image imgVerVehiculo;
	
	private Fisheye febCategoria;

	
	
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
    		getCategoria();
    		txtFilEstado.setText("A");
    		filtraCategoria();
    		//mymark2.setOpen(true);
    		//binder.loadComponent(ComAmb);
    		//binder.loadComponent(cmbTramite);
    		binder.loadComponent(listuser);

    		//binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }

public void cargarComponentes()
{
	txtFilDes.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.FiltroDescripcion", objUsuarioSistema.getIdioma()));
	txtFilIden.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.FiltroIdentificador", objUsuarioSistema.getIdioma()));
	txtFilEstado.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.FiltroEstado", objUsuarioSistema.getIdioma()));
	lshAcciones.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Acciones", objUsuarioSistema.getIdioma()));
	lshDescripcion.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Descripcion", objUsuarioSistema.getIdioma()));
	lshIdentificador.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Identificador", objUsuarioSistema.getIdioma()));
	lshEstado.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Estado", objUsuarioSistema.getIdioma()));
	imgEliminar.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Eliminar", objUsuarioSistema.getIdioma()));
	imgEditar.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Editar", objUsuarioSistema.getIdioma()));
	imgVerVehiculo.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.VerVehiculo", objUsuarioSistema.getIdioma()));
	
	febCategoria.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.AgregarCategoria", objUsuarioSistema.getIdioma()));
}

public void desconectar()
{

	Executions.getCurrent().getSession().removeAttribute("usuario");
	Executions.sendRedirect("index.zul");
	
}


public void filtraCategoria()
{
	if(!txtFilDes.equals("")|| !txtFilIden.equals("")||!txtFilEstado.equals(""))
	{System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		catVeh = new ArrayList<CategoriaVehiculo>();
		for(int i=0; i< catVehModel.size();i++)
		{
			if(
					catVehModel.get(i).getDescripcion().toLowerCase().contains(txtFilDes.getText().toLowerCase()) &&
					catVehModel.get(i).getIdentificador().toLowerCase().contains(txtFilIden.getText().toLowerCase())&&
					catVehModel.get(i).getEstado().toLowerCase().contains(txtFilEstado.getText().toLowerCase())
				)
				catVeh.add(catVehModel.get(i));
		}
		
		binder.loadComponent(listuser);
		
	}
}
	

public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");	
}


	public void eliminarCategoria()
	{
		int respuesta=Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.EliminaCategoria", objUsuarioSistema.getIdioma()),
				ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Atencion", objUsuarioSistema.getIdioma()),
				Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
		
		if(respuesta ==16)
		{				
			boolean vehExist=false;
			
			DatosDao dat = new DatosDao();
			
			if ( objCategoria.getIdentificador() != null )
			{
				
				/*rsInsert=st.executeQuery("select * from vehiculos where ve_categoria = "+ objCategoria.getId_categoria() +";");
				while(rsInsert.next())
				{					
					vehExist=true;
				}*/
				
				veh=  new ArrayList<Vehiculos>();
				vehModel=  new ArrayList<Vehiculos>();
				vehModel = dat.obtieneVehiculoDao(objUsuarioSistema.getUsu_id());
				veh = vehModel;		
				
				for(int i=0;i<veh.size()&&!vehExist;i++)
				{
					if(veh.get(i).getCatVeh()==objCategoria.getId_categoria())
							vehExist=true;
							
				}
				
				
				if(!vehExist)
				{	
				System.out.println("objCategoria.getId_categoria(): "+objCategoria.getId_categoria());
				dat.eliminarCategoriaDao(objCategoria);
					Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.RegistroEliminado", objUsuarioSistema.getIdioma()),
							ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Atencion", objUsuarioSistema.getIdioma()),
							Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
					getCategoria();
		    		binder.loadComponent(listuser);
				}
				else
					Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.ErrorRegistroEliminado", objUsuarioSistema.getIdioma()),
							ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Atencion", objUsuarioSistema.getIdioma()),
							Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				
				
			}
			
	
			else
			{
			//valida si user existe	
					
					Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.NoSeleccionCategoria", objUsuarioSistema.getIdioma()),
							ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Atencion", objUsuarioSistema.getIdioma()),
							Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			}
	}
		
	}
	
	public void cargarDataModal(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		perfil = per.buscarPerfPorUsuario(_user);
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "2.1")&& objCategoria==null)|| 
    			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "2.2")&& objCategoria!=null ))
    		{
    		int id_usuario=0;
    		String user=null;
    		/*Statement st=null;
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
    		
    		objCategoria.setIdUser(objUsuarioSistema.getUsu_id());
    		objCategoria.setUser(objUsuarioSistema.getUser());
    		
    		System.out.println("objCategoria->>viaja: "+objCategoria.getIdUser()+"-"+objCategoria.getUser());*/
       		//lista doblemente enlazada
    		Map<String,CategoriaVehiculo> maCategoria = new HashMap<String,CategoriaVehiculo>();
    		maCategoria.put("pUsuario", objCategoria);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroCategoria.zul",null, maCategoria);
    		
    		registro.doModal();
    		//objCategoria=null;
    		objCategoria=null;// new CategoriaVehiculo();
    		getCategoria();
    		txtFilDes.setText("");
    		txtFilIden.setText("");
    		filtraCategoria();
    		binder.loadComponent(listuser);
    		
    		}
    		else
    			Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.NoPermisoOpcion", objUsuarioSistema.getIdioma()),
    					ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Atencion", objUsuarioSistema.getIdioma()),
    					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		/*
    		//lista doblemente enlazada
    		Map<String,FrUsuarios> maUsuario = new HashMap<String,FrUsuarios>();
    		maUsuario.put("pUsuario", objUsuario);
    		
    	final Window registro = (Window) Executions.createComponents(
				"registra_usuario.zul",null, maUsuario);
		registro.doModal();
		objUsuario = null;
		HibernateSessionFactory.getSession().clear();
		Session sess = HibernateSessionFactory.getSession();
		UsuarioBo bo = new UsuarioBo();
		us = bo.obtenerUsuario(sess); 
		binder.loadComponent(listuser);
		//Poner para que no salga mensaje al cerrar ventana
		//System.out.println(objUsuario.getApellido());
		
		registro.setClosable(true);*/
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
    		
    		
    		if(	(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "2.1")&& objCategoria==null)|| 
    			(rpp.buscarPermisosDePerfilporIdPermiso(perfil, "2.2")&& objCategoria!=null ))
    		{
    		int id_usuario=0;
    		String user=null;
    		objCategoria=null;
    		
       		//lista doblemente enlazada
    		Map<String,CategoriaVehiculo> maCategoria = new HashMap<String,CategoriaVehiculo>();
    		maCategoria.put("pUsuario", objCategoria);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroCategoria.zul",null, maCategoria);
    		
    		registro.doModal();
    		//objCategoria=null;
    		objCategoria=null;// new CategoriaVehiculo();
    		getCategoria();
    		txtFilDes.setText("");
    		txtFilIden.setText("");
    		filtraCategoria();
    		binder.loadComponent(listuser);
    		
    		}
    		else
    			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	
	public void cargarDataModal2(){
    	try{
    		
    		int id_usuario=0;
    		String user=null;

    		objCategoria.setIdUser(objUsuarioSistema.getUsu_id());
    		objCategoria.setUser(objUsuarioSistema.getUser());
    		
    		System.out.println("objCategoria->>viaja: "+objCategoria.getIdUser()+"-"+objCategoria.getUser());
       		//lista doblemente enlazada
    		Map<String,CategoriaVehiculo> maCategoria = new HashMap<String,CategoriaVehiculo>();
    		maCategoria.put("pUsuario", objCategoria);
    		
    		
    		final Window registro = (Window) Executions.createComponents(
    				"VehiculoCategoria.zul",null, maCategoria);
    		
    		registro.doModal();
    		//objCategoria=null;
    		objCategoria= new CategoriaVehiculo();
    		getCategoria();
    		txtFilDes.setText("");
    		txtFilIden.setText("");
    		binder.loadComponent(listuser);
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	public void getCategoria()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		catVeh= new ArrayList<CategoriaVehiculo>();
		catVehModel=  new ArrayList<CategoriaVehiculo>();
		
		try{
			
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			catVehModel = dat.obtenerCatVehDao(objUsuarioSistema);
			
			 catVeh = catVehModel;
			binder.loadComponent(listuser);
		
		}
		catch(Exception e){e.printStackTrace();}
		
	}
	
	
	
	public Textbox getTxtFilEstado() {
		return txtFilEstado;
	}

	public void setTxtFilEstado(Textbox txtFilEstado) {
		this.txtFilEstado = txtFilEstado;
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

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Textbox getTxtFilDes() {
		return txtFilDes;
	}

	public void setTxtFilDes(Textbox txtFilDes) {
		this.txtFilDes = txtFilDes;
	}

	public Textbox getTxtFilIden() {
		return txtFilIden;
	}

	public void setTxtFilIden(Textbox txtFilIden) {
		this.txtFilIden = txtFilIden;
	}

	public List<CategoriaVehiculo> getCatVehModel() {
		return catVehModel;
	}

	public void setCatVehModel(List<CategoriaVehiculo> catVehModel) {
		this.catVehModel = catVehModel;
	}

	public String get_user() {
		return _user;
	}

	public void set_user(String _user) {
		this._user = _user;
	}

	public CategoriaVehiculo getObjCategoria() {
		return objCategoria;
	}

	public void setObjCategoria(CategoriaVehiculo objCategoria) {
		this.objCategoria = objCategoria;
	}

	public Listbox getListuser() {
		return listuser;
	}

	public void setListuser(Listbox listuser) {
		this.listuser = listuser;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public List<CategoriaVehiculo> getCatVeh() {
		return catVeh;
	}

	public void setCatVeh(List<CategoriaVehiculo> catVeh) {
		this.catVeh = catVeh;
	}


	public Listheader getLshAcciones() {
		return lshAcciones;
	}


	public void setLshAcciones(Listheader lshAcciones) {
		this.lshAcciones = lshAcciones;
	}


	public Listheader getLshDescripcion() {
		return lshDescripcion;
	}


	public void setLshDescripcion(Listheader lshDescripcion) {
		this.lshDescripcion = lshDescripcion;
	}


	public Listheader getLshIdentificador() {
		return lshIdentificador;
	}


	public void setLshIdentificador(Listheader lshIdentificador) {
		this.lshIdentificador = lshIdentificador;
	}


	public Listheader getLshEstado() {
		return lshEstado;
	}


	public void setLshEstado(Listheader lshEstado) {
		this.lshEstado = lshEstado;
	}


	public Image getImgEliminar() {
		return imgEliminar;
	}


	public void setImgEliminar(Image imgEliminar) {
		this.imgEliminar = imgEliminar;
	}


	public Image getImgEditar() {
		return imgEditar;
	}


	public void setImgEditar(Image imgEditar) {
		this.imgEditar = imgEditar;
	}


	public Image getImgVerVehiculo() {
		return imgVerVehiculo;
	}


	public void setImgVerVehiculo(Image imgVerVehiculo) {
		this.imgVerVehiculo = imgVerVehiculo;
	}
	

}
