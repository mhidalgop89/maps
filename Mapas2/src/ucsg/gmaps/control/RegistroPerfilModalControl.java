package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Grupo;
import Util.Perfiles;
import Util.Permisos;

public class RegistroPerfilModalControl extends GenericForwardComposer{

	Perfiles objPerfil = new Perfiles();
	Permisos objPermiso = new Permisos(); 
	List<Permisos> infoPermisoModel=  new ArrayList<Permisos>();
	List<Permisos> infoPermiso=  new ArrayList<Permisos>();
	List<Permisos>infoPermisosDePerfil=  new ArrayList<Permisos>();
	List<Permisos> infoPerfilPermiso=  new ArrayList<Permisos>();
	
	List<Perfiles> infoPer = new ArrayList<Perfiles>();
	List<Perfiles> infoPerModel = new ArrayList<Perfiles>();
	
	boolean checkTodo=true;
	
	List<Permisos> infoSelectedPerm=  new ArrayList<Permisos>();
	List<Permisos> infoSelectedPermModel=  new ArrayList<Permisos>();
	
	Listbox listPermiso;
	//Button btnEstado;
	Textbox txtPer;
	Textbox txtFilPer;
	Combobox cmbEstPer;
	Checkbox chbSeleccionaPermiso;
	Window winMantPer;
	//Connection conn=null;
	AnnotateDataBinder binder;

	public Checkbox getChbSeleccionaPermiso() {
		return chbSeleccionaPermiso;
	}

	public void setChbSeleccionaPermiso(Checkbox chbSeleccionaPermiso) {
		this.chbSeleccionaPermiso = chbSeleccionaPermiso;
	}

	public List<Permisos> getInfoPermisosDePerfil() {
		return infoPermisosDePerfil;
	}

	public void setInfoPermisosDePerfil(List<Permisos> infoPermisosDePerfil) {
		this.infoPermisosDePerfil = infoPermisosDePerfil;
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

	public boolean isCheckTodo() {
		return checkTodo;
	}

	public void setCheckTodo(boolean checkTodo) {
		this.checkTodo = checkTodo;
	}

	public Textbox getTxtFilPer() {
		return txtFilPer;
	}

	public void setTxtFilPer(Textbox txtFilPer) {
		this.txtFilPer = txtFilPer;
	}

	public List<Permisos> getInfoPerfilPermiso() {
		return infoPerfilPermiso;
	}

	public void setInfoPerfilPermiso(List<Permisos> infoPerfilPermiso) {
		this.infoPerfilPermiso = infoPerfilPermiso;
	}

	public Permisos getObjPermiso() {
		return objPermiso;
	}

	public void setObjPermiso(Permisos objPermiso) {
		this.objPermiso = objPermiso;
	}

	public Window getWinMantPer() {
		return winMantPer;
	}

	public void setWinMantPer(Window winMantPer) {
		this.winMantPer = winMantPer;
	}


	public List<Permisos> getInfoSelectedPerm() {
		return infoSelectedPerm;
	}

	public void setInfoSelectedPerm(List<Permisos> infoSelectedPerm) {
		this.infoSelectedPerm = infoSelectedPerm;
	}


	public List<Permisos> getInfoSelectedPermModel() {
		return infoSelectedPermModel;
	}


	public void setInfoSelectedPermModel(List<Permisos> infoSelectedPermModel) {
		this.infoSelectedPermModel = infoSelectedPermModel;
	}


	public Perfiles getObjPerfil() {
		return objPerfil;
	}


	public void setObjPerfil(Perfiles objPerfil) {
		this.objPerfil = objPerfil;
	}


	public List<Permisos> getInfoPermisoModel() {
		return infoPermisoModel;
	}


	public void setInfoPermisoModel(List<Permisos> infoPermisoModel) {
		this.infoPermisoModel = infoPermisoModel;
	}


	public List<Permisos> getInfoPermiso() {
		return infoPermiso;
	}


	public void setInfoPermiso(List<Permisos> infoPermiso) {
		this.infoPermiso = infoPermiso;
	}


	public Listbox getListPermiso() {
		return listPermiso;
	}


	public void setListPermiso(Listbox listPermiso) {
		this.listPermiso = listPermiso;
	}


	public Textbox getTxtPer() {
		return txtPer;
	}


	public void setTxtPer(Textbox txtPer) {
		this.txtPer = txtPer;
	}


	public Combobox getCmbEstPer() {
		return cmbEstPer;
	}


	public void setCmbEstPer(Combobox cmbEstPer) {
		this.cmbEstPer = cmbEstPer;
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
	    		//conn= ConexionUtil.getConnection();
	    		objPerfil=(Perfiles)arg.get("pPerfil");
	    		//if(objPerfil!=null)
	    		//{btnEstado.setDisabled(false);
	    		getPermisos();
	    		for(int i=0;i<infoPermisoModel.size();i++)
    			{
	    			infoPermisosDePerfil.add(infoPermisoModel.get(i));
    			}
	    		if (objPerfil !=null)
	    		{
	    			cmbEstPer.setDisabled(false);
	    			txtPer.setText(objPerfil.getPerNombre());
	    		if (objPerfil.getPerEstado().equals("A"))
	    			cmbEstPer.setSelectedIndex(0);
	    		else
	    			cmbEstPer.setSelectedIndex(1);
	    		
	    		
	    		getSelectedPerm();
	    		infoPermiso = new ArrayList<Permisos>();
	    		for(int i=0;i<infoPermisoModel.size();i++)
	    			{
		    			for(int j=0;j<infoSelectedPermModel.size();j++)
		    			{
		    				if(infoPermisoModel.get(i).getPrmId()==infoSelectedPermModel.get(j).getPrmId())
		    					{
		    						infoPermisoModel.get(i).setSelected(true);
		    					//System.out.println("infoPermisoModel.get(i).getPrmId(): "+infoPermisoModel.get(i).getPrmNombre()+ "i: "+i);
		    					}
		    			
		    			}
		    			if(!infoPermisoModel.get(i).isSelected())
		    				checkTodo=false;
	    			}
	    		
	    			infoPermiso= infoPermisoModel;
	    		chbSeleccionaPermiso.setChecked(checkTodo);
	    		
	    		}
	    		else
	    			{
	    				cmbEstPer.setDisabled(true);
	    				cmbEstPer.setSelectedIndex(0);
	    			}
	    	
	    		
	    		
	    		
	    		
	    		binder.loadAll();

	    		//}else{btnEstado.setDisabled(true);}
	    		
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	 
	 
	
	public void getSelectedPerm()
	 {
		 DatosDao dat = new DatosDao();
		
		infoSelectedPerm=  new ArrayList<Permisos>();
	 	infoSelectedPermModel=  new ArrayList<Permisos>();
		
		try{
			infoSelectedPermModel = dat.obtenerPermisosSeleccionadosDao( objPerfil.getPerId());
			infoSelectedPerm = infoSelectedPermModel;
		}
		catch(Exception e){e.printStackTrace();}
		
		
		
	 	/*
	 	Permisos permiso= new Permisos();
	 	Statement st=null;
	 	ResultSet rs=null;
	 	
	 	infoSelectedPerm=  new ArrayList<Permisos>();
	 	infoSelectedPermModel=  new ArrayList<Permisos>();
	 	
	 	try{
	 		
	 		st=conn.createStatement();

	 			rs=st.executeQuery(" select per.prm_id, prm_nombre, prm_valor " +
	 								" from permisos per, rel_perfil_permiso rel " +
	 								" where per.prm_id = rel.prm_id " +
	 								" and rel.per_id="+ objPerfil.getPerId()+" ;");
	 			
	 	
	 		while(rs.next())
	 		{
	 			permiso.setPrmId(rs.getLong("prm_id"));
	 			permiso.setPrmNombre(rs.getString("prm_nombre"));
	 			permiso.setPrmValor(rs.getString("prm_valor"));
	 			
	 			
	 			infoSelectedPermModel.add(permiso);
	 			permiso=new Permisos();
	 			
	 			
	 		}
	 		infoSelectedPerm = infoSelectedPermModel;
	 		//binder.loadComponent(listPermiso);
	 		
	 		
	 		
	 	}
	 	catch(Exception e){e.printStackTrace();}
	 	*/
	 }
	
	
	
	
	 public void getPermisos()
	 {
		 
		 
		 DatosDao dat = new DatosDao();
			int per_id=0;
			int usu_id=0;
			
			infoPermisoModel=  new ArrayList<Permisos>();
		 	infoPermiso=   new ArrayList<Permisos>();
			
			try{
				
				
				infoPermisoModel = dat.obtenerPermisosDao();
				infoPermiso = infoPermisoModel;
				
			
			}
			catch(Exception e){e.printStackTrace();}
		 
		 /*
	 	Permisos permiso= new Permisos();
	 	Statement st=null;
	 	ResultSet rs=null;
	 	
	 	infoPermisoModel=  new ArrayList<Permisos>();
	 	infoPermiso=   new ArrayList<Permisos>();
	 	
	 	try{
	 		
	 		st=conn.createStatement();

	 			rs=st.executeQuery("  select prm_id, prm_nombre,prm_valor from permisos order by prm_nombre;");
	 			
	 	
	 		while(rs.next())
	 		{
	 			permiso.setPrmId(rs.getLong("prm_id"));
	 			permiso.setPrmNombre(rs.getString("prm_nombre"));
	 			permiso.setPrmValor(rs.getString("prm_valor"));
	 			
	 			
	 			infoPermisoModel.add(permiso);
	 			
	 			
	 			permiso=new Permisos();
	 			
	 			
	 		}
	 		infoPermiso = infoPermisoModel;
	 		//binder.loadComponent(listPermiso);
	 		
	 		
	 		
	 	}
	 	catch(Exception e){e.printStackTrace();}
	 	*/
	 }
	 
	 
	 public void filtraPerfil()
	 {
	 	if(!txtFilPer.equals(""))
	 	{
	 		infoPermiso = new ArrayList<Permisos>();
	 		for(int i=0; i< infoPermisoModel.size();i++)
	 		{
	 			if(infoPermisoModel.get(i).getPrmNombre().toLowerCase().contains(txtFilPer.getText().toLowerCase()) 	)
	 				infoPermiso.add(infoPermisoModel.get(i));
	 		}
	 		
	 		binder.loadComponent(listPermiso);
	 		
	 	}
	 }
	 
	 
	 
	 public void guardarPerfil()
	 {
		 if(txtPer.equals("")|| cmbEstPer.getSelectedIndex()==-1||txtPer.equals(null)||txtPer==null)
			Messagebox.show("Debe de llenar todos los campos","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		 else{
			 if (objPerfil !=null)
	 		{
				 Actualizar();
	 		}
			 else
				 Insertar();
		 }
	 }
	 
	 public void permisoPerfil()
	 {
		 
		 
		 listPermiso.getItems().get(0).isSelected();
		 
		 
		 
		
		 System.out.println("Llega permisoPerfil"+infoPermiso.get(0).isSelected());
		 /*if (infoPermiso.contains(objPermiso));
		 infoPermisoModel.get(infoPermiso.indexOf(objPermiso)).setSelected( !objPermiso.isSelected() );*/
	 if(objPermiso !=null)
	 { 
		 for(int i=0 ;i<infoPermisoModel.size()&& !checkTodo;i++ )
			 if (infoPermiso.get(i).isSelected())
				 checkTodo = true;
		 
		for(int i=0 ;i<infoPermisoModel.size();i++ )
		{
			if(infoPermisoModel.get(i).getPrmId()==objPermiso.getPrmId())
				{
					if (infoPermiso.get(i).isSelected())
						infoPermiso.get(i).setSelected(false);
					else
						infoPermiso.get(i).setSelected(true);
				
				}
				
		}
	
	 }else
	 {
		 for(int i=0 ;i<infoPermisoModel.size()&& !checkTodo;i++ )
			 if (infoPermiso.get(i).isSelected())
				 checkTodo = true;
			 
			
		 
		 if (checkTodo)
			 {
			 for(int i=0 ;i<infoPermisoModel.size()&& checkTodo;i++ )
				 if (!infoPermiso.get(i).isSelected())
					 for(int j=0 ;j<infoPermisoModel.size();j++ )
						 infoPermiso.get(j).setSelected(checkTodo);
			 
			 System.out.println("true");
			 
				/* else
					 for(int j=0 ;j<infoPermisoModel.size();j++ )
						 infoPermiso.get(j).setSelected(!checkTodo);*/
			 }
		 else
		 { for(int j=0 ;j<infoPermisoModel.size();j++ )
				 infoPermiso.get(j).setSelected(!checkTodo);
		 System.out.println("false");
		 }
		 
		 checkTodo = false;
		 
	 }
	 }
	 
	 
	 public void onSelelect()
	 {System.out.println("XP onSelect: "+infoPermisosDePerfil.size()+" - "+objPermiso.getPrmId());
		 
		 for(int i=0;i<infoPermisosDePerfil.size();i++)
		 {
			 if(infoPermisosDePerfil.get(i).getPrmId()== objPermiso.getPrmId())
			 {	System.out.println("infoPermisosDePerfil.get(i).isSelected(): "+infoPermisosDePerfil.get(i).isSelected());
				 infoPermisosDePerfil.get(i).setSelected(!infoPermisosDePerfil.get(i).isSelected());
				 break;
			 }
		 }
	 }
	 
	 public void checkEverything()
	 {
		 boolean notSelected=false;
		 for(int i=0;i<infoPermiso.size()&&!notSelected;i++)
			{
				if(!infoPermiso.get(i).isSelected())
					notSelected=true;
			}
		 
		 if(notSelected)
			 for(int i=0;i<infoPermiso.size();i++)
				 infoPermiso.get(i).setSelected(true);
		 else
			 for(int i=0;i<infoPermiso.size();i++)
				 infoPermiso.get(i).setSelected(false);
		 
		 binder.loadComponent(listPermiso);
	 }
	 
	 public void Insertar()
	 {
		 
			try {
				int perfilMax=0;
				
				boolean existePerfil=false;
				getPerfil();
				for(int i=0;i<infoPer.size()&&!existePerfil;i++)
				{
					if(txtPer.getText().trim().equals(infoPer.get(i).getPerNombre().trim()))
						existePerfil=true;
				}
				
				if(existePerfil)
					Messagebox.show("El nombre de perfil ya existe","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				else
				{
					DatosDao dat = new DatosDao();					
					try{
						perfilMax=dat.insertaPerfilesDao(txtPer.getText(),"A");						
					}
					catch(Exception e){e.printStackTrace();}
							Messagebox.show("Registro insertado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);

						
						/*for(int i=0;i< infoPermiso.size();i++)
						{
							if(listPermiso.getItems().get(i).isSelected())
							{  	
								dat.insertaRelacionPerfilPermisoDao(perfilMax, infoPermiso.get(i).getPrmId());
							}
						}*/
						
						for(int i=0;i<infoPermiso.size();i++)
						{
							if(infoPermiso.get(i).isSelected())
								dat.insertaRelacionPerfilPermisoDao(perfilMax, infoPermiso.get(i).getPrmId());
						}
						
						winMantPer.detach();
						
						
					
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 
	 }
	 
	 public void Actualizar()
	 {
		 
		 try {
			 
			 	DatosDao dat= new DatosDao();
			 	
				boolean existePerfil=false;
				
				
				getPerfil();
				for(int i=0;i<infoPer.size()&&!existePerfil;i++)
				{
					if(txtPer.getText().trim().equals(infoPer.get(i).getPerNombre().trim()) &&  !txtPer.getText().equals(objPerfil.getPerNombre()))
						existePerfil=true;
				}
				if(existePerfil)
					Messagebox.show("El nombre de perfil ya existe","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
				else
				{
						dat.actualizaPerfilDao(txtPer.getText(), ((cmbEstPer.getSelectedIndex()==0)?"A":"I"),  objPerfil.getPerId());
						dat.eliminaRelacionPerfilPermiso(objPerfil.getPerId());
						Messagebox.show("Actualizado con exito!!");
					
						for(int i=0;i<infoPermiso.size();i++)
						{//System.out.println("infoPermisosDePerfil.get(i).isSelected(): "+infoPermiso.get(i).isSelected());
							if(infoPermiso.get(i).isSelected())
								dat.insertaRelacionPerfilPermisoDao((int)objPerfil.getPerId(), infoPermiso.get(i).getPrmId());
						}
						
						winMantPer.detach();

				}
				
				
			} catch (Exception e) {
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
						
			}
			catch(Exception e){e.printStackTrace();}
	 }
	 
	 public void cerrarVentana()
	 {
		 winMantPer.detach();
	 }
	 
}
