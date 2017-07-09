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
import org.zkoss.zul.Combobox;
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
import Util.Clientes;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.ReadPropertiesUtil;
import Util.Rel_usu_grupo;
import Util.Vehiculos;
import Util.cercas;


public class VehiculoControl extends GenericForwardComposer {
	
	private List<Vehiculos> veh= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	List<CategoriaVehiculo> catVeh= new ArrayList<CategoriaVehiculo>();
	List<CategoriaVehiculo> catVehModel= new ArrayList<CategoriaVehiculo>();
	List<Clientes> clientes= new ArrayList<Clientes>();
	List<Clientes> clientesModel= new ArrayList<Clientes>();
	
	CategoriaVehiculo objCatSelected;//= new CategoriaVehiculo();
	cercas cerca;
	
	Clientes objCli;
	private Vehiculos objVehiculo;
	private int id_grupo=0;
	Listbox listveh;
	private Vehiculos objVeh = new Vehiculos();
	//private String _user = null;
	private List<Grupo> gruVeh= new ArrayList<Grupo>();
	private List<Grupo> gruVehModel= new ArrayList<Grupo>();
	private List<cercas> cercas= new ArrayList<cercas>();
	private List<cercas> cercasModel= new ArrayList<cercas>();
	
	
	private Grupo objGruSelected;
	int id_categoria=0;
	
	Textbox txtFilUsu;
	Textbox txtFilCat;
	Textbox txtFilGru;
	Textbox txtFilCer;
	
	Combobox cmbGruVeh;//
	Combobox cmbCatVeh;//
	Combobox cmbCer;//
	Combobox cmbCli;//
	 
	Textbox txtFilNomVeh;//
	Textbox txtFilIme;//
	Textbox txtFilRec;//
	Textbox txtFilMan;//
	Textbox txtFilHorUso;//
	Textbox txtFilPla;//
	Textbox txtFilEstado;//
	MapUsuarioSistema objUsuarioSistema = null;
	
	
	private Listheader lshEditar;
	private Listheader lshNomVehiculo;
	private Listheader lshUsuario;
	private Listheader lshCategoria;
	private Listheader lshGrupo;
	private Listheader lshImei;
	private Listheader lshRecorrido;
	private Listheader lshMant;
	private Listheader lshCerca;
	private Listheader lshHorasUso;
	private Listheader lshPlaca;
	private Listheader lshEstado;
	private Image imgBorrar;
	private Image imgEditar;
	private Image imgTimerForward;
	private Image imgManWalk;
	private Image imgGrupo;
	
	private Fisheye febAgregarVehiculo;
	
	AnnotateDataBinder binder;
	
	
	public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{
	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("winvehiculo",this,true);
	    		
	    		binder = new AnnotateDataBinder(cmp);
	    		getUserFromIndex();
	    		if (objUsuarioSistema==null)
	    			{
	    					desconectar();
	    					return;
	    			}
	    		
	    		cargaComponentes();
	    		//getVehiculo();
	    		obtenerVehiculo();
	    		//fillCmbVehCat();
	    		obtenerCatVeh();
	    		//fillCmbGru();
	    		obtenerGruVeh();
	    		fillCercas();
	    		FillClientes();
	    		
	    		txtFilEstado.setText("A");
	    		filtraVehiculo();
	    		
	    	}
	    	catch(Exception e){
	    		System.out.println("doAfterCompose Exception");
	    		e.printStackTrace();
	    	}
	
	}
	
	public void cargaComponentes()
	{
		
		
		cmbGruVeh.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroGrupo", objUsuarioSistema.getIdioma()));
		cmbCatVeh.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroCategoria", objUsuarioSistema.getIdioma()));
		cmbCer.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroCerca", objUsuarioSistema.getIdioma()));
		cmbCli.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroUsuario", objUsuarioSistema.getIdioma()));
		 
		txtFilNomVeh.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroNombreVehiculo", objUsuarioSistema.getIdioma()));
		txtFilIme.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroImei", objUsuarioSistema.getIdioma()));
		txtFilRec.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroRecorrido", objUsuarioSistema.getIdioma()));
		txtFilMan.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroMantenimiento", objUsuarioSistema.getIdioma()));
		txtFilHorUso.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroHoraUso", objUsuarioSistema.getIdioma()));
		txtFilPla.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroPlaca", objUsuarioSistema.getIdioma()));
		txtFilEstado.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.FiltroEstado", objUsuarioSistema.getIdioma()));
		
		imgBorrar.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Borrar", objUsuarioSistema.getIdioma()));
		imgEditar.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Editar", objUsuarioSistema.getIdioma()));
		imgTimerForward.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.ActualizaKm", objUsuarioSistema.getIdioma()));
		imgManWalk.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.AsignaUsuariosReportar", objUsuarioSistema.getIdioma()));
		imgGrupo.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.VerificaUsuarios", objUsuarioSistema.getIdioma()));
		
		
		lshEditar.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Editar", objUsuarioSistema.getIdioma()));
		lshNomVehiculo.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.NombreVehiculo", objUsuarioSistema.getIdioma()));
		lshUsuario.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Usuario", objUsuarioSistema.getIdioma()));
		lshCategoria.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Categoria", objUsuarioSistema.getIdioma()));
		lshGrupo.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Grupo", objUsuarioSistema.getIdioma()));
		lshImei.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Imei", objUsuarioSistema.getIdioma()));
		lshRecorrido.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Recorrido", objUsuarioSistema.getIdioma()));
		lshMant.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Mantenimiento", objUsuarioSistema.getIdioma()));
		lshCerca.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Cerca", objUsuarioSistema.getIdioma()));
		lshHorasUso.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.HoraUso", objUsuarioSistema.getIdioma()));
		lshPlaca.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Placa", objUsuarioSistema.getIdioma()));
		lshEstado.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Estado", objUsuarioSistema.getIdioma()));
		
		febAgregarVehiculo.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.AgregarVehiculo", objUsuarioSistema.getIdioma()));
			
	}
	
	
	
	public void FillClientes()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		clientes= new ArrayList<Clientes>();
		clientesModel=  new ArrayList<Clientes>();
		
		try{

			clientesModel = dat.FillClientesDao();
			clientes = clientesModel;
			binder.loadComponent(cmbCli);
		
		}
		catch(Exception e){e.printStackTrace();}

	}
	
	
	public void eliminarVehiculo()
	{
		
		int respuesta=Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.DeseaEliminar", objUsuarioSistema.getIdioma()),
				ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion", objUsuarioSistema.getIdioma()),
				Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
		//System.out.println("respuesta: "+respuesta);
		if(respuesta ==16)
		{				
			DatosDao dat = new DatosDao();
			List<Rel_usu_grupo> infoRel = new ArrayList<Rel_usu_grupo>();
			
		
			//if ( objCategoria.getIdentificador() != null )
			if ( objVeh != null )
			{			
			dat.eliminarCoorVehDao(objVehiculo.getVehId());
			dat.eliminarVehDao(objVehiculo.getVehId());
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.RegistroEliminado", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				getUserFromIndex();
				//getVehiculo();
				obtenerVehiculo();
				//fillCmbVehCat();
				obtenerCatVeh();
				//fillCmbGru();
				obtenerGruVeh();
				fillCercas();
				FillClientes();
				
}
else
{

				
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.DebeSeleccionarVehiculo", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
}
	}	

	}
	
	
	
	
	
	
	public void fillCercas()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		cercas= new ArrayList<cercas>();
		cercasModel=  new ArrayList<cercas>();
		
		try{
			cercasModel = dat.fillCercasDao(objUsuarioSistema);
			cercas = cercasModel;
			binder.loadComponent(cmbCer);
		
		}
		catch(Exception e){e.printStackTrace();}

	}
	
	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	}
	
	public void desconectar()
	{
		
		
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.getCurrent().sendRedirect("index.zul");
		
		
	}
	
	
	public void filtraVehiculo()
	{
		String Grupo="";
		String Categoria="";
		String cerFil="";
		String usuFil="";
		cerFil =(cerca!=null)?cerca.getNombreCerca():"";
		usuFil =(objCli!=null)?objCli.getUsuario():"";
		Categoria =(objCatSelected!=null)?objCatSelected.getDescripcion():"";
		Grupo =(objGruSelected!=null)?objGruSelected.getGr_descripcion():"";
		if(/*!txtFilCat.equals("")|| !txtFilCer.equals("") || !txtFilGru.equals("") ||*/!txtFilHorUso.equals("") || !txtFilIme.equals("")
				|| !txtFilMan.equals("") || !txtFilNomVeh.equals("") || !txtFilPla.equals("") || !txtFilRec.equals("") //|| !txtFilUsu.equals("")
				|| !txtFilEstado.equals("")
				|| objCatSelected!=null || objGruSelected!=null || objCli!=null || cerca!=null)
		{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
			veh = new ArrayList<Vehiculos>();
			
			for(int i=0; i< vehModel.size();i++)
			{
				if(vehModel.get(i).getNombre().toLowerCase().contains(txtFilNomVeh.getText().toLowerCase()) &&
						//vehModel.get(i).getUsuario().toLowerCase().contains(txtFilUsu.getText().toLowerCase()) &&
						vehModel.get(i).getUsuario().toLowerCase().contains(usuFil.toLowerCase()) &&
						//vehModel.get(i).getCategoria().toLowerCase().contains(txtFilCat.getText().toLowerCase())&&
						vehModel.get(i).getCategoria().toLowerCase().contains(Categoria.toLowerCase())&&
						//vehModel.get(i).getGrupo().toLowerCase().contains(txtFilGru.getText().toLowerCase()) &&
						vehModel.get(i).getGrupo().toLowerCase().contains(Grupo.toLowerCase()) &&
						vehModel.get(i).getImei().toLowerCase().contains(txtFilIme.getText().toLowerCase())&&
						String.valueOf(vehModel.get(i).getRecorrido()).toLowerCase().contains(txtFilRec.getText().toLowerCase())&&
						vehModel.get(i).getMantenimiento().toLowerCase().contains(txtFilMan.getText().toLowerCase()) &&
						//vehModel.get(i).getCercaNombre().toLowerCase().contains(txtFilCer.getText().toLowerCase()) &&
						vehModel.get(i).getCercaNombre().toLowerCase().contains(cerFil.toLowerCase()) &&
						String.valueOf(vehModel.get(i).getVe_horas_encendido()).toLowerCase().contains(txtFilHorUso.getText().toLowerCase())&&
						vehModel.get(i).getPlaca().toLowerCase().contains(txtFilPla.getText().toLowerCase())&&
						vehModel.get(i).getEstado().toLowerCase().contains(txtFilEstado.getText().toLowerCase())
						)	
					veh.add(vehModel.get(i));
			}
			
			binder.loadComponent(listveh);
			
		}
	}
	
	
	public void obtenerGruVeh(){
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		gruVeh= new ArrayList<Grupo>();
		gruVehModel= new ArrayList<Grupo>();
		
		try{
			
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			gruVehModel = dat.obtenerGruVehDao();
			
			gruVeh = gruVehModel;
			binder.loadComponent(cmbGruVeh);
		
		}
		catch(Exception e){e.printStackTrace();}
		
		
	}
	
	//gruVeh
	
	public void fillCmbGru()
	{
		Grupo cGru= new Grupo();
		gruVeh= new ArrayList<Grupo>();
		
		Statement st_us=null;
		ResultSet rs_us=null;
		int adm=0;
		int usu_id=0;
		
		ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		
		//categoria="0";
		
		
		try{
			
			st=conn.createStatement();
			/*rs=st.executeQuery(   "select id_categoria_vehiculo, descripcion, identificador "+
									" from categoria_vehiculo" );*/
			
			
			
		
			rs= st.executeQuery(" select gr_id id_grupo, gr_descripcion grupo, gr_value valor from grupo;");
			
			/*rs=st.executeQuery( "select c.id_categoria_vehiculo, c.descripcion, c.identificador, u.user user  " +
								"	from categoria_vehiculo c,vehiculos v,usuarios u " +
								"	where c.id_categoria_vehiculo = v.ve_categoria " +
								"	and v.usu_id = u.usu_id ");/* +
								"	and u.user = '"+_user +"' " );*/
			
			
			
			while(rs.next())
			{
				System.out.println("cmb: "+String.valueOf( rs.getInt(1))+" - "+rs.getString(2)+" - "+rs.getString(3));
				
				cGru.setGr_Id(rs.getInt(1));
				cGru.setGr_descripcion(rs.getString(2));
				cGru.setGr_value(rs.getString(3));
					
		
			//if(adm==1 || rs.getInt("usu_id")==usu_id)
				gruVeh.add(cGru);
			
				cGru=new Grupo();
			}
			
			cGru.setGr_Id(0);
			cGru.setGr_descripcion("TODOS");
			cGru.setGr_value("0");
			
			
			gruVeh.add(cGru);
			
			cGru=new Grupo();
			
			//cmbGruVeh.setSelectedText(0, 1, "Escoger Grupo.", true);
			//System.out.println(cmbGruVeh.getSelectedIndex());
			
			
			binder.loadComponent(cmbGruVeh);
			//binder.loadComponent(lisVehiculo );
			
		}catch(Exception e ){e.printStackTrace();}
		
	}
	
	
	public void filtroBusqueda()
	{
		//		System.out.println("txtVehiculo: "+ txtVehiculo.getText());
//		if(( txtVehiculo.getText()==null && dateFecDes.getText()==null && dateFecHas.getText()==null) || (txtVehiculo.getText().equals("")&&dateFecHas.getText().equals("")&&dateFecDes.getText().equals(""))) 
		if( /*cmbCatVeh.getText()==null  || cmbCatVeh.getText().equals("")||  cmbGruVeh.getText()==null  || cmbGruVeh.getText().equals("")||*/ objCatSelected==null && objGruSelected==null)
		{
			Messagebox.show("Debe seleccionar llenar minimo 1 filtro de busqueda",
					"Atención!!!",
					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);}
		else
		{
			id_categoria =( objCatSelected==null)?0:objCatSelected.getId_categoria() ;
						//id_categoria = objCatSelected.getId_categoria();
			
			id_grupo = ( objGruSelected==null)?0:objGruSelected.getGr_Id() ;
						//id_grupo = objGruSelected.getGr_Id();
			
			//nombreVehiculo=(cmbVeh.getText() ==null||cmbVeh.getText().equals(""))?"TOD":cmbVeh.getText() ;
			getVehiculo();
		}
	
		
		
		/*
		try{
			if()
			
		}catch(InterruptedException e){e.printStackTrace();}*/
		
		
	}
	
	
	public void cargarDataModal(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		//perfil = per.buscarPerfPorUsuario(_user);
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    				|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		{
       		//lista doblemente enlazada
    		Map<String,Vehiculos> maVehiculo = new HashMap<String,Vehiculos>();
    		maVehiculo.put("pVehiculo", objVehiculo);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroVehiculo.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objVehiculo=null;
    		//getVehiculo();
    		obtenerVehiculo();
    		//fillCmbVehCat();
    		obtenerCatVeh();
    		//fillCmbGru();
    		obtenerGruVeh();
    		//binder.loadComponent(listveh);
    		filtraVehiculo();
    		}
    		else
    			Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),
    					ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion", objUsuarioSistema.getIdioma()),
    					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	public void cargarDataModal5(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		//perfil = per.buscarPerfPorUsuario(_user);
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    				|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		{
    			objVehiculo=null;
       		//lista doblemente enlazada
    		Map<String,Vehiculos> maVehiculo = new HashMap<String,Vehiculos>();
    		maVehiculo.put("pVehiculo", objVehiculo);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"RegistroVehiculo.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objVehiculo=null;
    		//getVehiculo();
    		obtenerVehiculo();
    		//fillCmbVehCat();
    		obtenerCatVeh();
    		//fillCmbGru();
    		obtenerGruVeh();
    		//binder.loadComponent(listveh);
    		}
    		else
    			Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),
    					ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion", objUsuarioSistema.getIdioma()),
    					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	public void cargarDataModal2(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		//if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    		//		|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		//{
       		//lista doblemente enlazada
    		Map<String,Vehiculos> maVehiculo = new HashMap<String,Vehiculos>();
    		maVehiculo.put("pVehiculo", objVehiculo);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"ActualizaKilometraje.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objVehiculo=null;
    		obtenerVehiculo();
    		obtenerCatVeh();
    		obtenerGruVeh();
    		//}else Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	
	public void cargarDataModal3(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		//if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    		//		|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		//{
       		//lista doblemente enlazada
    		Map<String,Vehiculos> maVehiculo = new HashMap<String,Vehiculos>();
    		maVehiculo.put("pVehiculo", objVehiculo);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"vehiculoReportaUsuario.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objVehiculo=null;
    		obtenerVehiculo();
    		obtenerCatVeh();
    		obtenerGruVeh();
    		//}else Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
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
    		
    		
    		//if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    		//		|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		//{
       		//lista doblemente enlazada
    		Map<String,Vehiculos> maVehiculo = new HashMap<String,Vehiculos>();
    		maVehiculo.put("pVehiculo", objVehiculo);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"usuariosReportados.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objVehiculo=null;
    		obtenerVehiculo();
    		obtenerCatVeh();
    		obtenerGruVeh();
    		//}else Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	public void obtenerCatVeh()
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
			binder.loadComponent(cmbCatVeh);
		
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	public void fillCmbVehCat()
	{
		CategoriaVehiculo cVeh= new CategoriaVehiculo();
		catVeh= new ArrayList<CategoriaVehiculo>();
		
		Statement st_us=null;
		ResultSet rs_us=null;
		int adm=0;
		int usu_id=0;
		
		ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		//categoria="0";
		
		
		try{
			
			st=conn.createStatement();
			/*rs=st.executeQuery(   "select id_categoria_vehiculo, descripcion, identificador "+
									" from categoria_vehiculo" );*/
			
			/*st_us=conn.createStatement();
			rs_us = st_us.executeQuery("select per_id, usu_id from usuarios where user = '"+ _user+"'");
			
			while(rs_us.next())
			{
				
				adm = rs_us.getInt("per_id");
				usu_id=rs_us.getInt("usu_id");
			}*/
			adm = objUsuarioSistema.getPer_id();
			usu_id=objUsuarioSistema.getUsu_id();
			
			System.out.println("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id  " +
					" from categoria_vehiculo c " +
					" where id_categoria_vehiculo in " +
					"	( select distinct(ve_categoria) " +
					"	  from vehiculos " +
					"	  where (usu_id = "+usu_id+" or 1="+ adm+")	);");
			rs= st.executeQuery("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id  " +
					" from categoria_vehiculo c " +
					" where id_categoria_vehiculo in " +
					"	( select distinct(ve_categoria) " +
					"	  from vehiculos " +
					"	  where (usu_id = "+usu_id+" or 1="+ adm+")	);");
			
			
			
			
			
			while(rs.next())
			{
				//System.out.println("cmb: "+String.valueOf( rs.getInt(1))+" - "+rs.getString(2)+" - "+rs.getString(3));
			cVeh.setId_categoria(rs.getInt(1));	
			cVeh.setDescripcion(rs.getString(2));
			cVeh.setIdentificador(rs.getString(3));
			//if(adm==1 || rs.getInt("usu_id")==usu_id)
			catVeh.add(cVeh);
			
			cVeh=new CategoriaVehiculo();
			}
			
			cVeh.setId_categoria(0);	
			cVeh.setDescripcion("TODOS");
			cVeh.setIdentificador("TOD");
			catVeh.add(cVeh);
			
			cVeh=new CategoriaVehiculo();
			
			//cmbCatVeh.setSelectedText(0, 1, "Escoger Cat.", true);
			//System.out.println(cmbCatVeh.getSelectedIndex());
			
			
			binder.loadComponent(cmbCatVeh);
			//binder.loadComponent(lisVehiculo );
			
			
			
		}catch(Exception e ){e.printStackTrace();}
		
		
		
		
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
			binder.loadComponent(listveh);			
			veh=new ArrayList<Vehiculos>();
		}
		catch(Exception e){e.printStackTrace();}
		
	}
		
	public void getVehiculo()
	{
		
		
		Vehiculos vehiculo= new Vehiculos();
		Statement st=null;
		ResultSet rs=null;
		
		Statement st_id=null;
		ResultSet rs_id=null;
		int per_id=0;
		int usu_id=0;
		
		Connection conn= ConexionUtil.getConnection();
		veh=  new ArrayList<Vehiculos>();
		vehModel=  new ArrayList<Vehiculos>();
		
				
				/*"select veh.ve_nombre, usu.user , cat.descripcion, ve_imagen, id_categoria_vehiculo, usu.usu_Id, veh.ve_Id, veh.ve_imei,veh.ve_recorrido, if(veh.ve_mantenimiento=0,'NO','SI') mantenimiento, cer.ce_nombre  , cer.ce_id "+ 
								" from vehiculos veh,categoria_vehiculo cat, usuarios usu, cercas cer "+
								" where veh.usu_id=usu.usu_id "+
								" and veh.ve_categoria=cat.id_categoria_vehiculo " +
								" and cer.ce_Id= veh.ce_Id ;");*/
		
		try{
			System.out.println("llena categoria");
			/*st_id=conn.createStatement();
			rs_id= st_id.executeQuery("select per_id, usu_id from usuarios where user = '"+_user+"';");
			
			while(rs_id.next())
			{ 
				per_id = rs_id.getInt(1); 
				usu_id = rs_id.getInt(2);
			}*/
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			
			System.out.println("select veh.ve_nombre, usu.user , cat.descripcion, ve_imagen, "+
					"	id_categoria_vehiculo, usu.usu_Id, veh.ve_Id, veh.ve_imei,truncate(veh.ve_recorrido,2), "+ 
					"	if(veh.ve_mantenimiento=0,'NO','SI') mantenimiento, cer.ce_nombre  , cer.ce_id,veh.ve_horas_encendido,"+ 
					"	usu.per_id, veh.Placa, gr_descripcion grupo, gr.gr_id gr_id , VE_ANIO,VE_ALIAS, VE_NOTA  "+
					" from vehiculos veh "+
					" inner join categoria_vehiculo cat on veh.ve_categoria=cat.id_categoria_vehiculo "+
					" inner join usuarios usu on veh.usu_id=usu.usu_id "+ 
					" inner join cercas cer on cer.ce_Id= veh.ce_Id "+ 
					" inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "+usu_id+"   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "+usu_id+
					" where veh.Estado='A' group by veh.ve_id") ;
			
			st=conn.createStatement();
			rs=st.executeQuery("select veh.ve_nombre, usu.user , cat.descripcion, ve_imagen, "+
					"	id_categoria_vehiculo, usu.usu_Id, veh.ve_Id, veh.ve_imei,truncate(veh.ve_recorrido,2), "+ 
					"	if(veh.ve_mantenimiento=0,'NO','SI') mantenimiento, cer.ce_nombre  , cer.ce_id,veh.ve_horas_encendido,"+ 
					"	usu.per_id, veh.Placa, gr_descripcion grupo, gr.gr_id gr_id , VE_ANIO,VE_ALIAS, VE_NOTA  "+
					" from vehiculos veh "+
					" inner join categoria_vehiculo cat on veh.ve_categoria=cat.id_categoria_vehiculo "+
					" inner join usuarios usu on veh.usu_id=usu.usu_id "+ 
					" inner join cercas cer on cer.ce_Id= veh.ce_Id "+ 
					" inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "+usu_id+"   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "+usu_id+
					" where veh.Estado='A' group by veh.ve_id");
			
			
			
			/*"select veh.ve_nombre, usu.user , cat.descripcion, ve_imagen, id_categoria_vehiculo, usu.usu_Id, veh.ve_Id, veh.ve_imei,truncate(veh.ve_recorrido,2), if(veh.ve_mantenimiento=0,'NO','SI') mantenimiento, cer.ce_nombre  , cer.ce_id,veh.ve_horas_encendido "+ 
								" from vehiculos veh,categoria_vehiculo cat, usuarios usu, cercas cer "+
								" where veh.usu_id=usu.usu_id "+
								" and veh.ve_categoria=cat.id_categoria_vehiculo " +
								" and cer.ce_Id= veh.ce_Id ;"*/
											
			while(rs.next())
			{
				vehiculo.setNombre(rs.getString(1));
				vehiculo.setUsuario(rs.getString(2));
				vehiculo.setCategoria(rs.getString(3));
				vehiculo.setCatVeh(rs.getInt(5));
				vehiculo.setIdUsu(rs.getInt(6));
				vehiculo.setVehId(rs.getInt(7));
				vehiculo.setImei(rs.getString(8));
				vehiculo.setRecorrido(rs.getDouble(9));
				vehiculo.setMantenimiento(rs.getString(10));
				vehiculo.setCercaNombre(rs.getString(11));
				vehiculo.setIdCerca(rs.getInt(12));
				vehiculo.setVe_horas_encendido(rs.getDouble(13));
				vehiculo.setPlaca(rs.getString("Placa"));
				vehiculo.setGrupo(rs.getString("grupo"));
				vehiculo.setGr_id(rs.getInt("gr_id"));
				vehiculo.setVe_alias(rs.getString("ve_alias"));
				vehiculo.setVe_anio(rs.getString("ve_anio"));
				vehiculo.setVe_notas(rs.getString("ve_nota"));
				
				
				//if(rs.getString(2).equals(_user) || per_id==1)
				vehModel.add(vehiculo);
				
				vehiculo = new Vehiculos();
				
				
			}
			veh = vehModel;
			binder.loadComponent(listveh);
			
			
			veh=new ArrayList<Vehiculos>();
		}
		catch(Exception e){e.printStackTrace();}
		
	}
	
	
	public Textbox getTxtFilEstado() {
		return txtFilEstado;
	}
	public void setTxtFilEstado(Textbox txtFilEstado) {
		this.txtFilEstado = txtFilEstado;
	}
	public List<CategoriaVehiculo> getCatVeh() {
		return catVeh;
	}
	public void setCatVeh(List<CategoriaVehiculo> catVeh) {
		this.catVeh = catVeh;
	}
	public List<CategoriaVehiculo> getCatVehModel() {
		return catVehModel;
	}
	public void setCatVehModel(List<CategoriaVehiculo> catVehModel) {
		this.catVehModel = catVehModel;
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
	public Clientes getObjCli() {
		return objCli;
	}
	public void setObjCli(Clientes objCli) {
		this.objCli = objCli;
	}
	public cercas getCerca() {
		return cerca;
	}
	public void setCerca(cercas cerca) {
		this.cerca = cerca;
	}
	public Combobox getCmbCli() {
		return cmbCli;
	}
	public void setCmbCli(Combobox cmbCli) {
		this.cmbCli = cmbCli;
	}
	public List<cercas> getCercas() {
		return cercas;
	}
	public void setCercas(List<cercas> cercas) {
		this.cercas = cercas;
	}
	public List<cercas> getCercasModel() {
		return cercasModel;
	}
	public void setCercasModel(List<cercas> cercasModel) {
		this.cercasModel = cercasModel;
	}
	public Combobox getCmbCer() {
		return cmbCer;
	}
	public void setCmbCer(Combobox cmbCer) {
		this.cmbCer = cmbCer;
	}
	public List<Grupo> getGruVehModel() {
		return gruVehModel;
	}
	public void setGruVehModel(List<Grupo> gruVehModel) {
		this.gruVehModel = gruVehModel;
	}

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}
	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}
	public List<Vehiculos> getVehModel() {
		return vehModel;
	}
	public void setVehModel(List<Vehiculos> vehModel) {
		this.vehModel = vehModel;
	}
	public Textbox getTxtFilNomVeh() {
		return txtFilNomVeh;
	}
	public void setTxtFilNomVeh(Textbox txtFilNomVeh) {
		this.txtFilNomVeh = txtFilNomVeh;
	}
	public Textbox getTxtFilUsu() {
		return txtFilUsu;
	}
	public void setTxtFilUsu(Textbox txtFilUsu) {
		this.txtFilUsu = txtFilUsu;
	}
	public Textbox getTxtFilCat() {
		return txtFilCat;
	}
	public void setTxtFilCat(Textbox txtFilCat) {
		this.txtFilCat = txtFilCat;
	}
	public Textbox getTxtFilGru() {
		return txtFilGru;
	}
	public void setTxtFilGru(Textbox txtFilGru) {
		this.txtFilGru = txtFilGru;
	}
	public Textbox getTxtFilIme() {
		return txtFilIme;
	}
	public void setTxtFilIme(Textbox txtFilIme) {
		this.txtFilIme = txtFilIme;
	}
	public Textbox getTxtFilRec() {
		return txtFilRec;
	}
	public void setTxtFilRec(Textbox txtFilRec) {
		this.txtFilRec = txtFilRec;
	}
	public Textbox getTxtFilMan() {
		return txtFilMan;
	}
	public void setTxtFilMan(Textbox txtFilMan) {
		this.txtFilMan = txtFilMan;
	}
	public Textbox getTxtFilCer() {
		return txtFilCer;
	}
	public void setTxtFilCer(Textbox txtFilCer) {
		this.txtFilCer = txtFilCer;
	}
	public Textbox getTxtFilHorUso() {
		return txtFilHorUso;
	}
	public void setTxtFilHorUso(Textbox txtFilHorUso) {
		this.txtFilHorUso = txtFilHorUso;
	}
	public Textbox getTxtFilPla() {
		return txtFilPla;
	}
	public void setTxtFilPla(Textbox txtFilPla) {
		this.txtFilPla = txtFilPla;
	}
	public int getId_grupo() {
		return id_grupo;
	}
	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}
	public Grupo getObjGruSelected() {
		return objGruSelected;
	}
	public void setObjGruSelected(Grupo objGruSelected) {
		this.objGruSelected = objGruSelected;
	}
	public List<Grupo> getGruVeh() {
		return gruVeh;
	}
	public void setGruVeh(List<Grupo> gruVeh) {
		this.gruVeh = gruVeh;
	}
	public Combobox getCmbGruVeh() {
		return cmbGruVeh;
	}
	public void setCmbGruVeh(Combobox cmbGruVeh) {
		this.cmbGruVeh = cmbGruVeh;
	}
	public int getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	public CategoriaVehiculo getObjCatSelected() {
		return objCatSelected;
	}
	public void setObjCatSelected(CategoriaVehiculo objCatSelected) {
		this.objCatSelected = objCatSelected;
	}
	public Combobox getCmbCatVeh() {
		return cmbCatVeh;
	}
	public void setCmbCatVeh(Combobox cmbCatVeh) {
		this.cmbCatVeh = cmbCatVeh;
	}
	public Vehiculos getObjVehiculo() {
		return objVehiculo;
	}
	public void setObjVehiculo(Vehiculos objVehiculo) {
		this.objVehiculo = objVehiculo;
	}
	public List<Vehiculos> getVeh() {
		return veh;
	}
	public void setVeh(List<Vehiculos> veh) {
		this.veh = veh;
	}
	public Listbox getListveh() {
		return listveh;
	}
	public void setListveh(Listbox listveh) {
		this.listveh = listveh;
	}
	public Vehiculos getObjVeh() {
		return objVeh;
	}
	public void setObjVeh(Vehiculos objVeh) {
		this.objVeh = objVeh;
	}
	public Listheader getLshEditar() {
		return lshEditar;
	}
	public void setLshEditar(Listheader lshEditar) {
		this.lshEditar = lshEditar;
	}
	public Listheader getLshNomVehiculo() {
		return lshNomVehiculo;
	}
	public void setLshNomVehiculo(Listheader lshNomVehiculo) {
		this.lshNomVehiculo = lshNomVehiculo;
	}
	public Listheader getLshUsuario() {
		return lshUsuario;
	}
	public void setLshUsuario(Listheader lshUsuario) {
		this.lshUsuario = lshUsuario;
	}
	public Listheader getLshCategoria() {
		return lshCategoria;
	}
	public void setLshCategoria(Listheader lshCategoria) {
		this.lshCategoria = lshCategoria;
	}
	public Listheader getLshGrupo() {
		return lshGrupo;
	}
	public void setLshGrupo(Listheader lshGrupo) {
		this.lshGrupo = lshGrupo;
	}
	public Listheader getLshImei() {
		return lshImei;
	}
	public void setLshImei(Listheader lshImei) {
		this.lshImei = lshImei;
	}
	public Listheader getLshRecorrido() {
		return lshRecorrido;
	}
	public void setLshRecorrido(Listheader lshRecorrido) {
		this.lshRecorrido = lshRecorrido;
	}
	public Listheader getLshMant() {
		return lshMant;
	}
	public void setLshMant(Listheader lshMant) {
		this.lshMant = lshMant;
	}
	public Listheader getLshCerca() {
		return lshCerca;
	}
	public void setLshCerca(Listheader lshCerca) {
		this.lshCerca = lshCerca;
	}
	public Listheader getLshHorasUso() {
		return lshHorasUso;
	}
	public void setLshHorasUso(Listheader lshHorasUso) {
		this.lshHorasUso = lshHorasUso;
	}
	public Listheader getLshPlaca() {
		return lshPlaca;
	}
	public void setLshPlaca(Listheader lshPlaca) {
		this.lshPlaca = lshPlaca;
	}
	public Listheader getLshEstado() {
		return lshEstado;
	}

	public void setLshEstado(Listheader lshEstado) {
		this.lshEstado = lshEstado;
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

	public Image getImgTimerForward() {
		return imgTimerForward;
	}

	public void setImgTimerForward(Image imgTimerForward) {
		this.imgTimerForward = imgTimerForward;
	}

	public Image getImgManWalk() {
		return imgManWalk;
	}

	public void setImgManWalk(Image imgManWalk) {
		this.imgManWalk = imgManWalk;
	}

	public Image getImgGrupo() {
		return imgGrupo;
	}

	public void setImgGrupo(Image imgGrupo) {
		this.imgGrupo = imgGrupo;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}
	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Fisheye getFebAgregarVehiculo() {
		return febAgregarVehiculo;
	}

	public void setFebAgregarVehiculo(Fisheye febAgregarVehiculo) {
		this.febAgregarVehiculo = febAgregarVehiculo;
	}		

}
