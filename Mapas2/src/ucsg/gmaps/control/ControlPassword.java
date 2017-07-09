package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.MD5.MD5;

import ConexionUtil.ConexionUtil;

public class ControlPassword extends GenericForwardComposer{
String _idenUser;
Textbox newPass;
Textbox retypeNewPass;
AnnotateDataBinder binder= new AnnotateDataBinder();	
	
public Textbox getNewPass() {
	return newPass;
}

public void setNewPass(Textbox newPass) {
	this.newPass = newPass;
}

public Textbox getRetypeNewPass() {
	return retypeNewPass;
}

public void setRetypeNewPass(Textbox retypeNewPass) {
	this.retypeNewPass = retypeNewPass;
}

public AnnotateDataBinder getBinder() {
	return binder;
}

public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}

public String get_idenUser() {
	return _idenUser;
}

public void set_idenUser(String user) {
	_idenUser = user;
}

public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winLogin",this,true);
    		GetIdentFromIndex();
    		
    		
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
	
public void GetIdentFromIndex()
{	
	
	_idenUser=(String)Executions.getCurrent().getSession().getAttribute("_user");

	
	
}
	
public boolean validaPassword(String Pass, String RetypePass)
{
boolean ret=false;	
ret=(Pass.equals(RetypePass))?true:false;
return ret;
}

public void guardaPass()
{
	int update=0;
	String md5=null;
	MD5 md = new MD5();
	try{
if(newPass.getText().equals("")|| retypeNewPass.getText().equals(""))
	Messagebox.show("Debe llenar los campos vacios","Atencion",Messagebox.OK,Messagebox.INFORMATION);
else
{
	
	
	if(validaPassword(newPass.getText(), retypeNewPass.getText()))
	{Statement st=null;
	ResultSet rs=null;
	Connection conn= ConexionUtil.getConnection();
	st=conn.createStatement();
	
	md5= md.digest(newPass.getText(), "MD5");
	update = st.executeUpdate("update usuarios set Password = '"+md5+"' where user = '"+_idenUser+"'");
	if (update==1)
	{	Messagebox.show("El Password fue registrado de forma exitosa","Atencion",Messagebox.OK,Messagebox.INFORMATION);
	newPass.setText("");
	retypeNewPass.setText("");	
	binder.loadComponent(newPass);
	binder.loadComponent(retypeNewPass);
	}
	}
	else
		{Messagebox.show("El password reingresado no es igual, Favor volver a ingresar los datos","Atencion",Messagebox.OK,Messagebox.INFORMATION);
		newPass.setText("");
		retypeNewPass.setText("");
		binder.loadComponent(newPass);
		binder.loadComponent(retypeNewPass);
		}
}

	}catch(Exception e){e.printStackTrace();}	

	}


}
