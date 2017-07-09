package ucsg.gmaps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConexionUtil.ConexionUtil;
import Util.Perfiles;

public class PerfilesDao {
	
	
	
	 public PerfilesDao()
	 {
		 
	 }
	
	
	public List<Perfiles> listAll()
	{
		Connection conn= ConexionUtil.getConnection();
		List<Perfiles> listall= new ArrayList<Perfiles>();
		Perfiles perfil = new Perfiles();
		Statement st=null;
		ResultSet rs=null;
		String sql = "SELECT * FROM PERFILES";
		
		try{
		st=conn.createStatement();	
		rs = st.executeQuery(sql);
		
		while(rs.next()){
			perfil.setPerId(rs.getLong("per_id"));
			perfil.setPerNombre(rs.getString("per_nombre"));
			perfil.setPerEstado(rs.getString("per_estado"));
			listall.add(perfil);
			perfil= new Perfiles();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return listall;
	}
	
	
	
	public void  guardarPerfil(Perfiles perfil )
	{
		Connection conn= ConexionUtil.getConnection();
		String sql = "INSERT INTO PERFILES(per_nombre, per_estado) VALUES (? , ?)";
		try{
        PreparedStatement st = conn.prepareStatement(sql);	
		st.setString(1, perfil.getPerNombre());
		st.setString(2, perfil.getPerEstado());
		st.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void  modificarPerfil(Perfiles perfil )
	{
		Connection conn= ConexionUtil.getConnection();
		String sql = "UPDATE PERFILES  SET per_nombre = ?, per_estado = ? WHERE per_id = ?";
		
		try{
        PreparedStatement st = conn.prepareStatement(sql);	
		st.setString(1, perfil.getPerNombre());
		st.setString(2, perfil.getPerEstado());
		st.setLong(3, perfil.getPerId());
		st.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public Perfiles buscarPorId(long id)
	{
		
		Perfiles perfil = new Perfiles();
		Connection conn= ConexionUtil.getConnection();
		ResultSet rs=null;
		
		String sql = "SELECT * FROM PERFILES where per_id = ?";
		try{
		PreparedStatement st = conn.prepareStatement(sql);
		st.setLong(1, id);
		rs = st.executeQuery();
		
		while(rs.next()){
			perfil.setPerId(rs.getLong("per_id"));
			perfil.setPerNombre(rs.getString("per_nombre"));
			perfil.setPerEstado(rs.getString("per_estado"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return perfil;
	}
	
	
	
	
	public Perfiles buscarPerfPorUsuario(String user)
	{
		long id=0;
		
		Perfiles perfil = new Perfiles();
		Connection conn= ConexionUtil.getConnection();
		
		Statement stUser=null;
		ResultSet rsUser=null;
		PreparedStatement st =null;
		ResultSet rs=null;
		
		//mh 
		try{
			stUser=conn.createStatement();
			System.out.println("select  per_id,usu_id, user from usuarios where user = '"+user+"' ");
		rsUser = stUser.executeQuery("select  per_id,usu_id, user from usuarios where user = '"+user+"' ");
		
		while(rsUser.next())
		{
			id=rsUser.getInt("per_id");
			
		}
		
		
		}catch(Exception e){e.printStackTrace();}
		
		
		String sql = "SELECT * FROM PERFILES where per_id = ?";
		try{
		 st = conn.prepareStatement(sql);
		st.setLong(1, id);
		rs = st.executeQuery();
		
		while(rs.next()){
			perfil.setPerId(rs.getLong("per_id"));
			perfil.setPerNombre(rs.getString("per_nombre"));
			perfil.setPerEstado(rs.getString("per_estado"));
			System.out.println(rs.getLong("per_id"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			System.out.println("finally");
			try {stUser.close();} catch (SQLException e) {e.printStackTrace();}
			try {rsUser.close();} catch (SQLException e) {e.printStackTrace();}
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return perfil;
	}
	
	public static void main(String args[])
	{
		PerfilesDao pd = new PerfilesDao();
		pd.buscarPerfPorUsuario("nsim");
		
	}
	
	
	
	

}
