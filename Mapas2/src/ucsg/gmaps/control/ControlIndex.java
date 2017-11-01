package ucsg.gmaps.control;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ConexionUtil.EncryptUtils;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;

import com.MD5.MD5;

import ucsg.gmaps.bo.ValidaUsuarioBo;
import ucsg.gmaps.dao.DatosDao;


public class ControlIndex extends GenericForwardComposer {
	
	private Textbox iden;
	private Textbox pass;
	private Button btnIniciar;
	private Window winLogin;
	private Label lblSeleccionIdioma;
	private Label lblIngresarSistema;
	private String token="";
	private String idioma="";
	String idenUser;
	String passUser;
	String _desconectar="S";
	MapUsuarioSistema objUsuarioSistema = null;
	AnnotateDataBinder binder=null;
	private Combobox cmbIdioma;
	

	

	
	
	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winMapGoogle2",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();
    		
    		
    		 token = Executions.getCurrent().getParameter("token");
    		 System.out.println("token: "+token);
    		 if(token!=null)
    		 {
//    			 String string = "004-034556";
//    			 String[] parts = string.split("-");
//    			 String part1 = parts[0]; // 004
//    			 String part2 = parts[1]; // 034556
    			 EncryptUtils encript = new EncryptUtils();
    			 MD5 md5=new MD5();
    			 String string="";
    			 string = encript.desencriptar(token);
    			 String[] parts = string.split("#");
    			 if(parts.length==4 && string.contains("#"))
    			 {
    				 idenUser = parts[0]; 
        			 passUser = md5.digest(parts[1],"MD5");
        			 idioma = parts[2].toLowerCase();
        			 
        			 validaUsuario3();
    			 }
    			 
    			 
    		 }
    		 
    		cmbIdioma.setSelectedIndex(1);
    		btnIniciar.setLabel(ReadPropertiesUtil.obtenerProperty("Ingresar",cmbIdioma.getSelectedItem().getValue().toString()));
    		lblSeleccionIdioma.setValue(ReadPropertiesUtil.obtenerProperty("SeleccionaIdioma",cmbIdioma.getSelectedItem().getValue().toString()));
    		lblIngresarSistema.setValue(ReadPropertiesUtil.obtenerProperty("IngresarSistema",cmbIdioma.getSelectedItem().getValue().toString()));
    		
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
		
	
public void getUserFromIndex()
{
	
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	
	if (objUsuarioSistema!=null)
		Executions.getCurrent().sendRedirect("bienvenido.zul");
		//Executions.sendRedirect("principal.zul");
	
		
	/*
	
	idenUser=(String)Executions.getCurrent().getSession().getAttribute("_user");
	if (idenUser!=null)
		{
		Executions.getCurrent().getSession().setAttribute("_user",idenUser);	
		Executions.sendRedirect("principal.zul");
		
		}*/
		//desconectar();
		
		
	System.out.println("mg3_user: "+idenUser);
	
}




	public void ingresa()
	{
	
	String verify="0";
	
	
		
		try{
			//Map<String, Object> mapResult = 
			
			ValidaUsuarioBo valUser= new ValidaUsuarioBo();
			verify=valUser.VerificaUsuarioBo(idenUser, passUser);
			
			System.out.println("verify: "+verify );
			
			if (verify.equals("1"))
			{_desconectar="N";
			Executions.getCurrent().getSession().setAttribute("_user",idenUser);	
			Executions.sendRedirect("principal.zul");
			
			}
			else
			{
				Messagebox.show("Error en identificacion ó clave","Atencion",Messagebox.OK,Messagebox.INFORMATION);
			}
			}catch(Exception e)
			{e.printStackTrace();}
		
		
	}
	
	public void seleccionaIdioma()
	{
		//System.out.println(ReadPropertiesUtil.obtenerProperty("Ingresar","en"));
		
		
			btnIniciar.setLabel(ReadPropertiesUtil.obtenerProperty("Ingresar",cmbIdioma.getSelectedItem().getValue().toString()));
			lblSeleccionIdioma.setValue(ReadPropertiesUtil.obtenerProperty("SeleccionaIdioma",cmbIdioma.getSelectedItem().getValue().toString()));
			lblIngresarSistema.setValue(ReadPropertiesUtil.obtenerProperty("IngresarSistema",cmbIdioma.getSelectedItem().getValue().toString()));
			winLogin.invalidate();
		
	}
	
	
	public void validaUsuario3()
	{
//		idenUser=null;
//		passUser=null;
//		
//		idenUser=iden.getText();
//		MD5 md5=new MD5();
//		passUser= md5.digest(pass.getText(),"MD5");  //pass.getText();
		
		
		
		DatosDao datos = new DatosDao();
		
		
		Map<String, Object> mapResult = datos.VerificaUsuarioDao(idenUser, passUser);
		objUsuarioSistema = null;
		
		if(mapResult.get("error") == null){
			objUsuarioSistema = (MapUsuarioSistema) mapResult.get("usuarioSistema");		
			
			if(objUsuarioSistema != null){
				
				objUsuarioSistema.setIdioma(idioma);
				System.out.println("idioma seleccionado: "+idioma );
				Executions.getCurrent().getSession().setAttribute("usuario", objUsuarioSistema);
				Executions.getCurrent().getSession().setAttribute("_user",idenUser);
				Executions.sendRedirect("principal.zul");
				//Executions.getCurrent().sendRedirect("FormularioActualizacion.zul");
			}else{
//				Messagebox.show("Usuario o Contraseña incorrectos.","Atencion",Messagebox.OK,Messagebox.INFORMATION);
//				Messagebox.show(ReadPropertiesUtil.obtenerProperty("UsuarioIncorrecto",idioma),
//						ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", idioma),
//						Messagebox.OK,Messagebox.INFORMATION);
			        System.out.println("Executions.getCurrent().getRemoteAddr()+ "+Executions.getCurrent().getRemoteAddr());
				//HttpServletRequest request = null;
				//System.out.println("request.getRemoteAddr(): "+ request.getRemoteAddr());
				limpiar();
			}	
		}else{
			Messagebox.show(mapResult.get("error").toString(),"Atencion",Messagebox.OK,Messagebox.INFORMATION);
			
		}
			
	}
	
	
	public void validaUsuario2()
	{	
		idenUser=null;
		passUser=null;
		
		idenUser=iden.getText();
		MD5 md5=new MD5();
		passUser= md5.digest(pass.getText(),"MD5");  //pass.getText();
		
		
		
		DatosDao datos = new DatosDao();
		
		
		Map<String, Object> mapResult = datos.VerificaUsuarioDao(idenUser, passUser);
		objUsuarioSistema = null;
		
		if(mapResult.get("error") == null){
			objUsuarioSistema = (MapUsuarioSistema) mapResult.get("usuarioSistema");		
			
			if(objUsuarioSistema != null){
				
				objUsuarioSistema.setIdioma(cmbIdioma.getSelectedItem().getValue().toString());
				System.out.println("idioma seleccionado: "+cmbIdioma.getSelectedItem().getValue() );
				Executions.getCurrent().getSession().setAttribute("usuario", objUsuarioSistema);
				Executions.getCurrent().getSession().setAttribute("_user",idenUser);
				Executions.sendRedirect("principal.zul");
				//Executions.getCurrent().sendRedirect("principal.zul", "_blank");
				Clients.evalJavaScript("location.reload()");
				//Executions.getCurrent().sendRedirect("FormularioActualizacion.zul");
			}else{
//				Messagebox.show("Usuario o Contraseña incorrectos.","Atencion",Messagebox.OK,Messagebox.INFORMATION);
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("UsuarioIncorrecto",cmbIdioma.getSelectedItem().getValue().toString()),
						ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", cmbIdioma.getSelectedItem().getValue().toString()),
						Messagebox.OK,Messagebox.INFORMATION);
			        System.out.println("Executions.getCurrent().getRemoteAddr()+ "+Executions.getCurrent().getRemoteAddr());
				//HttpServletRequest request = null;
				//System.out.println("request.getRemoteAddr(): "+ request.getRemoteAddr());
				limpiar();
			}	
		}else{
			Messagebox.show(mapResult.get("error").toString(),"Atencion",Messagebox.OK,Messagebox.INFORMATION);
			
		}
			
	}
	public void limpiar()
	{
		iden.setText("");
		pass.setText("");
		binder.loadComponent(iden);
		binder.loadComponent(pass);
		
	}
	
	public void validaUsuario()
	{
		idenUser=null;
		passUser=null;
		String verify="0";
		idenUser=iden.getText();
		MD5 md5=new MD5();
		passUser= md5.digest(pass.getText(),"MD5");  //pass.getText();
		try{
		ValidaUsuarioBo valUser= new ValidaUsuarioBo();
		verify=valUser.VerificaUsuarioBo(idenUser, passUser);
		
		System.out.println("verify: "+verify );
		
		if (verify.equals("1"))
		{_desconectar="N";
		Executions.getCurrent().getSession().setAttribute("_user",idenUser);	
		Executions.sendRedirect("principal.zul");
		
		}
		else
		{
			Messagebox.show("Error en identificacion ó clave","Atencion",Messagebox.OK,Messagebox.INFORMATION);
		}
		}catch(Exception e)
		{e.printStackTrace();}
		
		}
	
	
	public Combobox getCmbIdioma() {
		return cmbIdioma;
	}


	public void setCmbIdioma(Combobox cmbIdioma) {
		this.cmbIdioma = cmbIdioma;
	}


	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public String getIdenUser() {
		return idenUser;
	}


	public void setIdenUser(String idenUser) {
		this.idenUser = idenUser;
	}


	public String getPassUser() {
		return passUser;
	}


	public void setPassUser(String passUser) {
		this.passUser = passUser;
	}


	public String get_desconectar() {
		return _desconectar;
	}


	public void set_desconectar(String _desconectar) {
		this._desconectar = _desconectar;
	}


	public Textbox getIden() {
		return iden;
	}


	public void setIden(Textbox iden) {
		this.iden = iden;
	}

	public Textbox getPass() {
		return pass;
	}

	public void setPass(Textbox pass) {
		this.pass = pass;
	}
	
	
	public Button getBtnIniciar() {
		return btnIniciar;
	}


	public void setBtnIniciar(Button btnIniciar) {
		this.btnIniciar = btnIniciar;
	}


	public Window getWinLogin() {
		return winLogin;
	}


	public void setWinLogin(Window winLogin) {
		this.winLogin = winLogin;
	}


	public Label getLblSeleccionIdioma() {
		return lblSeleccionIdioma;
	}


	public void setLblSeleccionIdioma(Label lblSeleccionIdioma) {
		this.lblSeleccionIdioma = lblSeleccionIdioma;
	}


	public Label getLblIngresarSistema() {
		return lblIngresarSistema;
	}


	public void setLblIngresarSistema(Label lblIngresarSistema) {
		this.lblIngresarSistema = lblIngresarSistema;
	}


	public static void main(String arg[])
	{
		/*String &Clave=null;
		&Clave = GetEncryptionKey();*/
	
		ControlIndex ci= new ControlIndex();
		ci.validaUsuario();
		
	}
	

}
