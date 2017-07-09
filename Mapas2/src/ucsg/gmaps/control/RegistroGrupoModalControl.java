package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;

public class RegistroGrupoModalControl extends GenericForwardComposer {
	
	private Textbox desGru;
	private Textbox valGru=null;
	private Grupo objGrupo=new Grupo();
	private Window winRegGru;
	
	private Caption cptRegistroGrupos;
	private Label lblDescripcion;
	private Label lblValor;
	private Button btnGuardar;
	private Button btnCancelar;
	MapUsuarioSistema objUsuarioSistema = null;
	
	AnnotateDataBinder binder;
	
	
	public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{
	    		
	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("winRegGru",this,true);
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		
	    		
	    		getUserFromIndex();
	    		
	    		if (objUsuarioSistema==null)
	    		{
	    				desconectar();
	    				return;
	    		}
	    		cargarComponentes();
	    		
	    		objGrupo=(Grupo)arg.get("pGrupo");
	    		if(objGrupo!=null)
	    		{
	    			//System.out.println("modal-->objCategoria:"+ objGrupo.getIdUser());
	    		desGru.setText(objGrupo.getGr_descripcion());
	    		valGru.setText(String.valueOf(objGrupo.getGr_value()));
	    		//id_user= objCategoria.getIdUser();
	    		valGru=null;
	    		binder.loadAll();
	    		}
	    	
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	
	
	public void cargarComponentes()
	{		
		cptRegistroGrupos.setLabel( ReadPropertiesUtil.obtenerProperty("control.RegistroGrupo.RegistroGrupos", objUsuarioSistema.getIdioma()));
		lblDescripcion.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroGrupo.Descripcion", objUsuarioSistema.getIdioma()));
		lblValor.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroGrupo.Valor", objUsuarioSistema.getIdioma()));
		btnGuardar.setLabel( ReadPropertiesUtil.obtenerProperty("control.RegistroGrupo.Guardar", objUsuarioSistema.getIdioma()));
		btnCancelar.setLabel( ReadPropertiesUtil.obtenerProperty("control.RegistroGrupo.Cancelar", objUsuarioSistema.getIdioma()));
		
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

	
	
	
	public void guardarGrupo()
	{
		boolean flagInsert=false;
		int update=0;
		if(desGru.getText().equals("") || desGru==null || desGru.getText().equals(null)|| valGru.getText().equals("") || valGru==null || valGru.getText().equals(null) )
		{
			Messagebox.show("Debe de llenar todos los campos","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		}
		else
		{
			/*Statement st=null;
			ResultSet rs1=null;
			ResultSet rs=null;
			ResultSet rsInsert=null;*/
			DatosDao dat = new DatosDao();
			
			//Connection conn= ConexionUtil.getConnection();
			if(objGrupo!=null)
			{
				try{
					dat.actualizaGrupoDao(desGru.getText(), valGru.getText(), objGrupo.getGr_Id());
					
				/*st=conn.createStatement();
				update=st.executeUpdate("update grupo set gr_descripcion = '"+desGru.getText() +"', GR_VALUE="+ valGru.getText() +
						" WHERE GR_ID = "+objGrupo.getGr_Id()+";");*/
				winRegGru.detach();	
				
				}catch(Exception e){e.printStackTrace();}
			}
			else{
			
			try{
				
				dat.insertaGrupoDao(desGru.getText(),valGru.getValue());
			/*
			st=conn.createStatement();
			flagInsert = st.execute(
					"insert into grupo( gr_descripcion, gr_value) "+
					"values('"+ desGru.getText()+"',"+valGru.getText()+");");
				if (!flagInsert)*/
					Messagebox.show("Registro insertado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				
				winRegGru.detach();
				valGru=null;
			}catch(Exception e){e.printStackTrace();}
			}
			
		}
		
		
		
		
	}
	
	public void cerrarVentana(){
		 try{
			 winRegGru.detach();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	
	

	
	
	
	 public Caption getCptRegistroGrupos() {
		return cptRegistroGrupos;
	}
	public void setCptRegistroGrupos(Caption cptRegistroGrupos) {
		this.cptRegistroGrupos = cptRegistroGrupos;
	}
	public Label getLblDescripcion() {
		return lblDescripcion;
	}
	public void setLblDescripcion(Label lblDescripcion) {
		this.lblDescripcion = lblDescripcion;
	}
	public Label getLblValor() {
		return lblValor;
	}
	public void setLblValor(Label lblValor) {
		this.lblValor = lblValor;
	}
	public Button getBtnGuardar() {
		return btnGuardar;
	}
	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}
	public Button getBtnCancelar() {
		return btnCancelar;
	}
	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	public Textbox getDesGru() {
		return desGru;
	}
	public void setDesGru(Textbox desGru) {
		this.desGru = desGru;
	}
	public Textbox getValGru() {
		return valGru;
	}
	public void setValGru(Textbox valGru) {
		this.valGru = valGru;
	}
	public Grupo getObjGrupo() {
		return objGrupo;
	}
	public void setObjGrupo(Grupo objGrupo) {
		this.objGrupo = objGrupo;
	}
	public Window getWinRegGru() {
		return winRegGru;
	}
	public void setWinRegGru(Window winRegGru) {
		this.winRegGru = winRegGru;
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
	

}
