package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;





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



import Util.CategoriaVehiculo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;
import ConexionUtil.ConexionUtil;





public class RegistroCategoriaModalControl extends GenericForwardComposer {
		
		
		private Textbox desCat;
		private Textbox ideCat;
		private Textbox txtEstado;
		//private int id_user;
		private Window winMantUser;
		
		private CategoriaVehiculo objCategoria= new CategoriaVehiculo();
		MapUsuarioSistema objUsuarioSistema = null;
		private List<CategoriaVehiculo> catVeh= new ArrayList<CategoriaVehiculo>();
		private List<CategoriaVehiculo> catVehModel= new ArrayList<CategoriaVehiculo>();
		private String estado;
		AnnotateDataBinder binder;
		
		//componentes
		private Caption cptRegistroCategoria;
		private Label lblDescripcion;
		private Label lblIdentificador;
		private Label lblEstado;
		private  Button btnGuardar;
		private  Button btnCancelar;
		private  Button btnEstado;
		
		
		 public void doAfterCompose(Component cmp){  	
		    	
		    	try
		    	{
		    		
		    		
		    		super.doAfterCompose(cmp);
		    		cmp.setAttribute("winMantUser",this,true);
		    		//Ejecución de Ajax
		    		binder = new AnnotateDataBinder(cmp);
		    		getUserFromIndex();
		    		
					if (objUsuarioSistema==null)
					{
							desconectar();
							return;
					}
		    		objCategoria=(CategoriaVehiculo)arg.get("pUsuario");
		    		if(objCategoria!=null)
		    		{btnEstado.setDisabled(false);
		    			System.out.println("modal-->objCategoria:"+ objCategoria.getIdUser());
		    		desCat.setText(objCategoria.getDescripcion());
		    		ideCat.setText(objCategoria.getIdentificador());
		    		
		    		estado=(objCategoria.getEstado().equals("A"))?"ACTIVO":"INACTIVO";
		    		txtEstado.setText(estado);
		    		
		    		binder.loadAll();
		    		}
		    		else
		    		{
		    			btnEstado.setDisabled(true);
		    			txtEstado.setText("ACTIVO");
		    		}
		    		cargarComponentes();
		    		
		    		/*Session sess = HibernateSessionFactory.getSession();
		    		UsuarioBo bo = new UsuarioBo();	
			
		    		//Ejecución de Ajax
		    		binder = new AnnotateDataBinder(cmp);	    		
		    		objUsuario=(FrUsuarios)arg.get("pUsuario");
		    		//System.out.println("La mama de este.");
		    		//System.out.println(objUsuario.getEstado());
		    		if (objUsuario != null){
		    		name.setReadonly(true);	
		    		name.setValue(objUsuario.getIdUsuario());
		    		pwd1.setValue(objUsuario.getIdContrasena());
		    		pwd2.setValue(objUsuario.getIdContrasena());
		    		nombreuser.setValue(objUsuario.getNombre());
		    		apellidouser.setValue(objUsuario.getApellido());
	    	  		binder.loadAll();
		    	}else{
		    		name.setReadonly(false);
		    		name.setFocus(true);
		    		
		    	}*/
		    		
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    	}
		    }
		 
		 
		 public void cargarComponentes()
		 {
			 cptRegistroCategoria.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.RegistroCategoria", objUsuarioSistema.getIdioma()));
			 lblDescripcion.setValue(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Descripcion", objUsuarioSistema.getIdioma()));
			 lblIdentificador.setValue(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Identificador", objUsuarioSistema.getIdioma()));
			 lblEstado.setValue(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Estado", objUsuarioSistema.getIdioma()));
			 
			 btnCancelar.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Cancelar", objUsuarioSistema.getIdioma()));
			 btnEstado.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.ActivarInactivar", objUsuarioSistema.getIdioma()));
			 btnGuardar.setLabel(ReadPropertiesUtil.obtenerProperty("control.CategoriaControl.Guardar", objUsuarioSistema.getIdioma()));
			 
		 }
		 
		 public void desconectar()
		 {
		 	Executions.getCurrent().getSession().removeAttribute("usuario");
		 	Executions.sendRedirect("index.zul");	
		 }

		 public void getUserFromIndex()
			{
				objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");				
			}
		 
		 public void inactivar()
			{
			 String estado="";
			 estado=(objCategoria.getEstado().equals("A"))?"I":"A";
			 DatosDao dat = new DatosDao();
			 dat.activarInactivarCategoriaDao(estado, objCategoria.getId_categoria());
			 winMantUser.detach();
			/*Statement st=null;
			ResultSet rs=null;
			int update=0;
			Connection conn= ConexionUtil.getConnection();
			try {
				st=conn.createStatement();
			
			update= st.executeUpdate(" 	update categoria_vehiculo "+
					" set 	Estado='I' " 
					+" WHERE id_categoria_vehiculo='"+objCategoria.getId_categoria()+"';");
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			
			
			
			
			}
		 
		 
	public void guardarCategoria()
	{
		int catMax=0;
		int update=0;
		boolean flagInsert=false;
		boolean existeCategoria=false; 
		DatosDao dat = new DatosDao();
		int usu_id=0;
		try{
		
			//System.out.println("categoria: "+ desCat.getText());
			//if(desCat.equals(" ")|| ideCat.equals(" "))
				if(desCat.getText().equals("")|| ideCat.getText().equals(""))
			{Messagebox.show("Debe de llenar todos los campos","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);}
		else
		{
			
			
			/*Statement st=null;
			ResultSet rs1=null;
			ResultSet rs=null;
			ResultSet rsInsert=null;
			Connection conn= ConexionUtil.getConnection();
			st=conn.createStatement();*/
						
			if (objCategoria==null  /*objCategoria.getDescripcion()==null && objCategoria.getIdentificador()==null*/){			
				
				
				catVehModel = dat.obtenerCatVehDao(objUsuarioSistema);
				 catVeh = catVehModel;
				
				for (int i=0;i<  catVeh.size()&&!existeCategoria;i++)
				{
					if(catVeh.get(i).getIdentificador().equals(ideCat.getValue()))
						existeCategoria=true;
				}
				
			if(existeCategoria==false){
							
			dat.insertaCategoriaDao(desCat.getValue(), ideCat.getValue(), objUsuarioSistema.getUsu_id());
				Messagebox.show("Registro insertado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			
			winMantUser.detach();
			}
			else
			{
				Messagebox.show("El identificador de categoria ya existe, favor cambiar el identficador","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			}
		}//cierre if(objCategoria==null)
			else
			{
				dat.actualizaCategoriaDao(desCat.getValue(), ideCat.getValue(), objCategoria.getId_categoria());
				Messagebox.show("Registro actualizado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				winMantUser.detach();	
				
			}
		}
	
		
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	public void cerrarVentana(){
		 try{
			 winMantUser.detach();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


public Textbox getTxtEstado() {
		return txtEstado;
	}


	public void setTxtEstado(Textbox txtEstado) {
		this.txtEstado = txtEstado;
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


public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


public Button getBtnEstado() {
		return btnEstado;
	}


	public void setBtnEstado(Button btnEstado) {
		this.btnEstado = btnEstado;
	}



public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


public CategoriaVehiculo getObjCategoria() {
		return objCategoria;
	}


	public void setObjCategoria(CategoriaVehiculo objCategoria) {
		this.objCategoria = objCategoria;
	}


public Textbox getDesCat() {
		return desCat;
	}


	public void setDesCat(Textbox desCat) {
		this.desCat = desCat;
	}


	public Textbox getIdeCat() {
		return ideCat;
	}


	public void setIdeCat(Textbox ideCat) {
		this.ideCat = ideCat;
	}


	public Window getWinMantUser() {
		return winMantUser;
	}


	public void setWinMantUser(Window winMantUser) {
		this.winMantUser = winMantUser;
	}

	public Caption getCptRegistroCategoria() {
		return cptRegistroCategoria;
	}

	public void setCptRegistroCategoria(Caption cptRegistroCategoria) {
		this.cptRegistroCategoria = cptRegistroCategoria;
	}

	public Label getLblDescripcion() {
		return lblDescripcion;
	}

	public void setLblDescripcion(Label lblDescripcion) {
		this.lblDescripcion = lblDescripcion;
	}

	public Label getLblIdentificador() {
		return lblIdentificador;
	}

	public void setLblIdentificador(Label lblIdentificador) {
		this.lblIdentificador = lblIdentificador;
	}

	public Label getLblEstado() {
		return lblEstado;
	}

	public void setLblEstado(Label lblEstado) {
		this.lblEstado = lblEstado;
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

	
	
}
