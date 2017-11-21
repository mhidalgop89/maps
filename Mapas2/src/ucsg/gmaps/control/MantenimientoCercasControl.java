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
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;
import Util.cercas;

public class MantenimientoCercasControl extends GenericForwardComposer{
	
	List<cercas> infoCerca= new ArrayList<cercas>();
	List<cercas> infoCercaModal= new ArrayList<cercas>();
	
	List<Vehiculos> veh= new ArrayList<Vehiculos>();
	List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	
	List<Clientes> clientes= new ArrayList<Clientes>();
	List<Clientes> clientesModel= new ArrayList<Clientes>();
	Clientes objCli;
	cercas objCerca;
	Listbox listCercas;
	//String _user;
	
	MapUsuarioSistema objUsuarioSistema = null;
	AnnotateDataBinder binder;
	
	//componentes
	private Textbox txtFilNom;
	private Textbox txtFilDes;
	private Textbox txtFilUsu;
	private Textbox txtFilEst;
	private Combobox cmbCli;
	private Listheader lshEditar;
	private Listheader lshNombre;
	private Listheader lshDescripcion;
	private Listheader lshUsuario;
	private Listheader lshEstado;
	private Image imgEliminar;
	private Image imgEditar;
	private Image imgVerVehiculos;
	private Fisheye febAgregarCerca;
	
	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winManCercas",this,true);
    		binder = new AnnotateDataBinder(cmp);
    		//Ejecución de Ajax
    		
    		
    		//_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
    		getUserFromIndex();    
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		
    		getCercas();
    		FillClientes();
    		
    		txtFilEst.setText("A");
    		filtraCerca();
    		    		
    		binder.loadComponent(listCercas);

    		cargarComponentes();
    		binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }


public void cargarComponentes()
{
	
	txtFilNom.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.FiltroNombre", objUsuarioSistema.getIdioma()));
	txtFilDes.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.FiltroDescripcion", objUsuarioSistema.getIdioma()));
	cmbCli.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.FiltroUsuario", objUsuarioSistema.getIdioma()));
	txtFilEst.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.FiltroEstado", objUsuarioSistema.getIdioma()));
	lshEditar.setLabel( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Acciones", objUsuarioSistema.getIdioma()));
	lshNombre.setLabel( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Nombre", objUsuarioSistema.getIdioma()));
	lshDescripcion.setLabel( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Descripcion", objUsuarioSistema.getIdioma()));
	lshUsuario.setLabel( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Usuario", objUsuarioSistema.getIdioma()));
	lshEstado.setLabel( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Estado", objUsuarioSistema.getIdioma()));
	imgEliminar.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Eliminar", objUsuarioSistema.getIdioma()));
	imgEditar.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Editar", objUsuarioSistema.getIdioma()));
	imgVerVehiculos.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.VerVehiculo", objUsuarioSistema.getIdioma()));
	febAgregarCerca.setTooltiptext( ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.AgregarCerca", objUsuarioSistema.getIdioma()));

}

public void desconectar()
{
	Executions.getCurrent().getSession().removeAttribute("usuario");
	Executions.sendRedirect("index.zul");	
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

public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
}
	

public void cargarDataModal(){
	try{
		
		Perfiles perfil = new Perfiles();
		PerfilesDao per= new PerfilesDao();
		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
		
		if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "4.1")&&objCerca==null)
				|| (rpp.buscarPermisosDePerfilporIdPermiso(perfil, "4.2")&&objCerca!=null ))
		{    		
		
   		//lista doblemente enlazada
		Map<String,cercas> maCategoria = new HashMap<String,cercas>();
		maCategoria.put("pCerca",  objCerca );
		
		
		final Window registro = (Window) Executions.createComponents(
				"RegistroMantenimientoCerca.zul",null, maCategoria);
		
		registro.doModal();
		
		txtFilDes.setText("");
		txtFilEst.setText("");
		txtFilNom.setText("");
		//txtFilUsu.setText("");
		
		getCercas();
		objCerca=null;
		}
		else
			Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),
					ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		/*objCategoria=null;
		getCategoria();
		binder.loadComponent(listuser);*/
	}catch(Exception e){
		e.printStackTrace();
	}

}

//cargarDataModal2()
public void cargarDataModal2(){
	try{
		
	 		
		
   		//lista doblemente enlazada
		Map<String,cercas> maCategoria = new HashMap<String,cercas>();
		maCategoria.put("pCerca",  objCerca );
		
		
		final Window registro = (Window) Executions.createComponents(
				"VehiculoCerca.zul",null, maCategoria);
		
		registro.doModal();
		
		txtFilDes.setText("");
		txtFilEst.setText("");
		txtFilNom.setText("");
		//txtFilUsu.setText("");
		
		getCercas();
		
	}catch(Exception e){
		e.printStackTrace();
	}

}

public void filtraCerca()
{
	String usuFil="";
	infoCerca = new ArrayList<cercas>();
	usuFil =(objCli!=null)?objCli.getUsuario():"";
	for(int i=0;i<infoCercaModal.size();i++)
	{
		if(infoCercaModal.get(i).getNombreCerca().toLowerCase().contains(txtFilNom.getText().toLowerCase()) &&
		   infoCercaModal.get(i).getDescripcionCerca().toLowerCase().contains(txtFilDes.getText().toLowerCase())&&
		   //infoCercaModal.get(i).getUsuario().toLowerCase().contains(txtFilUsu.getText().toLowerCase())&&
		   infoCercaModal.get(i).getUsuario().toLowerCase().contains(usuFil.toLowerCase())&&
		   infoCercaModal.get(i).getEstado().toLowerCase().contains(txtFilEst.getText().toLowerCase()))
		   {
				infoCerca.add(infoCercaModal.get(i));
		   }
	}
	binder.loadComponent(listCercas);
	
	
}

public void eliminarCerca()
{
	int respuesta=Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.DeseaEliminar", objUsuarioSistema.getIdioma()),
			ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
			Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
	//System.out.println("respuesta: "+respuesta);
	if(respuesta ==16)
	{				
		
		boolean vehExist=false;
		
		DatosDao dat = new DatosDao();	
		
		if ( objCerca != null )
		{
			veh=  new ArrayList<Vehiculos>();
			vehModel=  new ArrayList<Vehiculos>();
			vehModel = dat.obtieneVehiculoDao(objUsuarioSistema.getUsu_id());
			veh = vehModel;
			
			for(int i=0;i<veh.size()&&!vehExist;i++)
			{
				if(veh.get(i).getIdCerca()==objCerca.getIdCercas())
					vehExist=true;
			}
						
			if(!vehExist)
			{				
				dat = new DatosDao();	
				dat.eliminarCercaDao(objCerca);

				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.RegistroEliminado", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				getCercas();
	    		binder.loadComponent(listCercas);

			}
			else
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.ErrorRegistroEliminado", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			
			
		}
		

		else
		{
		//valida si user existe	
				
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.NoSeleccionCategoria", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		}
}

}


	
	public void getCercas()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		infoCerca =  new ArrayList<cercas>();
		infoCercaModal =  new ArrayList<cercas>();
		
		try{
			infoCercaModal = dat.fillCercasDao(objUsuarioSistema);
			infoCerca = infoCercaModal;
			binder.loadComponent(listCercas);
		
		}
		catch(Exception e){e.printStackTrace();}		
		
		
	}
	
	public void nuevaRuta()
	{Perfiles perfil = new Perfiles();
	PerfilesDao per= new PerfilesDao();
	perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
	RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();
	
	try{
	
	if((rpp.buscarPermisosDePerfilporIdPermiso(perfil, "4")/*&&objCerca==null*/))
	{ 
		
		Executions.sendRedirect("MapaGoogle4.zul");
	}
	else
	Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),
			ReadPropertiesUtil.obtenerProperty("control.MantenimientoCercasControl.Atencion", objUsuarioSistema.getIdioma()),
			Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	
	}catch(Exception e){e.printStackTrace();};
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

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public List<cercas> getInfoCercaModal() {
		return infoCercaModal;
	}

	public void setInfoCercaModal(List<cercas> infoCercaModal) {
		this.infoCercaModal = infoCercaModal;
	}

	public Textbox getTxtFilNom() {
		return txtFilNom;
	}

	public void setTxtFilNom(Textbox txtFilNom) {
		this.txtFilNom = txtFilNom;
	}

	public Textbox getTxtFilDes() {
		return txtFilDes;
	}

	public void setTxtFilDes(Textbox txtFilDes) {
		this.txtFilDes = txtFilDes;
	}

	public Textbox getTxtFilUsu() {
		return txtFilUsu;
	}

	public void setTxtFilUsu(Textbox txtFilUsu) {
		this.txtFilUsu = txtFilUsu;
	}

	public Textbox getTxtFilEst() {
		return txtFilEst;
	}

	public void setTxtFilEst(Textbox txtFilEst) {
		this.txtFilEst = txtFilEst;
	}


	public Listbox getListCercas() {
		return listCercas;
	}

	public void setListCercas(Listbox listCercas) {
		this.listCercas = listCercas;
	}

	public List<cercas> getInfoCerca() {
		return infoCerca;
	}

	public void setInfoCerca(List<cercas> infoCerca) {
		this.infoCerca = infoCerca;
	}

	public cercas getObjCerca() {
		return objCerca;
	}

	public void setObjCerca(cercas objCerca) {
		this.objCerca = objCerca;
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

	public Listheader getLshNombre() {
		return lshNombre;
	}

	public void setLshNombre(Listheader lshNombre) {
		this.lshNombre = lshNombre;
	}

	public Listheader getLshDescripcion() {
		return lshDescripcion;
	}

	public void setLshDescripcion(Listheader lshDescripcion) {
		this.lshDescripcion = lshDescripcion;
	}

	public Listheader getLshUsuario() {
		return lshUsuario;
	}

	public void setLshUsuario(Listheader lshUsuario) {
		this.lshUsuario = lshUsuario;
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

	public Image getImgVerVehiculos() {
		return imgVerVehiculos;
	}

	public void setImgVerVehiculos(Image imgVerVehiculos) {
		this.imgVerVehiculos = imgVerVehiculos;
	}


	public Fisheye getFebAgregarCerca() {
		return febAgregarCerca;
	}


	public void setFebAgregarCerca(Fisheye febAgregarCerca) {
		this.febAgregarCerca = febAgregarCerca;
	}

}
