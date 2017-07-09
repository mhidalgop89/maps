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
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.ReadPropertiesUtil;

public class UsuarioGrupoRelControl  extends GenericForwardComposer{

	AnnotateDataBinder binder;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<Clientes> usu= new ArrayList<Clientes>();
	private List<Clientes> usuModel= new ArrayList<Clientes>();
	private Grupo objGrupo=new Grupo();
	//componentes
	private Caption cptUsuarios;
	private Listheader lshNombre;
	private Button btnSalir;
	
	Listbox listcli;
	Window winUsuGruRel;
	

	

	public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{
	    		
	    		
	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("winPerUsu",this,true);
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		objGrupo=(Grupo)arg.get("pGrupo");
	    		
	    		getUserFromIndex();
	    		
	    		if (objUsuarioSistema==null)
	    		{
	    				desconectar();
	    				return;
	    		}
	    		cargarComponentes();
	    		
	    		
	    		getUsuarios();
	    		binder.loadAll();

	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }


	public void cargarComponentes()
	{		
		cptUsuarios.setLabel( ReadPropertiesUtil.obtenerProperty("control.UsuarioGRupoRel.Usuarios", objUsuarioSistema.getIdioma()));
		lshNombre.setLabel(ReadPropertiesUtil.obtenerProperty("control.UsuarioGRupoRel.Nombres", objUsuarioSistema.getIdioma()));
		btnSalir.setLabel( ReadPropertiesUtil.obtenerProperty("control.UsuarioGRupoRel.Salir", objUsuarioSistema.getIdioma()));
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
		
		
		usu=  new ArrayList<Clientes>();
		usuModel =  new ArrayList<Clientes>();
		try{
			usuModel = dat.obtieneUsuariosGruDao(objGrupo.getGr_Id());
		
					
			usu = usuModel;
			binder.loadComponent(listcli);
		
		}
		catch(Exception e){e.printStackTrace();}	
		
	}


	public void cerrarVentana()
	{
		winUsuGruRel.detach();
	}
	
	
	public Window getWinUsuGruRel() {
		return winUsuGruRel;
	}

	public void setWinUsuGruRel(Window winUsuGruRel) {
		this.winUsuGruRel = winUsuGruRel;
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

	public Grupo getObjGrupo() {
		return objGrupo;
	}

	public void setObjGrupo(Grupo objGrupo) {
		this.objGrupo = objGrupo;
	}

	public Listbox getListcli() {
		return listcli;
	}

	public void setListcli(Listbox listcli) {
		this.listcli = listcli;
	}

	public Caption getCptUsuarios() {
		return cptUsuarios;
	}

	public void setCptUsuarios(Caption cptUsuarios) {
		this.cptUsuarios = cptUsuarios;
	}

	public Listheader getLshNombre() {
		return lshNombre;
	}

	public void setLshNombre(Listheader lshNombre) {
		this.lshNombre = lshNombre;
	}

	public Button getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(Button btnSalir) {
		this.btnSalir = btnSalir;
	}
		
		
}
