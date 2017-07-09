package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
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
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.Vehiculos;

public class CercasControl extends GenericForwardComposer{
	
	
	Listbox listCercas;
	private List<CategoriaVehiculo> catVeh= new ArrayList<CategoriaVehiculo>();
	private List<CategoriaVehiculo> catVehModel= new ArrayList<CategoriaVehiculo>();
	CategoriaVehiculo objCatSelected= new CategoriaVehiculo();
	List<Coordenadas> infoCercas= new ArrayList<Coordenadas>();
	List<Vehiculos>Veh= new ArrayList<Vehiculos>();
	List<Vehiculos>vehModel= new ArrayList<Vehiculos>();
	
	Vehiculos objVehSelected= new Vehiculos();
	//String _user;
	Coordenadas objCercas;
	String fechaDesde="TOD";
	String fechaHasta="TOD";
	String nombreVehiculo="TOD";
	String categoriaVehiculo="TOD";
	Datebox dateFecDes;
	Datebox dateFecHas;
	Textbox txtVehiculo=new Textbox();
	Combobox cmbVeh;
	Combobox cmbCatVeh;
	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	
	
	
	
	public List<CategoriaVehiculo> getCatVehModel() {
		return catVehModel;
	}
	public void setCatVehModel(List<CategoriaVehiculo> catVehModel) {
		this.catVehModel = catVehModel;
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
	public String getCategoriaVehiculo() {
		return categoriaVehiculo;
	}
	public void setCategoriaVehiculo(String categoriaVehiculo) {
		this.categoriaVehiculo = categoriaVehiculo;
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
	public List<CategoriaVehiculo> getCatVeh() {
		return catVeh;
	}
	public void setCatVeh(List<CategoriaVehiculo> catVeh) {
		this.catVeh = catVeh;
	}
	public Vehiculos getObjVehSelected() {
		return objVehSelected;
	}
	public void setObjVehSelected(Vehiculos objVehSelected) {
		this.objVehSelected = objVehSelected;
	}
	public Combobox getCmbVeh() {
		return cmbVeh;
	}
	public void setCmbVeh(Combobox cmbVeh) {
		this.cmbVeh = cmbVeh;
	}
	public List<Vehiculos> getVeh() {
		return Veh;
	}
	public void setVeh(List<Vehiculos> veh) {
		Veh = veh;
	}

	public Listbox getListCercas() {
		return listCercas;
	}
	public void setListCercas(Listbox listCercas) {
		this.listCercas = listCercas;
	}
	public List<Coordenadas> getInfoCercas() {
		return infoCercas;
	}
	public void setInfoCercas(List<Coordenadas> infoCercas) {
		this.infoCercas = infoCercas;
	}
	public Coordenadas getObjCercas() {
		return objCercas;
	}
	public void setObjCercas(Coordenadas objCercas) {
		this.objCercas = objCercas;
	}
	public Datebox getDateFecDes() {
		return dateFecDes;
	}
	public void setDateFecDes(Datebox dateFecDes) {
		this.dateFecDes = dateFecDes;
	}
	public Datebox getDateFecHas() {
		return dateFecHas;
	}
	public void setDateFecHas(Datebox dateFecHas) {
		this.dateFecHas = dateFecHas;
	}
	public Textbox getTxtVehiculo() {
		return txtVehiculo;
	}
	public void setTxtVehiculo(Textbox txtVehiculo) {
		this.txtVehiculo = txtVehiculo;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getNombreVehiculo() {
		return nombreVehiculo;
	}
	public void setNombreVehiculo(String nombreVehiculo) {
		this.nombreVehiculo = nombreVehiculo;
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
	    		cmp.setAttribute("winAlarma",this,true);
	    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		getUserFromIndex();
	    		getCercas();	    		
	    		fillCmbVehCat();
	    		fillCmbVeh();
	    		cmbVeh.setSelectedText(0, 1, "--Escoger Opcion--", true);
	    		cmbCatVeh.setSelectedText(0, 1, "--Escoger Opcion--", true);
	    		//binder.loadComponent(listAlarmas);
	    		
	    		binder.loadAll();
	    	}
	    	catch(Exception e){
	    		System.out.println("doAfterCompose Exception");
	    		e.printStackTrace();
	    	}
	    }
		
	
	
	public void volver()
	{
		Executions.sendRedirect("Reportes.zul");
		
	}
	

	
	public void getUserFromIndex()
	{
		
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
			if (objUsuarioSistema==null)
				Executions.sendRedirect("DesconectaUsuario.zul");
		/*
		_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
		if (_user==null)
			Executions.sendRedirect("DesconectaUsuario.zul");
			//desconectar();
			
			
		System.out.println("mg3_user: "+_user);*/
		
	}
	
	
	public void fillCmbVeh()
	{
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;

		Veh=  new ArrayList<Vehiculos>();
		vehModel=  new ArrayList<Vehiculos>();
		Vehiculos cVeh= new Vehiculos();
		
		try{
			
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			vehModel = dat.obtieneVehiculoDao(usu_id);
			cVeh.setVehId(0);
			cVeh.setNombre("TODOS");
			
			vehModel.add(cVeh);
			Veh = vehModel;			
			binder.loadComponent(cmbVeh);			
			//Veh=new ArrayList<Vehiculos>();
		}
		catch(Exception e){e.printStackTrace();}
		
		/*Vehiculos cVeh= new Vehiculos();
		Veh= new ArrayList<Vehiculos>();
		
		ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		
		try{
			
			st=conn.createStatement();

			if( objCatSelected.getId_categoria() == 0 )
				{rs = st.executeQuery("select  ve_id, ve_nombre from vehiculos  where estado='A';");}
				
			else
			rs = st.executeQuery("select  ve_id, ve_nombre from vehiculos where (ve_categoria = "+objCatSelected.getId_categoria() +" or 'TODOS'='"+cmbCatVeh.getText() +"')  and estado='A';");
			
			while(rs.next())
			{
				
			cVeh.setVehId(rs.getInt(1));
			cVeh.setNombre(rs.getString(2));
			
			
			
			Veh.add(cVeh);
			
			cVeh=new Vehiculos();
			}
			
			cVeh.setVehId(0);
			cVeh.setNombre("TODOS");
			
			Veh.add(cVeh);
			
			cVeh=new Vehiculos();

			
			binder.loadComponent(cmbVeh);
			
			objCatSelected = new CategoriaVehiculo();
			
			
		}catch(Exception e ){e.printStackTrace();}
		*/
		
		
		
	}
	
	
	
	
	
	public void getCercas()
	{
		DatosDao dat = new DatosDao();
		int usu_id=0;
		infoCercas= new ArrayList<Coordenadas>();		
		try{
			usu_id = objUsuarioSistema.getUsu_id();
			infoCercas = dat.obtenerCercasDao(fechaDesde, fechaHasta, nombreVehiculo, categoriaVehiculo);
				
			binder.loadComponent(listCercas);			
		}
		catch(Exception e){e.printStackTrace();}
		
		
		/*ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		Coordenadas coor = new Coordenadas();
		infoCercas= new ArrayList<Coordenadas>();
		try{
		
			st=conn.createStatement();
			System.out.println("getCercas: "+"select c.co_id, v.ve_id,v.ve_nombre,c.co_latitud ,c.co_longitud, c.co_fecha, c.co_comando,cv.descripcion ,@rownum:=@rownum+1 rownum   " +
					" from coordenadas c, vehiculos v,categoria_vehiculo cv,(select @rownum:=0 row1)r " +
					" where c.ve_id=v.ve_id And v.Ve_categoria  = cv.id_categoria_vehiculo and v.estado='A'" +
					" and (c.co_fecha>='"+ fechaDesde +"' or 'TOD'='"+fechaDesde+"') " +
					" and (c.co_fecha<='"+ fechaHasta +"' or 'TOD'='"+fechaHasta+"') " +
					" AND (V.ve_nombre like '%"+ nombreVehiculo +"%' OR 'TOD'='"+nombreVehiculo+"') " +
					" AND  (CV.descripcion  like '%"+ categoriaVehiculo +"%' OR 'TOD'='"+categoriaVehiculo+"')  and c.co_dentroCerca=0 and @rownum  <1000 ");
			rs=st.executeQuery("select c.co_id, v.ve_id,v.ve_nombre,c.co_latitud ,c.co_longitud, c.co_fecha, c.co_comando,cv.descripcion ,@rownum:=@rownum+1 rownum   " +
					" from coordenadas c, vehiculos v,categoria_vehiculo cv,(select @rownum:=0 row1)r " +
					" where c.ve_id=v.ve_id And v.Ve_categoria  = cv.id_categoria_vehiculo and v.estado='A'" +
					" and (c.co_fecha>='"+ fechaDesde +"' or 'TOD'='"+fechaDesde+"') " +
					" and (c.co_fecha<='"+ fechaHasta +"' or 'TOD'='"+fechaHasta+"') " +
					" AND (V.ve_nombre like '%"+ nombreVehiculo +"%' OR 'TOD'='"+nombreVehiculo+"') " +
					" AND  (CV.descripcion  like '%"+ categoriaVehiculo +"%' OR 'TOD'='"+categoriaVehiculo+"')  and c.co_dentroCerca=0 and @rownum  <1000 ");
			
			while(rs.next())
			{
				coor.setIdCoorenada(rs.getInt(1));
				coor.setIdVehiculo(rs.getInt(2));
				coor.setVehiculo(rs.getString(3));
				coor.setLatitud(rs.getDouble(4));
				coor.setLongitud(rs.getDouble(5));
				coor.setFecha(rs.getTimestamp(6));
				coor.setComando(rs.getString(7));
				infoCercas.add(coor);
				coor= new Coordenadas();
			}
			
			//binder.loadComponent(listCercas);
			
			
		}catch(Exception e){e.printStackTrace();}*/
		
		
	}
	
	
	
	
	public void cargarDataModal(){
    	try{
    		
    		Perfiles perfil = new Perfiles();
    		PerfilesDao per= new PerfilesDao();
    		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
    		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
    		
    		
    		/*if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.1")&&objVehiculo ==null)
    				|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "3.2")&&objVehiculo!=null ))
    		{*/
       		//lista doblemente enlazada
    		Map<String,Coordenadas> maVehiculo = new HashMap<String,Coordenadas>();
    		maVehiculo.put("pCoordenadas", objCercas);
    		//System.out.println("objVehiculo.getIdCerca():::: "+objVehiculo.getIdCerca());
    		
    		final Window registro = (Window) Executions.createComponents(
    				"MapaDeAlarma.zul",null, maVehiculo);
    		
    		registro.doModal();
    		objCercas=null;
    		//getVehiculo();
    		//binder.loadComponent(listveh);
    		/*}
    		else
    			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);*/
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	
	
	
	public void limpiarBusqueda()
	{
		txtVehiculo.setText("TODOS");// No Vale XD
		cmbCatVeh.setText("TODOS");
		cmbVeh.setText("TODOS");
		dateFecDes.setText("2012-01-01 00:00:00");
		dateFecHas.setText("2012-01-01 00:00:00");
		binder.loadComponent(dateFecDes);
		binder.loadComponent(dateFecHas);
		binder.loadComponent(txtVehiculo);
		
	}
	

	
	public void filtroBusqueda()
	{
		//		System.out.println("txtVehiculo: "+ txtVehiculo.getText());
//		if(( txtVehiculo.getText()==null && dateFecDes.getText()==null && dateFecHas.getText()==null) || (txtVehiculo.getText().equals("")&&dateFecHas.getText().equals("")&&dateFecDes.getText().equals(""))) 
		if(( cmbVeh.getText()==null && dateFecDes.getText()==null && dateFecHas.getText()==null &&cmbCatVeh.getText()==null) || (cmbVeh.getText().equals("")&&dateFecHas.getText().equals("")&&dateFecDes.getText().equals("") && cmbCatVeh.getText().equals("")))
		{Messagebox.show("Debe seleccionar llenar minimo 1 filtro de busqueda","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);}
		else
		{	
			
		
			fechaDesde=(dateFecDes.getText()==null|| dateFecDes.getText().equals(""))?"TOD":dateFecDes.getText()+" 00:00:00";
			fechaHasta=(dateFecHas.getText()==null||dateFecHas.getText().equals(""))?"TOD":dateFecHas.getText()+" 23:59:59";
			nombreVehiculo=(cmbVeh.getText() ==null||cmbVeh.getText().equals(""))?"TOD":((cmbVeh.getText().equals("TODOS") || cmbVeh.getSelectedIndex()==-1)?"TOD":cmbVeh.getText())/*cmbVeh.getText()*/ ;
			categoriaVehiculo =(cmbCatVeh.getText() ==null||cmbCatVeh.getText().equals(""))?"TOD":((cmbCatVeh.getText().equals("TODOS") || cmbCatVeh.getSelectedIndex()==-1)?"TOD":cmbCatVeh.getText())/*cmbVeh.getText()*/ ;
			getCercas();
			binder.loadComponent(listCercas);
		
		}
		
		
	}

	
	
	public void fillCmbVehCat()
	{
		

		DatosDao dat = new DatosDao();
		CategoriaVehiculo cVeh= new CategoriaVehiculo();
		catVeh=  new ArrayList<CategoriaVehiculo>();
		catVehModel=  new ArrayList<CategoriaVehiculo>();
		
		try{
			
			
			catVehModel = dat.obtenerCatVehDao(objUsuarioSistema);
			cVeh.setId_categoria(0);	
			cVeh.setDescripcion("TODOS");
			cVeh.setIdentificador("TOD");
			catVehModel.add(cVeh);
			catVeh = catVehModel;			
			binder.loadComponent(cmbCatVeh);			

		}
		catch(Exception e){e.printStackTrace();}
		/*CategoriaVehiculo cVeh= new CategoriaVehiculo();
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
			rs= st.executeQuery("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id  " +
					" from categoria_vehiculo c " +
					" where c.estado='A' and id_categoria_vehiculo in " +
					"	( select distinct(ve_categoria) " +
					"	  from vehiculos " +
					"	  where (usu_id = "+objUsuarioSistema.getUsu_id()+" or 1="+ objUsuarioSistema.getPer_id()+")	);");
	
			while(rs.next())
			{
				System.out.println("cmb: "+String.valueOf( rs.getInt(1))+" - "+rs.getString(2)+" - "+rs.getString(3));
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
			
			cmbCatVeh.setSelectedText(0, 1, "Escoger Cat.", true);
			System.out.println(cmbCatVeh.getSelectedIndex());
			
			
			binder.loadComponent(cmbCatVeh);
			//binder.loadComponent(lisVehiculo );
			
			
			
		}catch(Exception e ){e.printStackTrace();}
		*/
		
		
		
	}

}
