package ucsg.gmaps.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ConexionUtil.ConexionUtil;

public class ValidaUsuarioDao {

	public String VerificaUsuarioDao( String user, String pass)
	{	
		String exist=null;
		Connection conn= ConexionUtil.getConnection();
		ResultSet rs=null;
		Statement st=null;
		try {
		st=conn.createStatement();
		rs = st.executeQuery("select Fnc_valida_usuario('"+user+"','"+pass+"') ;");
		
		System.out.println("funk ocall 2");
		
		while(rs.next()){
		
		exist=rs.getString(1);
		}
		
		}
		catch(Exception e)
		{e.printStackTrace();}
		System.out.println(exist);
		return exist;
	}
	
	
	public static void main(String args[])
	{
		ValidaUsuarioDao vu=new ValidaUsuarioDao();
		vu.VerificaUsuarioDao("mario", "12345");
		
	}
	
	
}
