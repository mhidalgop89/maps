package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import com.MD5.MD5;

import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Clientes;
import Util.Perfiles;

public class RegistroUsuarioModalControl extends GenericForwardComposer {
	private Textbox nomUsu;
	private Textbox apeUsu;
	private Textbox cedUsu;
	private Textbox maiUsu;
	private Textbox usuUsu;
	private Textbox passUsu;
	private Textbox txtTel;
	private Textbox txtCel;
	private Textbox txtDir;
	private Textbox reTypePass;
	private Textbox txtEstado;
	private Datebox dtbFecNac;
	private Combobox cmbPerfil;
	private Button btnEstado;
	private Perfiles objPerfilSelected=new Perfiles();;
	Window winMantCli;
	String _user="";
	private String estado;
	private List<Perfiles>infoPerfil= new ArrayList<Perfiles>();
	private Clientes objUsuario = new Clientes();
	List<Clientes> usu=  new ArrayList<Clientes>();
	List<Clientes> usuModel =  new ArrayList<Clientes>();
	AnnotateDataBinder binder;
	
	
	
	public Textbox getTxtEstado() {
		return txtEstado;
	}
	public void setTxtEstado(Textbox txtEstado) {
		this.txtEstado = txtEstado;
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
	public Textbox getReTypePass() {
		return reTypePass;
	}
	public void setReTypePass(Textbox reTypePass) {
		this.reTypePass = reTypePass;
	}
	public Textbox getTxtDir() {
		return txtDir;
	}
	public void setTxtDir(Textbox txtDir) {
		this.txtDir = txtDir;
	}
	public Textbox getTxtTel() {
		return txtTel;
	}
	public void setTxtTel(Textbox txtTel) {
		this.txtTel = txtTel;
	}
	public Textbox getTxtCel() {
		return txtCel;
	}
	public void setTxtCel(Textbox txtCel) {
		this.txtCel = txtCel;
	}
	public Datebox getDtbFecNac() {
		return dtbFecNac;
	}
	public void setDtbFecNac(Datebox dtbFecNac) {
		this.dtbFecNac = dtbFecNac;
	}
	public String get_user() {
		return _user;
	}
	public void set_user(String _user) {
		this._user = _user;
	}
	public Button getBtnEstado() {
		return btnEstado;
	}
	public void setBtnEstado(Button btnEstado) {
		this.btnEstado = btnEstado;
	}
	public Perfiles getObjPerfilSelected() {
		return objPerfilSelected;
	}
	public void setObjPerfilSelected(Perfiles objPerfilSelected) {
		this.objPerfilSelected = objPerfilSelected;
	}
	public List<Perfiles> getInfoPerfil() {
		return infoPerfil;
	}
	public void setInfoPerfil(List<Perfiles> infoPerfil) {
		this.infoPerfil = infoPerfil;
	}
	public Combobox getCmbPerfil() {
		return cmbPerfil;
	}
	public void setCmbPerfil(Combobox cmbPerfil) {
		this.cmbPerfil = cmbPerfil;
	}
	public Window getWinMantCli() {
		return winMantCli;
	}
	public void setWinMantCli(Window winMantCli) {
		this.winMantCli = winMantCli;
	}
	public Clientes getObjUsuario() {
		return objUsuario;
	}
	public void setObjUsuario(Clientes objUsuario) {
		this.objUsuario = objUsuario;
	}
	public Textbox getNomUsu() {
		return nomUsu;
	}
	public void setNomUsu(Textbox nomUsu) {
		this.nomUsu = nomUsu;
	}
	public Textbox getApeUsu() {
		return apeUsu;
	}
	public void setApeUsu(Textbox apeUsu) {
		this.apeUsu = apeUsu;
	}
	public Textbox getCedUsu() {
		return cedUsu;
	}
	public void setCedUsu(Textbox cedUsu) {
		this.cedUsu = cedUsu;
	}
	public Textbox getMaiUsu() {
		return maiUsu;
	}
	public void setMaiUsu(Textbox maiUsu) {
		this.maiUsu = maiUsu;
	}
	public Textbox getUsuUsu() {
		return usuUsu;
	}
	public void setUsuUsu(Textbox usuUsu) {
		this.usuUsu = usuUsu;
	}
	public Textbox getPassUsu() {
		return passUsu;
	}
	public void setPassUsu(Textbox passUsu) {
		this.passUsu = passUsu;
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
	    		cmp.setAttribute("winMantCli",this,true);
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		objUsuario  =(Clientes)arg.get("pClientes");
	    		 getUserFromIndex();
	    		if(objUsuario!=null)
	    		{btnEstado.setDisabled(false);
	    		nomUsu.setText(objUsuario.getNombre());
	    		apeUsu.setText(objUsuario.getApellido());
	    		cedUsu.setText(objUsuario.getCedula());
	    		maiUsu.setText(objUsuario.getMail());
	    		usuUsu.setText(objUsuario.getUsuario());
	    		passUsu.setText(objUsuario.getPassword());
	    		reTypePass.setText(objUsuario.getPassword());
	    		cmbPerfil.setText(objUsuario.getPerfil());
	    		objPerfilSelected.setPerId( objUsuario.getPer_id() );
	    		
	    		txtDir.setText(objUsuario.getUsu_direccion());
	    		txtTel.setText(objUsuario.getUsu_telefono());
	    		txtCel.setText(objUsuario.getUsu_celular());
	    		dtbFecNac.setValue((objUsuario.getFecha_nacimiento()==null)?null:objUsuario.getFecha_nacimiento());
	    		estado = (objUsuario.getEstado().equals("A"))?"ACTIVO":"INACTIVO";
	    		txtEstado.setText(estado);
	    		
	    		binder.loadAll();
	    		}
	    		else
	    		{
	    			btnEstado.setDisabled(true);
	    			txtEstado.setText("ACTIVO");
	    		}
	    		
	    		getPerfil();
	    		binder.loadComponent(cmbPerfil);
	    		
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
	 
	 
	 	
		public void getUserFromIndex()
		{
			_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
			if (_user==null)
				Executions.sendRedirect("DesconectaUsuario.zul");
				//desconectar();
				
				
			System.out.println("mg3_user: "+_user);
			
		}
	 
		public void inactivar()
		{
			String estado="";
			estado=(objUsuario.getEstado().equals("A"))?"I":"A";
			DatosDao dat = new DatosDao();
			dat.activarInactivarUsuariosDao(estado, objUsuario.getUsu_id());
			winMantCli.detach();
		/*Statement st=null;
		ResultSet rs=null;
		int update=0;
		Connection conn= ConexionUtil.getConnection();
		try {
			st=conn.createStatement();
		
		update= st.executeUpdate(" 	update usuarios "+
				" set 	Estado='I' " 
				+" where 	user='"+ objUsuario.getUsuario() +  "' ;");
		conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		
		
		
		}
	 public void getPerfil()
	 {
		 infoPerfil = new ArrayList<Perfiles>();
		 DatosDao dat = new DatosDao();
		 infoPerfil = dat.ConsultaPefilDao();
		 
		 /*Statement st=null;
		 ResultSet rs=null;
		 Connection conn= ConexionUtil.getConnection();
		 Perfiles perUser= new Perfiles();
		 
		 
		try{
		st=conn.createStatement();
		rs = st.executeQuery("select per_id, per_nombre, per_estado from perfiles where per_estado = 'A'");
			infoPerfil = new ArrayList<Perfiles>();
		while(rs.next())
		{
			perUser.setPerId(rs.getInt("per_id"));
			perUser.setPerNombre(rs.getString("per_nombre"));
			perUser.setPerEstado(rs.getString("per_estado"));
			
			infoPerfil.add(perUser);
			perUser = new Perfiles();
			
			
		}
		
		}catch(Exception e){e.printStackTrace();} */
		 
	 }
	 
	 public void getUsuario()
	 {
		 DatosDao dat = new DatosDao();
			
			
			usu=  new ArrayList<Clientes>();
			usuModel =  new ArrayList<Clientes>();
			try{
				usuModel = dat.obtieneUsuariosDao();
				usu = usuModel;
				
			
			}
			catch(Exception e){e.printStackTrace();}
		 
	 }

		public void guardarUsuario()
		{
			int usuMax=0;
			int update=0;
			boolean flagInsert=false;
			boolean userExist=false;
			String usuExistente="";
			MD5 md5= new MD5();
			DatosDao dat= new DatosDao();
			
			/*
			 * 	private Textbox apeUsu;
	private Textbox cedUsu;
	private Textbox maiUsu;
	private Textbox usuUsu;
	private Textbox passUsu;
			 * */
			
			try{
				
				
			if( nomUsu.getText().equals("") || cedUsu.getText().equals("")|| maiUsu.getText().equals("") || passUsu.getText().equals("")
					||reTypePass.getText().equals("")
					||cmbPerfil.getSelectedIndex()==-1 || dtbFecNac==null || dtbFecNac.getText().equals("")||usuUsu.getText().equals("") )
			{Messagebox.show("Debe de llenar todos los campos","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);}
			else
			{
				
				
						if(!reTypePass.getText().equals(passUsu.getText()))
						{
							
							Clients.showNotification("No coincide con el password",Clients.NOTIFICATION_TYPE_ERROR, reTypePass, "end_center", 7000);
							
						}
						else{
						
							/*Statement st=null;
							
							ResultSet rs1=null;
							ResultSet rs=null;
							ResultSet rsInsert=null;
							ResultSet rsAct=null;
							Connection conn= ConexionUtil.getConnection();
							st=conn.createStatement();
							System.out.println(objUsuario);*/
							if (objUsuario == null || objUsuario.getApellido()==null )
							{
								
								/*rsInsert=st.executeQuery("select user, password from usuarios where user = '"+ usuUsu.getText() +"';");
								while(rsInsert.next())
								{
									
									userExist=true;
									
									
								}*/
								
								
								getUsuario();
								for(int i=0;i<usu.size()&&!userExist;i++)
									if(usu.get(i).getUsuario().equals(usuUsu.getText()))
										userExist=true;
								
								if(!userExist)
								{	
								/*rs1=st.executeQuery("select max(Usu_id)+1 from usuarios;");
								while(rs1.next())
								{
									
									usuMax = rs1.getInt(1);
									
								}*/
								
								dat.insertaUsuarioDao(nomUsu.getText(), apeUsu.getText(), cedUsu.getText(), maiUsu.getText(), usuUsu.getText(), 
										md5.digest(passUsu.getText(),"MD5"),(int) objPerfilSelected.getPerId(), "A", txtDir.getText(), txtTel.getText(), 
										txtCel.getText(), dtbFecNac.getText());
						
								/*String sql=null;
								sql= 	"insert into usuarios "+
								"( "+
								" Usu_id, "+
								" Usu_nombre, "+
								" Usu_apellido, "+
								" Usu_Cedula, "+
								" Usu_email, "+
								" User, "+
								" Password, "+
								" Per_id, " +
								" Estado,   "+
								" USU_DIRECCION, " +
								" USU_TELEFONO, " +
								" USU_CELULAR, " +
								" USU_FECHA_NACIMIENTO ) "+
								" values "+
								"( "+
								usuMax +" , '"+  //" 4, "+
								nomUsu.getText() +"' , '"+  //" 'nombre', "+
								apeUsu.getText()+"' , '"+  //" 'apellido', "+
								cedUsu.getText() +"' , '"+   //" 'cedula', "+
								maiUsu.getText() +"' , '"+ //" 'mail', "+
								usuUsu.getText() +"' , '"+   //" 'user', "+
								md5.digest(passUsu.getText(),"MD5")+"', "+  //" 'password' "+
								objPerfilSelected.getPerId()+" " +
								" ,'A' " +
								" , '"+ txtDir.getText() +"' " +
								" , '"+ txtTel.getText()+"' " +
								" , '"+ txtCel.getText()+"' " +
								" , '"+dtbFecNac.getText() +"' ) ;";
								System.out.println("llega llena usu: ");
								System.out.println(sql);
								flagInsert = st.execute(
										"insert into usuarios "+
										"( "+
										" Usu_id, "+
										" Usu_nombre, "+
										" Usu_apellido, "+
										" Usu_Cedula, "+
										" Usu_email, "+
										" User, "+
										" Password, "+
										" Per_id, " +
										" Estado,   "+
										" USU_DIRECCION, " +
										" USU_TELEFONO, " +
										" USU_CELULAR, " +
										" USU_FECHA_NACIMIENTO ) "+
										" values "+
										"( "+
										usuMax +" , '"+  //" 4, "+
										nomUsu.getText() +"' , '"+  //" 'nombre', "+
										apeUsu.getText()+"' , '"+  //" 'apellido', "+
										cedUsu.getText() +"' , '"+   //" 'cedula', "+
										maiUsu.getText() +"' , '"+ //" 'mail', "+
										usuUsu.getText() +"' , '"+   //" 'user', "+
										md5.digest(passUsu.getText(),"MD5")+"', "+  //" 'password' "+
										objPerfilSelected.getPerId()+" " +
										" ,'A' " +
										" , '"+ txtDir.getText() +"' " +
										" , '"+ txtTel.getText()+"' " +
										" , '"+ txtCel.getText()+"' " +
										" , '"+dtbFecNac.getText() +"' ) ;"	);
								
			if (!flagInsert)*/
							Messagebox.show("Registro insertado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			
							winMantCli.detach();
			
							}
								else
								{
									Messagebox.show("Usuario ya existe, favor escoger otro nombre de usuario","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
								}
								
							}
							
			
							else
							{
							//valida si user existe	
								
							/*	rsAct=st.executeQuery("select user, password from usuarios where user = '"+ usuUsu.getText() +"';");
							while(rsAct.next())
							{
								//usuExistente= rsAct.getString("");
								userExist=true;
								
								
							}*/
							getUsuario();
							for(int i=0;i<usu.size()&&!userExist;i++)
								if(usu.get(i).getUsuario().equals(usuUsu.getText()))
									userExist=true;
								
								if(!userExist || (userExist && objUsuario.getUsuario().equals(usuUsu.getText()) ))
								{
										//Statement stPass=null;
										//ResultSet rsPass=null;
										String PassExiste="";
										boolean passCam=false;
									
										/*Connection connPass= ConexionUtil.getConnection();
										stPass=connPass.createStatement();
										
										
											
										rsPass=stPass.executeQuery("select password from usuarios where usu_id = "+ objUsuario.getUsu_id() +";");
										System.out.println("select password from usuarios where usu_id = "+ objUsuario.getUsu_id() +";");
											while(rsPass.next())
											{
												PassExiste= rsPass.getString(1);
												System.out.println("rsPass.getString(1): "+rsPass.getString(1)+" PassExiste: "+PassExiste);
												
											}*/
										
											
											for(int i=0;i<usu.size()&&!userExist;i++)
												if(usu.get(i).getUsu_id()==objUsuario.getUsu_id())
													PassExiste=usu.get(i).getPassword();
										
										if(PassExiste.equals(passUsu.getText()))
										
										{
											
											
											dat.actualizaUsuarioDao(nomUsu.getText() , apeUsu.getText(), cedUsu.getText(), maiUsu.getText(), 
													usuUsu.getText(), passUsu.getText(), (int)objPerfilSelected.getPerId(), 
													txtDir.getText(), txtTel.getText(), txtCel.getText(), 
													((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)|| dtbFecNac.getText().equals("") )?null:dtbFecNac.getText()), 
													objUsuario.getUsu_id());
											/*System.out.println("update usuarios set 	" +
												" Usu_nombre= '"+ nomUsu.getText() +"',  " +
												" Usu_apellido= '"+apeUsu.getText()  +"',  " +
												" Usu_Cedula='"+ cedUsu.getText() +"' ,  " +
												" Usu_email='"+maiUsu.getText()+"' ,  " +
												" User = '"+ usuUsu.getText() + "' ,  " +
												//" Password = '"+ md5.digest(passUsu.getText(),"MD5") +"', " +
												" Per_id =	" +objPerfilSelected.getPerId()+" , "+
												" USU_DIRECCION ='"+ txtDir.getText()+"', "+
												" usu_telefono ='"+ txtTel.getText()+"', "+
												" usu_celular ='"+ txtCel.getText()+"', "+//'1900/01/01'
												" USU_FECHA_NACIMIENTO = "+ ((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)|| dtbFecNac==null||dtbFecNac.getText().equals("") )?"null":"'"+dtbFecNac.getText()+"'")+" "+
												" where user =  '"+ objUsuario.getUsuario() +"';  ");
											
											update=st.executeUpdate("update usuarios set 	" +
												" Usu_nombre= '"+ nomUsu.getText() +"',  " +
												" Usu_apellido= '"+apeUsu.getText()  +"',  " +
												" Usu_Cedula='"+ cedUsu.getText() +"' ,  " +
												" Usu_email='"+maiUsu.getText()+"' ,  " +
												" User = '"+ usuUsu.getText() + "' ,  " +
												//" Password = '"+ md5.digest(passUsu.getText(),"MD5") +"', " +
												" Per_id =	" +objPerfilSelected.getPerId()+" , "+
												" USU_DIRECCION ='"+ txtDir.getText()+"', "+
												" usu_telefono ='"+ txtTel.getText()+"', "+
												" usu_celular ='"+ txtCel.getText()+"', "+//'1900/01/01'
												" USU_FECHA_NACIMIENTO = "+ ((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)|| dtbFecNac==null||dtbFecNac.getText().equals("") )?"null":"'"+dtbFecNac.getText()+"'")+" "+
												" where user =  '"+ objUsuario.getUsuario() +"';  ");*/
										Messagebox.show("Registro actualizado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
										winMantCli.detach();
										}
										else
										{
																				
dat.actualizaUsuarioDao(nomUsu.getText() , apeUsu.getText(), cedUsu.getText(), maiUsu.getText(), 
		usuUsu.getText(), md5.digest(passUsu.getText(),"MD5"), (int)objPerfilSelected.getPerId(), 
		txtDir.getText(), txtTel.getText(), txtCel.getText(), 
		((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)||dtbFecNac.getText().equals("") )?null:dtbFecNac.getText()), 
		objUsuario.getUsu_id());
											/*System.out.println("update usuarios set 	" +
												" Usu_nombre= '"+ nomUsu.getText() +"',  " +
												" Usu_apellido= '"+apeUsu.getText()  +"',  " +
												" Usu_Cedula='"+ cedUsu.getText() +"' ,  " +
												" Usu_email='"+maiUsu.getText()+"' ,  " +
												" User = '"+ usuUsu.getText() + "' ,  " +
												" Password = '"+ md5.digest(passUsu.getText(),"MD5") +"', " +
												" Per_id =	" +objPerfilSelected.getPerId()+
												" ,USU_DIRECCION ="+ txtDir.getText()+
												" ,usu_telefono ="+ txtTel.getText()+
												" ,usu_celular ="+ txtCel.getText()+
												" ,USU_FECHA_NACIMIENTO = "+ ((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)|| dtbFecNac==null||dtbFecNac.getText().equals("") )?"null":"'"+dtbFecNac.getText()+"'")+" "+
												" where user =  '"+ objUsuario.getUsuario() +"';  ");
										update=st.executeUpdate("update usuarios set 	" +
												" Usu_nombre= '"+ nomUsu.getText() +"',  " +
												" Usu_apellido= '"+apeUsu.getText()  +"',  " +
												" Usu_Cedula='"+ cedUsu.getText() +"' ,  " +
												" Usu_email='"+maiUsu.getText()+"' ,  " +
												" User = '"+ usuUsu.getText() + "' ,  " +
												" Password = '"+ md5.digest(passUsu.getText(),"MD5") +"', " +
												" Per_id =	" +objPerfilSelected.getPerId()+
												" ,USU_DIRECCION ="+ txtDir.getText()+
												" ,usu_telefono ="+ txtTel.getText()+
												" ,usu_celular ="+ txtCel.getText()+
												" ,USU_FECHA_NACIMIENTO = "+ ((dtbFecNac.getText()==null ||dtbFecNac.getText().equals(null)|| dtbFecNac==null||dtbFecNac.getText().equals("") )?"null":"'"+dtbFecNac.getText()+"'")+" "+
												" where user =  '"+ objUsuario.getUsuario() +"';  ");*/
										Messagebox.show("Registro actualizado correctamente","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
										winMantCli.detach();
										
										}
								}
								else
								{
									Messagebox.show("Usuario ya existe, favor escoger otro nombre de usuario","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
								}
								
			
							}
							
					}
			}
			
			}catch(Exception e){e.printStackTrace();}
			/*int catMax=0;
			int update=0;
			boolean flagInsert=false;
			boolean existeCategoria=false; 
			try{
			if(desCat.equals(null)|| ideCat.equals(null))
				{Messagebox.show("Debe de llenar todos los campos","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);}
			else
			{
				
				
				Statement st=null;
				ResultSet rs1=null;
				ResultSet rs=null;
				ResultSet rsInsert=null;
				Connection conn= ConexionUtil.getConnection();
				st=conn.createStatement();
				if (objCategoria==null){			
						
				
				rs1=st.executeQuery("SELECT MAX(id_categoria_VEHICULO)+1 from categoria_vehiculo; ");
				
				
				while(rs1.next())
				{
					
					catMax = rs1.getInt(1);
					
				}
				
				rs=st.executeQuery("select identificador from categoria_vehiculo;");
				while(rs.next())
				{
					
					if(rs.getString(1).equals(ideCat.getValue()))
						existeCategoria=true;
					
				}		
				System.out.println("existeCategoria==false");
				if(existeCategoria==false){
				System.out.println("ENTRA");
				flagInsert = st.execute(" insert into categoria_vehiculo ( "+
					" id_categoria_VEHICULO, "+
					" descripcion, "+
					" identificador "+
					" ) "+
				"values( "+
				catMax +" , '"+	
				desCat.getValue()+"' , '"+
				ideCat.getValue()+
					"' );"	
					);
				if (flagInsert)
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
					update=st.executeUpdate("update categoria_vehiculo set descripcion='"+desCat.getValue() +
							"' , identificador = '" +ideCat.getValue()+
							"' WHERE IDENTIFICADOR='"+objCategoria.getIdentificador()+"';");
					winMantUser.detach();	
					
				}
			}
		
			
			}
			catch(Exception e){e.printStackTrace();}*/
			
		}
	
		public void cerrarVentana(){
			 try{
				 winMantCli.detach();
				 
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
	

}
