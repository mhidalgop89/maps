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
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;



import ConexionUtil.ConexionUtil;
import Util.Clientes;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Rel_usu_grupo;
import Util.Vehiculos;

public class MantenimientoGrupoControl extends GenericForwardComposer {
	AnnotateDataBinder binder;
	Clientes objUsuario;
	Grupo objGrupo;
	List<Grupo>infoGrupo= new ArrayList<Grupo>();
	List<Grupo>infoGrupoModel= new ArrayList<Grupo>();
	List<Rel_usu_grupo> infoRelUsuGru = new ArrayList<Rel_usu_grupo>();
	List<Rel_usu_grupo> infoRelUsuGruModal = new ArrayList<Rel_usu_grupo>();
	private List<Vehiculos> veh= new ArrayList<Vehiculos>();
	private List<Vehiculos> vehModel= new ArrayList<Vehiculos>();
	MapUsuarioSistema objUsuarioSistema = null;
	Textbox txtNom;
	Textbox txtApe;
	Textbox txtFilGru;
	Checkbox chbSeleccionaGrupo;
	boolean checkTodo=true;
	Listbox listGrupo;
	String _user;
	Window winGrupo;
	
	


public void doAfterCompose(Component cmp){  	
    	
		try
		{
			
			
			super.doAfterCompose(cmp);
			cmp.setAttribute("winMantCli",this,true);
			//Ejecución de Ajax
			binder = new AnnotateDataBinder(cmp);
			getUserFromIndex();
    		
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		cargarComponentes();
			
			
			objUsuario  =(Clientes)arg.get("pClientes");
			getGrupo();
			if(objUsuario!=null)
			{
			txtNom.setText(objUsuario.getNombre());
			txtApe.setText(objUsuario.getApellido());
			txtNom.setReadonly(true);
			txtNom.setReadonly(true);
			getSelectedGroup();
			
			for (int i=0;i<infoGrupoModel.size();i++)
			{
				for(int j=0;j<infoRelUsuGruModal.size();j++)
				{
					if(infoGrupoModel.get(i).getGr_Id()== infoRelUsuGruModal.get(j).getGr_id())
						infoGrupo.get(i).setSelected(true);
				}
				if(!infoGrupoModel.get(i).isSelected())
    				checkTodo=false;
			}
			chbSeleccionaGrupo.setChecked(checkTodo);
			
			}/*
			else
			{btnEstado.setDisabled(true);}*/
			
			//getPerfil();
			//binder.loadComponent(cmbPerfil);
			binder.loadAll();
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }


public void cargarComponentes()
{
	

	
	
	
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


public void getSelectedGroup()
{
	DatosDao dat = new DatosDao();
	
	
	infoRelUsuGru = new ArrayList<Rel_usu_grupo>();
	infoRelUsuGruModal = new ArrayList<Rel_usu_grupo>();
	try{
		infoRelUsuGruModal = dat.ConsultaUsuGruPorUsuarioDao(objUsuario);
		infoRelUsuGru = infoRelUsuGruModal;
		
	
	}
	catch(Exception e){e.printStackTrace();}	
	
	/*Rel_usu_grupo rug= new Rel_usu_grupo();
	Statement stGr=null;
	ResultSet rsGr=null;
	int per=0;
	
	Statement st=null;
	ResultSet rs=null;
	Connection conn= ConexionUtil.getConnection();
    infoRelUsuGru = new ArrayList<Rel_usu_grupo>();
    infoRelUsuGruModal = new ArrayList<Rel_usu_grupo>();
    try{
		System.out.println("llena grupo");
		st=conn.createStatement();
		stGr= conn.createStatement();
		
		rs=st.executeQuery("select  id_rel_usu_grupo, usu_id, gr_id from rel_usu_grupo where usu_id = "+objUsuario.getUsu_id() +" ;");

		while(rs.next())
		{
			rug.setId_rel_usu_grupo(rs.getInt("id_rel_usu_grupo"));
			rug.setUsu_id(rs.getInt("usu_id"));
			rug.setGr_id(rs.getInt("gr_id"));
			
			infoRelUsuGruModal.add(rug);
			rug= new Rel_usu_grupo();
			
			
		}
		infoRelUsuGru = infoRelUsuGruModal;
		binder.loadComponent(listGrupo);
		
		
		
	}
	catch(Exception e){e.printStackTrace();}*/
	
	
}

public void checkEverything()
{
	 boolean notSelected=false;
	 for(int i=0;i<infoGrupo.size()&&!notSelected;i++)
		{
			if(!infoGrupo.get(i).isSelected())
				notSelected=true;
		}
	 
	 if(notSelected)
		 for(int i=0;i<infoGrupo.size();i++)
			 infoGrupo.get(i).setSelected(true);
	 else
		 for(int i=0;i<infoGrupo.size();i++)
			 infoGrupo.get(i).setSelected(false);
	 
	 binder.loadComponent(listGrupo);
}

public void getGrupo()
{
	DatosDao dat = new DatosDao();
	
	
	infoGrupo = new ArrayList<Grupo>();
    infoGrupoModel = new ArrayList<Grupo>();
	try{
		infoGrupoModel = dat.obtenerGruVehDao();
		infoGrupo = infoGrupoModel;
		binder.loadComponent(listGrupo);
	
	}
	catch(Exception e){e.printStackTrace();}	
	
	/*Grupo gr= new Grupo();
	Statement stGr=null;
	ResultSet rsGr=null;
	int per=0;
	
	Statement st=null;
	ResultSet rs=null;
	Connection conn= ConexionUtil.getConnection();
    infoGrupo = new ArrayList<Grupo>();
    infoGrupoModel = new ArrayList<Grupo>();
    try{
		System.out.println("llena grupo");
		st=conn.createStatement();
		stGr= conn.createStatement();
		
		rs=st.executeQuery("select gr_id id_grupo, gr_descripcion grupo, gr_value valor from grupo;");

		while(rs.next())
		{
			gr.setGr_Id(rs.getInt("id_grupo"));
			gr.setGr_descripcion(rs.getString("grupo"));
			gr.setGr_value(rs.getInt("valor"));
			
			infoGrupoModel.add(gr);
			gr= new Grupo();
			
			
		}
		infoGrupo = infoGrupoModel;
		binder.loadComponent(listGrupo);
		
		
		
	}
	catch(Exception e){e.printStackTrace();}*/
	
}

public void filtraGrupo()
{
	if(!txtFilGru.equals(""))
	{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		infoGrupo = new ArrayList<Grupo>();
		
		for(int i=0; i< infoGrupoModel.size();i++)
		{
			if(		infoGrupoModel.get(i).getGr_descripcion().toLowerCase().contains(txtFilGru.getText().toLowerCase()))	
				infoGrupo.add(infoGrupoModel.get(i));
		}
		
		binder.loadComponent(listGrupo);
		
	}
}




public void guardarGrupo()
{
	DatosDao dat = new DatosDao();
	dat.eliminaRelUsuarioGrupoDao(objUsuario);
	for(int i=0;i< infoGrupo.size();i++)
	{
		/*if(listGrupo.getItems().get(i).isSelected())
		{ 
			dat.insertaRelUsuarioGrupoDao(objUsuario,infoGrupo.get(i));
		}*/
		
		if(infoGrupo.get(i).isSelected())
			dat.insertaRelUsuarioGrupoDao(objUsuario,infoGrupo.get(i));
		
	}
	
	Messagebox.show("Actualizado con exito","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	
	winGrupo.detach();
	
	
	
	
	/*
	try {
		int perfilMax=0;
		int relPerPrmMax=0;
		boolean existePerfil=false;
		boolean flagInsert=false;
		
		Statement st=null;
		ResultSet rsMaxRelPerPerm=null;
		ResultSet rs1=null;
		ResultSet rs=null;
		ResultSet rsInsert=null;
		boolean rsDelete=false;
		Connection conn= ConexionUtil.getConnection();
		st=conn.createStatement();
		
		
							
				
				rsDelete= st.execute("delete from rel_usu_grupo where usu_id = "+objUsuario.getUsu_id() );	
				
				
				for(int i=0;i< infoGrupo.size();i++)
				{//listPermiso.getItems().get(0).isSelected();
					//if(infoPermiso.get(i).isSelected())
					if(listGrupo.getItems().get(i).isSelected())
					{  
						flagInsert = st.execute(" insert into rel_usu_grupo ( "+
								" usu_id, "+
								" gr_id "+
								" ) "+
							"values( "+
							objUsuario.getUsu_id() +" , "+	
							infoGrupo.get(i).getGr_Id() + " )" 	
								); 
						
						
					}
				}
				
				Messagebox.show("Actualizado con exito","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				
				winGrupo.detach();
				
				
			
			
		
		
		
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}*/
 
	 
}

public void eliminarGrupo()
{
	int respuesta=Messagebox.show("¿Esta seguro que desea eliminar?","Atención!!!",Messagebox.YES|Messagebox.NO,Messagebox.INFORMATION);	
	//System.out.println("respuesta: "+respuesta);
	if(respuesta ==16)
	{				
		DatosDao dat = new DatosDao();
		List<Rel_usu_grupo> infoRel = new ArrayList<Rel_usu_grupo>();
		
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
				vehModel = dat.obtieneVehiculoDao(objUsuarioSistema.getUsu_id());
				veh = vehModel;	
				for(int i=0;i<veh.size()&&!vehExist;i++)
				{
					if(veh.get(i).getGr_id()==objGrupo.getGr_Id())
						vehExist=true;
				}
				
				infoRel = dat.ConsultaUsuGruDao(objGrupo.getGr_Id());	
				if(infoRel.size()>0)
					usuExist=true;
				
				/*rsVeh=st.executeQuery("select * from vehiculos where gr_id = "+ objGrupo.getGr_Id() +";");
			while(rsVeh.next())
			{					
				vehExist=true;
			}
				
				
			rsUsu=st.executeQuery("select * from rel_usu_grupo where gr_id = "+ objGrupo.getGr_Id() +";");
			while(rsUsu.next())
			{					
				usuExist=true;
			}*/
			
			if(!vehExist && !usuExist)
			{	
			dat.eliminarGrupoDao(objGrupo.getGr_Id());
			//flagDelete = st.execute("delete from grupo where gr_id = "+objGrupo.getGr_Id());

			//if (!flagDelete){
				Messagebox.show("Registro Eliminado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				getGrupo();
	    		binder.loadComponent(listGrupo);
				//}
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

public void cerrarVentana()
{
	winGrupo.detach();
}



public boolean isCheckTodo() {
	return checkTodo;
}

public void setCheckTodo(boolean checkTodo) {
	this.checkTodo = checkTodo;
}

public Checkbox getChbSeleccionaGrupo() {
	return chbSeleccionaGrupo;
}

public void setChbSeleccionaGrupo(Checkbox chbSeleccionaGrupo) {
	this.chbSeleccionaGrupo = chbSeleccionaGrupo;
}

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

public Textbox getTxtFilGru() {
	return txtFilGru;
}

public void setTxtFilGru(Textbox txtFilGru) {
	this.txtFilGru = txtFilGru;
}

public Grupo getObjGrupo() {
	return objGrupo;
}

public void setObjGrupo(Grupo objGrupo) {
	this.objGrupo = objGrupo;
}

public List<Rel_usu_grupo> getInfoRelUsuGru() {
	return infoRelUsuGru;
}

public void setInfoRelUsuGru(List<Rel_usu_grupo> infoRelUsuGru) {
	this.infoRelUsuGru = infoRelUsuGru;
}

public List<Rel_usu_grupo> getInfoRelUsuGruModal() {
	return infoRelUsuGruModal;
}

public void setInfoRelUsuGruModal(List<Rel_usu_grupo> infoRelUsuGruModal) {
	this.infoRelUsuGruModal = infoRelUsuGruModal;
}

public Window getWinGrupo() {
	return winGrupo;
}

public void setWinGrupo(Window winGrupo) {
	this.winGrupo = winGrupo;
}

public AnnotateDataBinder getBinder() {
	return binder;
}

public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}

public Clientes getObjUsuario() {
	return objUsuario;
}

public void setObjUsuario(Clientes objUsuario) {
	this.objUsuario = objUsuario;
}

public List<Grupo> getInfoGrupo() {
	return infoGrupo;
}

public void setInfoGrupo(List<Grupo> infoGrupo) {
	this.infoGrupo = infoGrupo;
}

public List<Grupo> getInfoGrupoModel() {
	return infoGrupoModel;
}

public void setInfoGrupoModel(List<Grupo> infoGrupoModel) {
	this.infoGrupoModel = infoGrupoModel;
}

public Textbox getTxtNom() {
	return txtNom;
}

public void setTxtNom(Textbox txtNom) {
	this.txtNom = txtNom;
}

public Textbox getTxtApe() {
	return txtApe;
}

public void setTxtApe(Textbox txtApe) {
	this.txtApe = txtApe;
}

public Listbox getListGrupo() {
	return listGrupo;
}

public void setListGrupo(Listbox listGrupo) {
	this.listGrupo = listGrupo;
}

public String get_user() {
	return _user;
}

public void set_user(String _user) {
	this._user = _user;
}


}
