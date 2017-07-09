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
import Util.Permisos;
import Util.RelPerfilPermiso;

public class RelPerfilPermisoDAO {
	
	 public RelPerfilPermisoDAO()
	 {
		 
	 }

	
	public void  guardarRelPerfilPermiso(RelPerfilPermiso relPerfilPermiso )
	{
		Connection conn= ConexionUtil.getConnection();
		String sql = "INSERT INTO REL_PERFIL_PERMISO (per_id, prm_id) VALUES (? , ?)";
		try{
        PreparedStatement st = conn.prepareStatement(sql);	
		st.setLong(1, relPerfilPermiso.getPerId());
		st.setLong(2, relPerfilPermiso.getPrmId());
		st.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public List<Permisos> buscarPermisosDePerfil(Perfiles perfil)
	{
		List<Permisos> list = new ArrayList<Permisos>();
		Permisos permiso = new Permisos();
		Connection conn= ConexionUtil.getConnection();
		ResultSet rs=null;
		
		String sql = "SELECT p.prm_id,  p.prm_nombre , p.prm_valor FROM rel_perfil_permiso rel , permisos p" +
					" where rel.per_id = ? " +
					" and p.prm_id = rel.prm_id";
		try{
		PreparedStatement st = conn.prepareStatement(sql);
		st.setLong(1, perfil.getPerId());
		rs = st.executeQuery();
		
		while(rs.next()){
			permiso.setPrmId(rs.getLong("prm_id"));
			permiso.setPrmNombre(rs.getString("prm_nombre"));
			permiso.setPrmValor(rs.getString("prm_valor"));
			list.add(permiso);
			permiso= new Permisos();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public boolean buscarPermisosDePerfilporIdPermiso(Perfiles perfil, String prmCod)
	{
		List<Permisos> list = new ArrayList<Permisos>();
		boolean retorno = false;
		Permisos permiso = new Permisos();
		Connection conn= ConexionUtil.getConnection();
		
		ResultSet rs=null;
		Statement st= null;
		System.out.println("perfil.getPerId(): "+perfil.getPerId()+ "-- prmCod: "+prmCod);
		
		String sql = "SELECT p.prm_id,  p.prm_nombre , p.prm_valor FROM rel_perfil_permiso rel , permisos p" +
					" where rel.per_id = " + perfil.getPerId() +
					" and p.prm_id = rel.prm_id and p.prm_valor = '" + prmCod + "'";
		
		System.out.println("sql: "+sql);
		try{
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		//PreparedStatement st = conn.prepareStatement(sql);
		//st.setInt(1,(int) perfil.getPerId());
		//st.setString(2, prmCod);
		//rs = st.executeQuery();
		
			while(rs.next())
			{
				return true;
				
			}
			
		/*if(rs.isClosed())
			retorno = false;
		else
			retorno = true;
		*/
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			System.out.println("finally buscarPermisosDePerfilporIdPermiso");
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return retorno ;
	}

	/*
	public static void main(String args[])
	{
	RelPerfilPermisoDAO per = new RelPerfilPermisoDAO();
	
	
	per.buscarPermisosDePerfilporIdPermiso('2', '6');
		
	}*/
	

}
