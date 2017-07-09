package ucsg.gmaps.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.CategoriaVehiculo;
import Util.Clientes;
import Util.Coordenadas;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.Permisos;
import Util.Rel_usu_grupo;
import Util.VehiculoUsuario;
import Util.Vehiculos;
import Util.cercas;
import ConexionUtil.ConexionUtil;

public class DatosDao {
	
public List<Coordenadas>  VehiculoSobreMapaDao(int Pv_id_veh){
		
		List<Coordenadas> infoCoorTotales= new ArrayList<Coordenadas>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_VEHICULO_SOBRE_MAPA_PRC(?)}");
				clsCall.setInt(1, Pv_id_veh);
					
				
				clsCall.execute();
				rs = clsCall.getResultSet();

				Coordenadas coorTotal = new Coordenadas();
				infoCoorTotales = new ArrayList<Coordenadas>();
				
				while (rs.next()) {
					coorTotal= new Coordenadas();
					
					
					coorTotal.setLatitud(rs.getDouble("co_latitud"));
					coorTotal.setLongitud(rs.getDouble("co_longitud"));
					coorTotal.setFecha(rs.getTimestamp("co_fecha"));
					coorTotal.setCo_recorrido(rs.getDouble("co_recorrido"));
					coorTotal.setIdVehiculo(rs.getInt("ve_id"));
					
					
					infoCoorTotales.add(coorTotal);
					
				}
				//if(rs != null) rs.close();
				//if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					if(conn!=null)conn.close();
					if(clsCall!=null)clsCall.close();
					if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return infoCoorTotales;
		
	}
	
	
public List<Coordenadas>  TrazaRuta6Dao(int Pv_id_veh ,String Pv_fec_desde , String Pv_fec_hasta ){
		
		List<Coordenadas> infoCoorTotales= new ArrayList<Coordenadas>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_TRAZAR_RUTA_6(?,?,?)}");
				clsCall.setInt(1, Pv_id_veh);
				clsCall.setString(2, Pv_fec_desde);
				clsCall.setString(3, Pv_fec_hasta);		
				
				clsCall.execute();
				rs = clsCall.getResultSet();

				Coordenadas coorTotal = new Coordenadas();
				infoCoorTotales = new ArrayList<Coordenadas>();
				
				while (rs.next()) {
					coorTotal= new Coordenadas();
					
					coorTotal.setIdCoorenada(rs.getInt("co_id"));
					coorTotal.setLatitud(rs.getDouble("co_latitud"));
					coorTotal.setLongitud(rs.getDouble("co_longitud"));
					coorTotal.setVelocidad(rs.getDouble("co_velocidad"));
					coorTotal.setFecha(rs.getTimestamp("co_fecha"));
					coorTotal.setCo_recorrido(rs.getDouble("co_recorrido"));
					
					
					infoCoorTotales.add(coorTotal);
					
				}
			////infoCoor lat Ant TrazaRuta6Dao
				List<Coordenadas> infoCoorFil;// = new ArrayList<Coordenadas>();
				infoCoorFil = new ArrayList<Coordenadas>();
				infoCoorFil= infoCoorTotales;
				double latAnt=0;
				double lngAnt=0;
				
				
				infoCoorTotales = new ArrayList<Coordenadas>();
				for(int i=0;i<infoCoorFil.size();i++)
				{
					if(i==0)
						{
							infoCoorTotales.add(infoCoorFil.get(i));
							latAnt=infoCoorFil.get(i).getLatitud();
							lngAnt=infoCoorFil.get(i).getLongitud();
						}
					else
					{
						if(infoCoorFil.get(i).getLatitud()!=latAnt && infoCoorFil.get(i).getLongitud()!=lngAnt)
							{
								infoCoorTotales.add(infoCoorFil.get(i));
								latAnt=infoCoorFil.get(i).getLatitud();
								lngAnt=infoCoorFil.get(i).getLongitud();
							}
					}
				}
				
				
				/////////////
				
				
				//if(rs != null) rs.close();
				//if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					if(conn!=null)conn.close();
					if(clsCall!=null)clsCall.close();
					if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return infoCoorTotales;
		
	}
	
	
	
	
	
	public Map<String, Object>  VerificaUsuarioDao( String user, String pass)
	{	
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		CallableStatement clsCall = null;
		ResultSet rss = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_VALIDA_USER(?,?)}");
				clsCall.setString(1, user);
				clsCall.setString(2, pass);
				
				clsCall.execute();
				rss = clsCall.getResultSet();
				MapUsuarioSistema objUsuarioSistemaUtil = null;
				while (rss.next()) {
					objUsuarioSistemaUtil = new MapUsuarioSistema();
					objUsuarioSistemaUtil.setUser(user);
					objUsuarioSistemaUtil.setPer_id(rss.getInt("per_id"));
					objUsuarioSistemaUtil.setUsu_id(rss.getInt("usu_id"));
				}
				if(rss != null) rss.close();
				if(clsCall != null) clsCall.close();
				mapReturn.put("error", null);
				mapReturn.put("usuarioSistema", objUsuarioSistemaUtil);
			}else{
				mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
			}
		
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally{
			try {if(conn!=null)conn.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
		}
		
		return mapReturn;
	}
	
		
public void eliminarCategoriaDao(CategoriaVehiculo objCategoria){
		
		
		CallableStatement clsCall = null;
		
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_ELIMINAR_CATEGORIA_PRC(?)}");
				clsCall.setInt(1, objCategoria.getId_categoria());			
				clsCall.execute();
				
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
		try { if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
			
		}
	}



public void eliminarUsuarioDao(int idUsuario){
	
	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINA_USUARIO_PRC(?)}");
			clsCall.setInt(1, idUsuario);			
			clsCall.execute();
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
	try { if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
		
	}
}


public void eliminarRelacionUsuarioGrupoDao(int idUsuario){
	
	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINA_RELACION_USUARIO_GRUPO_PRC(?)}");
			clsCall.setInt(1, idUsuario);			
			clsCall.execute();
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
	try { if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
		
	}
}





public void eliminarCoorVehDao(Integer Pv_ve_id){
	
	
	CallableStatement clsCall = null;
	//ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINAR_DET_VEHICULO(?)}");
			clsCall.setInt(1,Pv_ve_id);			
			clsCall.execute();
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();	}
			try {if(clsCall!=null) clsCall.close();} catch (SQLException e) {e.printStackTrace();	}
	}
}


public void eliminarVehDao(Integer Pv_ve_id){
	
	
	CallableStatement clsCall = null;
	//ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINAR_VEHICULO_PRC(?)}");
			clsCall.setInt(1,Pv_ve_id);			
			clsCall.execute();
			
			//if(rs != null) rs.close();
			if(clsCall != null) clsCall.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
	}
}



public void  insertaCercaDao(String Pv_nombre, String Pv_desc , int Pv_usu_id )
{	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_INSERTA_CERCA_PRC(?,?,?)}");
			clsCall.setString(1, Pv_nombre);
			clsCall.setString(2, Pv_desc);
			clsCall.setInt(3, Pv_usu_id);
			
			clsCall.execute();
		
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		
		try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  insertaDetCercaDao(int Pv_ord , double Pv_lng, double Pv_lat , int Pv_ce_id )
{	
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_INSERTA_DET_CERCA_PRC(?,?,?,?)}");
			clsCall.setInt(1, Pv_ord);
			clsCall.setDouble(2, Pv_lng);
			clsCall.setDouble(3, Pv_lat);
			clsCall.setInt(4, Pv_ce_id);
			
			clsCall.execute();
		
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(conn!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
		
	}
}
	

public void  insertaUsuarioDao(String Pv_usu_nombre , String Pv_usu_apellido , String Pv_cedula , String Pv_email ,
		String Pv_user , String Pv_pass , int Pv_perfil_id ,String Pv_estado ,
		String Pv_direccion , String Pv_telefono ,String Pv_celular , String Pv_fecha_nacimiento)
{	
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_INSERTA_USUARIO_PRC(?,?,?,?,?,?,?,?,?,?,?,?)}");
			clsCall.setString(1, Pv_usu_nombre);
			clsCall.setString(2, Pv_usu_apellido);
			clsCall.setString(3, Pv_cedula);
			clsCall.setString(4, Pv_email);
			clsCall.setString(5, Pv_user);
			clsCall.setString(6, Pv_pass);
			clsCall.setInt(7, Pv_perfil_id);
			clsCall.setString(8, Pv_estado);
			clsCall.setString(9, Pv_direccion);
			clsCall.setString(10, Pv_telefono);
			clsCall.setString(11, Pv_celular);
			clsCall.setString(12, Pv_fecha_nacimiento);
			
			
			clsCall.execute();
		
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
	}
}

public void  actualizaUsuarioDao(String Pv_usu_nombre , String Pv_usu_apellido , String Pv_cedula , String Pv_email ,
		String Pv_user , String Pv_pass , int Pv_perfil_id ,
		String Pv_direccion , String Pv_telefono ,String Pv_celular , String Pv_fecha_nacimiento ,int idUsuario)
{	
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTUALIZA_USUARIO_PRC(?,?,?,?,?,?,?,?,?,?,?,?)}");
			clsCall.setString(1, Pv_usu_nombre);
			clsCall.setString(2, Pv_usu_apellido);
			clsCall.setString(3, Pv_cedula);
			clsCall.setString(4, Pv_email);
			clsCall.setString(5, Pv_user);
			clsCall.setString(6, Pv_pass);
			clsCall.setInt(7, Pv_perfil_id);
			
			clsCall.setString(8, Pv_direccion);
			clsCall.setString(9, Pv_telefono);
			clsCall.setString(10, Pv_celular);
			clsCall.setString(11, Pv_fecha_nacimiento);
			
			clsCall.setInt(12, idUsuario);
			
			
			clsCall.execute();
		
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
	}
}


public void eliminarCercaDao(cercas objCerca){
	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINAR_CERCA_PRC(?)}");
			clsCall.setInt(1, objCerca.getIdCercas());			
			clsCall.execute();
			
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {e.printStackTrace();	}
			try {clsCall.close();	} catch (SQLException e) {e.printStackTrace();	}
			
		
	}
}	


public void eliminarGrupoDao(int Pv_id_grupo){
	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINAR_GRUPO_PRC(?)}");
			clsCall.setInt(1, Pv_id_grupo);			
			clsCall.execute();
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
	}
}	


public void  insertaGrupoDao(String Pv_des , String Pv_valor )
{	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_INSERTAR_GRUPO_PRC(?,?)}");
			clsCall.setString(1, Pv_des);
			clsCall.setString(2, Pv_valor);
						
			clsCall.execute();
		
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  actualizaGrupoDao(String Pv_des , String Pv_valor, int Pv_gr_id )
{	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_UPDATE_GRUPO_PRC(?,?,?)}");
			clsCall.setString(1, Pv_des);
			clsCall.setString(2, Pv_valor);
			clsCall.setInt(3, Pv_gr_id);
						
			clsCall.execute();
		
			if(clsCall != null) clsCall.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}


public void  actualizaRecorridoDao(int Pv_ve_id, double Pv_ve_recorrido )
{	
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTUALIZA_RECORRIDO_PRC(?,?)}");
			clsCall.setInt(1, Pv_ve_id);
			clsCall.setDouble(2, Pv_ve_recorrido);
		
						
			clsCall.execute();
		
			if(clsCall != null) clsCall.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}


	public List<Clientes>  FillClientesDao(){
		
		List<Clientes> infoCliModel= new ArrayList<Clientes>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_USUARIOS_PRC}");
							
				clsCall.execute();
				rs = clsCall.getResultSet();
				Clientes cv = null;
				while (rs.next()) {
					cv= new Clientes();
					cv.setNombre(rs.getString(1));
					cv.setApellido(rs.getString(2));
					cv.setCedula(rs.getString(3));
					cv.setMail(rs.getString(4));
					cv.setUsuario(rs.getString(5));
					cv.setPassword(rs.getString(6));
					cv.setPer_id(rs.getInt("per_id"));
					cv.setPerfil(rs.getString("per_nombre"));
					cv.setUsu_id(rs.getInt("usu_id"));
					cv.setUsu_direccion(rs.getString("usu_direccion"));
					cv.setUsu_telefono(rs.getString("usu_telefono"));
					cv.setUsu_celular(rs.getString("usu_celular"));
					
					//sdf.format(rs.getDate("usu_fecha_nacimiento"));
					
					cv.setFecha_nacimiento( rs.getDate("usu_fecha_nacimiento"));
					
					infoCliModel.add(cv);
					
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally FillClientesDao");
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoCliModel;
		
	}
	
	
public List<Rel_usu_grupo>  ConsultaUsuGruDao(int Pv_gr_id){
		
		List<Rel_usu_grupo> infoRelModel= new ArrayList<Rel_usu_grupo>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_REL_USU_GRUPO_PRC(?)}");
				clsCall.setInt(1, Pv_gr_id);	
				clsCall.execute();
				rs = clsCall.getResultSet();
				Rel_usu_grupo rel = null;
				while (rs.next()) {
					rel= new Rel_usu_grupo();
					rel.setId_rel_usu_grupo(rs.getInt(1));
					rel.setUsu_id(rs.getInt(2));
					rel.setGr_id(rs.getInt(3));
					
					infoRelModel.add(rel);
					
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {	if(conn!=null)conn.close();} catch (SQLException e) {	e.printStackTrace();}
			try {	if(rs!=null)rs.close();} catch (SQLException e) {	e.printStackTrace();}
			try {	if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
			
		}
		
		return infoRelModel;
		
	}
	

public List<Perfiles>  ConsultaPefilDao(){
	
	List<Perfiles> infoPerModel= new ArrayList<Perfiles>();
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_CONSULTA_PERFIL_PRC}");
			clsCall.execute();
			rs = clsCall.getResultSet();
			Perfiles perfil = null;
			while (rs.next()) {
				perfil=new Perfiles();
				
				perfil.setPerId(rs.getLong("per_id"));
				perfil.setPerNombre(rs.getString("per_nombre"));
				perfil.setPerEstado(rs.getString("per_estado"));
				
				infoPerModel.add(perfil);
				
				
			}
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
	}
	
	return infoPerModel;
	
}


	public List<Grupo>  obtenerGruVehDao()
	{	
		List<Grupo> infoGrupoModel= new ArrayList<Grupo>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_GRUPO_PRC}");
							
				clsCall.execute();
				rs = clsCall.getResultSet();
				Grupo gr = null;
				while (rs.next()) {
					gr= new Grupo();
					gr.setGr_Id(rs.getInt("id_grupo"));
					gr.setGr_descripcion(rs.getString("grupo"));
					gr.setGr_value(rs.getString("valor"));
					
					infoGrupoModel.add(gr);	
				}
		
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtenerGruVehDao");
				try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoGrupoModel;
	}
	
	
	
	public List<Permisos>  obtenerPermisosDao()
	{	
		List<Permisos> infoPermisos= new ArrayList<Permisos>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_PERMISOS_PRC}");
							
				clsCall.execute();
				rs = clsCall.getResultSet();
				Permisos permiso = null;
				while (rs.next()) {
					permiso= new Permisos();
					permiso.setPrmId(rs.getDouble(1));
					permiso.setPrmNombre(rs.getString(2));
					permiso.setPrmValor(rs.getString(3));
					
					infoPermisos.add(permiso);	
				}
		
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtenerGruVehDao");
				try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoPermisos;
	}
	
	
	
	public int  insertaPerfilesDao(String Pv_nombre , String Pv_estado ) 
	{	
		CallableStatement clsCall = null; 
		Connection conn = null;
		int idPerfil=0;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_INSERT_PERFILES_PRC(?,?,?)}");
				
				clsCall.registerOutParameter(3, Types.INTEGER);
				
				clsCall.setString(1, Pv_nombre);
				clsCall.setString(2, Pv_estado);
							
				
				clsCall.execute();
				idPerfil = clsCall.getInt(3);
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return idPerfil;
	}
	
	public int  existePerfilConUsuarioDao(int idPerfil) 
	{	
		CallableStatement clsCall = null; 
		Connection conn = null;
		int cantUsuarios=0;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_EXISTE_USUARIO_CON_PERFIL_PRC(?,?)}");
				
				clsCall.registerOutParameter(2, Types.INTEGER);
				clsCall.setInt(1, idPerfil);
				
							
				
				clsCall.execute();
				cantUsuarios = clsCall.getInt(2);
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return cantUsuarios;
	}
	
	public void  insertaRelacionPerfilPermisoDao(int idPerfil, double idPermiso)  
	{	
		CallableStatement clsCall = null; 
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_INSERTA_RELACION_PERFIL_PERMISO_PRC(?,?)}");
				
				
				clsCall.setInt(1, idPerfil);
				clsCall.setDouble(2, idPermiso);
							
				clsCall.execute();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
	}
	
	
	
	
	public List<Permisos>  obtenerPermisosSeleccionadosDao(long perfilId)
	{	
		List<Permisos> infoPermisos= new ArrayList<Permisos>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_PERMISO_SELECCIONADO_PRC(?)}");
				clsCall.setLong(1, perfilId);			
				clsCall.execute();
				rs = clsCall.getResultSet();
				Permisos permiso = null;
				while (rs.next()) {
					permiso= new Permisos();
					permiso.setPrmId(rs.getDouble(1));
					permiso.setPrmNombre(rs.getString(2));
					permiso.setPrmValor(rs.getString(3));
					
					infoPermisos.add(permiso);	
				}
		
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtenerGruVehDao");
				try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
				try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoPermisos;
	}
	
	
	
	public List<cercas>  fillCercasDao(MapUsuarioSistema objUsuarioSistema)
	{	
		List<cercas> infoCercas= new ArrayList<cercas>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_CERCA_PRC(?,?)}");
				clsCall.setInt(1, objUsuarioSistema.getUsu_id());
				clsCall.setInt(2, objUsuarioSistema.getPer_id());
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				cercas cer = null;
				while (rs.next()) {
					cer = new cercas();
					cer.setIdCercas(rs.getInt(1));
					cer.setNombreCerca(rs.getString(2));
					cer.setDescripcionCerca(rs.getString(3));
					cer.setIdUsuario(rs.getInt(4));
					cer.setEstado(rs.getString(5));
					cer.setUsuario(rs.getString(6));
					//System.out.println("-- -- -- --"+cer.getEstado());
					infoCercas.add(cer);
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally fillCercasDao");
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return infoCercas;
	}
	
	
	public List<CategoriaVehiculo>  obtenerCatVehDao(MapUsuarioSistema objUsuarioSistema)
	{	
		List<CategoriaVehiculo> infoCategorias= new ArrayList<CategoriaVehiculo>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_CATEGORIA_PRC(?,?)}");
				clsCall.setInt(1, objUsuarioSistema.getUsu_id());
				clsCall.setInt(2, objUsuarioSistema.getPer_id());
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				CategoriaVehiculo cv = null;
				while (rs.next()) {
					cv = new CategoriaVehiculo();
					cv.setId_categoria(rs.getInt(1));
					cv.setDescripcion(rs.getString(2));
					cv.setIdentificador(rs.getString(3));
					cv.setEstado(rs.getString("estado"));
					
					
					//if(rs.getString(2).equals(_user) || per_id==1)
					infoCategorias.add(cv);
				}
			
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
				System.out.println("finally obtenerCatVehDao");
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoCategorias;
	}
	
	
	public List<CategoriaVehiculo>  presentaVehiculoDao(MapUsuarioSistema objUsuarioSistema) 
	{	
		List<CategoriaVehiculo> infoCategorias= new ArrayList<CategoriaVehiculo>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_VEHICULOS_MAP_GOOLE3(?)}");
				clsCall.setInt(1, objUsuarioSistema.getUsu_id());
				
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				CategoriaVehiculo cv = null;
				while (rs.next()) {
					cv = new CategoriaVehiculo();
					cv.setNomVeh(rs.getString("nombreVehiculo"));
					cv.setId_categoria(rs.getInt("id_categoria"));
					cv.setIdVeh(rs.getInt("idVehiculo"));
					cv.setImaVeh(rs.getString("imagen"));
					
					
					//if(rs.getString(2).equals(_user) || per_id==1)
					infoCategorias.add(cv);
				}
			
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
				System.out.println("finally presentaVehiculoDao");
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoCategorias;
	}
	
	
	
	public void  actualizaVehiculoDao(int Pv_veh_id,String Pv_nom ,int Pv_usu_id , int Pv_id_cat 
			, double Pv_recorrido,String Pv_imei , boolean Pv_mant ,int Pv_ce_id , double Pv_horas_enc , int Pv_gr_id,  String Pv_placa 
				, int Pv_anio , String Pv_alias , String Pv_notas )
			{	
				List<CategoriaVehiculo> infoCategorias= new ArrayList<CategoriaVehiculo>();
				CallableStatement clsCall = null;
				//ResultSet rs = null;
				Connection conn = null;
				try {
					conn= ConexionUtil.getConnection();
					if(conn != null){
						clsCall = conn.prepareCall("{call MAP_ACTUALIZA_VEHICULOS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						clsCall.setInt(1, Pv_veh_id);
						clsCall.setString(2, Pv_nom);
						clsCall.setInt(3, Pv_usu_id);
						clsCall.setInt(4, Pv_id_cat);
						
						clsCall.setDouble(5, Pv_recorrido);
						clsCall.setString(6, Pv_imei);
						clsCall.setBoolean(7, Pv_mant);
						clsCall.setInt(8, Pv_ce_id);
						clsCall.setDouble(9, Pv_horas_enc);
						clsCall.setInt(10, Pv_gr_id);
						clsCall.setString(11, Pv_placa);
						
						clsCall.setInt(12, Pv_anio);
						clsCall.setString(13, Pv_alias);
						clsCall.setString(14, Pv_notas);
						
						clsCall.execute();
						/*rs = clsCall.getResultSet();
						CategoriaVehiculo cv = null;
						while (rs.next()) {
							cv = new CategoriaVehiculo();
							cv.setId_categoria(rs.getInt(1));
							cv.setDescripcion(rs.getString(2));
							cv.setIdentificador(rs.getString(3));
							
							
							//if(rs.getString(2).equals(_user) || per_id==1)
							infoCategorias.add(cv);
						}*/
				
						
					}		
				}
				catch(Exception e)
				{e.printStackTrace();
				}finally {
					try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
					try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
					//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
					
				}
			
			}
	
	
	
	public void  guardaVehiculo(String Pv_nom ,int Pv_usu_id , int Pv_id_cat , String Pv_img 
	   ,String Pv_imei , double Pv_recorrido , boolean Pv_mant ,int Pv_ce_id , double Pv_horas_enc , String Pv_placa ,int Pv_gr_id 
		, int Pv_anio , String Pv_alias , String Pv_notas, double PV_recorrido_inicial )
	{	
		List<CategoriaVehiculo> infoCategorias= new ArrayList<CategoriaVehiculo>();
		CallableStatement clsCall = null;
		//ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_INSERTA_VEHICULO_PRC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				clsCall.setString(1, Pv_nom);
				clsCall.setInt(2, Pv_usu_id);
				clsCall.setInt(3, Pv_id_cat);
				clsCall.setString(4, Pv_img);
				
				clsCall.setString(5, Pv_imei);
				clsCall.setDouble(6, Pv_recorrido);
				clsCall.setBoolean(7, Pv_mant);
				clsCall.setInt(8, Pv_ce_id);
				clsCall.setDouble(9, Pv_horas_enc);
				clsCall.setString(10, Pv_placa);
				clsCall.setInt(11, Pv_gr_id);
				
				clsCall.setInt(12, Pv_anio);
				clsCall.setString(13, Pv_alias);
				clsCall.setString(14, Pv_notas);
				clsCall.setDouble(15,PV_recorrido_inicial );
				
				clsCall.execute();
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			//try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
	}
	
	
	public void  insertaCategoriaDao(String Pv_des , String Pv_iden , int Pv_usu_id )
			{	
				CallableStatement clsCall = null;
				Connection conn = null;
				try {
					conn= ConexionUtil.getConnection();
					if(conn != null){
						clsCall = conn.prepareCall("{call MAP_INSERTA_CATEGORIA_PRC(?,?,?)}");
						clsCall.setString(1, Pv_des);
						clsCall.setString(2, Pv_iden);
						clsCall.setInt(3, Pv_usu_id);
						
						
						clsCall.execute();
					
						
						
					}		
				}
				catch(Exception e)
				{e.printStackTrace();
				}finally {
					try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
					try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
					
				}
				
				//return infoCategorias;
			}
	
	public void  actualizaCategoriaDao(String Pv_des , String Pv_iden , int Pv_cat_id )
	{	
		CallableStatement clsCall = null;
		//ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_ACTUALIZA_CATEGORIA_PRC(?,?,?)}");
				clsCall.setString(1, Pv_des);
				clsCall.setString(2, Pv_iden);
				clsCall.setInt(3, Pv_cat_id);
				
				
				clsCall.execute();

			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {e.printStackTrace();}
			//try {rs.close();	} catch (SQLException e) {e.printStackTrace();}
			
		}
		
	}
	
	

	public List<Vehiculos>  obtieneVehiculoDao( int id_user)
	{	
		List<Vehiculos> infoVehiculos= new ArrayList<Vehiculos>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_VEH_PRC(?)}");
				clsCall.setInt(1, id_user);
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				Vehiculos vehiculo = null;
				while (rs.next()) {
					vehiculo = new Vehiculos();
					vehiculo.setNombre(rs.getString(1));
					vehiculo.setUsuario(rs.getString(2));
					vehiculo.setCategoria(rs.getString(3));
					vehiculo.setCatVeh(rs.getInt(5));
					vehiculo.setIdUsu(rs.getInt(6));
					vehiculo.setVehId(rs.getInt(7));
					vehiculo.setImei(rs.getString(8));
					vehiculo.setRecorrido(rs.getDouble(9));
					vehiculo.setMantenimiento(rs.getString(10));
					vehiculo.setCercaNombre(rs.getString(11));
					if(rs.getString(11)==null || rs.getString(11).equals(""))
						vehiculo.setCercaNombre("NINGUNA");
					vehiculo.setIdCerca(rs.getInt(12));
					vehiculo.setVe_horas_encendido(rs.getDouble(13));
					vehiculo.setPlaca(rs.getString("Placa"));
					vehiculo.setGrupo(rs.getString("grupo"));
					vehiculo.setGr_id(rs.getInt("gr_id"));
					vehiculo.setVe_alias(rs.getString("ve_alias"));
					vehiculo.setVe_anio(rs.getString("ve_anio"));
					vehiculo.setVe_notas(rs.getString("ve_nota"));
					vehiculo.setRecorridoInicial(rs.getDouble("ve_recorrido_inicial"));
					vehiculo.setEstado(rs.getString("estado"));
					
					
					//if(rs.getString(2).equals(_user) || per_id==1)
					infoVehiculos.add(vehiculo);
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtieneVehiculoDao");
		try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return infoVehiculos;
	}
	

	
	public List<Clientes>  obtieneUsuariosGruDao(int Pv_gr_id)
	{	
		List<Clientes> usuModel= new ArrayList<Clientes>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_REL_USU_PRC (?)}");
				clsCall.setInt(1, Pv_gr_id);
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				Clientes cv = null;
				while (rs.next()) {
					cv = new Clientes();
					
					cv.setNombre(rs.getString("usu_nombre"));
					cv.setApellido(rs.getString("usu_apellido"));
					cv.setCedula(rs.getString("usu_cedula"));
					
					cv.setUsuario(rs.getString("user"));
					cv.setPassword(rs.getString("password"));
					cv.setPer_id(rs.getInt("per_id"));
					
					cv.setUsu_id(rs.getInt("usu_id"));
					
					
					//sdf.format(rs.getDate("usu_fecha_nacimiento"));
					
					cv.setFecha_nacimiento( rs.getDate("usu_fecha_nacimiento"));
					
					usuModel.add(cv);
				}
				if(rs != null) rs.close();
				if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(rs!=null)rs.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
		
		return usuModel;
	}
	
	
	public List<Clientes>  obtieneUsuariosDao()
	{	
		List<Clientes> usuModel= new ArrayList<Clientes>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_USUARIOS}");
				
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				Clientes cv = null;
				while (rs.next()) {
					cv = new Clientes();
					cv.setNombre(rs.getString(1));
					cv.setApellido(rs.getString(2));
					cv.setCedula(rs.getString(3));
					cv.setMail(rs.getString(4));
					cv.setUsuario(rs.getString(5));
					cv.setPassword(rs.getString(6));
					cv.setPer_id(rs.getInt("per_id"));
					cv.setPerfil(rs.getString("per_nombre"));
					cv.setUsu_id(rs.getInt("usu_id"));
					cv.setUsu_direccion(rs.getString("usu_direccion"));
					cv.setUsu_telefono(rs.getString("usu_telefono"));
					cv.setUsu_celular(rs.getString("usu_celular"));
					cv.setEstado(rs.getString("estado"));
					cv.setUsu_id(rs.getInt("usu_id"));
					//sdf.format(rs.getDate("usu_fecha_nacimiento"));
					
					cv.setFecha_nacimiento( rs.getDate("usu_fecha_nacimiento"));
					
					usuModel.add(cv);
				}
				if(rs != null) rs.close();
				if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		return usuModel;
	}
	
	public List<VehiculoUsuario> obtieneVehiculoUsuarioDao(Vehiculos objVehiculo)
	{
		List<VehiculoUsuario> infoVehUsu= new ArrayList<VehiculoUsuario>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_VEHICULO_USUARIO_PRC (?)}");
				clsCall.setInt(1, objVehiculo.getVehId());
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				VehiculoUsuario VehUsu = null;
				while (rs.next()) {
					VehUsu = new VehiculoUsuario();
					
					VehUsu.setVeId(rs.getInt("ve_id"));
					VehUsu.setUsuId(rs.getInt("usu_id"));
					
					infoVehUsu.add(VehUsu);
				}
				if(rs != null) rs.close();
				if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(rs!=null)rs.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
		
		return infoVehUsu;
	}
	
	
	
	public void eliminaRelacionPerfilPermiso(long perId)
	{
		CallableStatement clsCall = null;
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_ELIMINA_RELACION_PERFIL_PERMISO (?)}");
				clsCall.setLong(1, perId);
				
				clsCall.execute();
				
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
	}
	
	
	public void eliminaPerfilDao(int perId)
	{
		CallableStatement clsCall = null;
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_ELIMINA_PERFIL_PRC(?)}");
				clsCall.setInt(1, perId);
				
				clsCall.execute();
				
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
	}
	
	public void eliminaVehiculoUsuarioDao(Vehiculos objVehiculo)
	{
		CallableStatement clsCall = null;
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_ELIMINA_VEHICULO_USUARIO_PRC (?)}");
				clsCall.setInt(1, objVehiculo.getVehId());
				
				clsCall.execute();
				
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
	}
	//MAP_INSERTA_VEHICULO_USUARIO_PRC
	
	public void insertaVehiculoUsuarioDao(Vehiculos objVehiculo,Clientes objUsuarios)
	{
		CallableStatement clsCall = null;
		
		Connection conn = null;
		
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_INSERTA_VEHICULO_USUARIO_PRC (?,?)}");
				clsCall.setInt(1, objVehiculo.getVehId());
				clsCall.setInt(2, objUsuarios.getUsu_id());
				
				clsCall.execute();
				
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {conn.close();	} catch (SQLException e) {	e.printStackTrace();}
			try {clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
		}
	}
	
	
public List<Rel_usu_grupo>  ConsultaUsuGruPorUsuarioDao(Clientes objUser){
		
		List<Rel_usu_grupo> infoRelModel= new ArrayList<Rel_usu_grupo>();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_REL_USU_GRUPO_X_USUARIO_PRC(?)}");
				clsCall.setInt(1, objUser.getUsu_id());	
				clsCall.execute();
				rs = clsCall.getResultSet();
				Rel_usu_grupo rel = null;
				while (rs.next()) {
					rel= new Rel_usu_grupo();
					rel.setId_rel_usu_grupo(rs.getInt(1));
					rel.setUsu_id(rs.getInt(2));
					rel.setGr_id(rs.getInt(3));
					
					infoRelModel.add(rel);
					
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {	if(conn!=null)conn.close();} catch (SQLException e) {	e.printStackTrace();}
			try {	if(rs!=null)rs.close();} catch (SQLException e) {	e.printStackTrace();}
			try {	if(clsCall!=null)clsCall.close();} catch (SQLException e) {	e.printStackTrace();}
			
		}
		
		return infoRelModel;
		
	}

public void eliminaRelUsuarioGrupoDao(Clientes objUser)
{
	CallableStatement clsCall = null;
	Connection conn = null;
	
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ELIMINA_REL_USU_GRUPO_PRC (?)}");
			clsCall.setInt(1, objUser.getUsu_id());
			
			clsCall.execute();
	
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
		try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
	}
}


public void insertaRelUsuarioGrupoDao(Clientes objUsuarios, Grupo objGrupo)
{
	CallableStatement clsCall = null;
	
	Connection conn = null;
	
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_INSERTA_REL_USU_GRUPO_PRC (?,?)}");
			clsCall.setInt(1, objUsuarios.getUsu_id());
			clsCall.setInt(2, objGrupo.getGr_Id());
			
			clsCall.execute();
			
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		try {if(conn!=null)conn.close();	} catch (SQLException e) {	e.printStackTrace();}
		try {if(clsCall!=null)clsCall.close();	} catch (SQLException e) {	e.printStackTrace();}
	}
}

public List<Coordenadas>  obtenerAlarmasDao( String fecDes, String fecHas,String nomVeh, String catVeh)
{	
	List<Coordenadas> infoAlarmas= new ArrayList<Coordenadas>();
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_CONSULTA_ALARMAS_PRC(?,?,?,?)}");
			clsCall.setString(1, fecDes);
			clsCall.setString(2, fecHas);
			clsCall.setString(3, nomVeh);
			clsCall.setString(4, catVeh);
			
			
			
			clsCall.execute();
			rs = clsCall.getResultSet();
			Coordenadas coor = null;
			while (rs.next()) {
				coor= new Coordenadas();
				coor.setIdCoorenada(rs.getInt(1));
				coor.setIdVehiculo(rs.getInt(2));
				coor.setVehiculo(rs.getString(3));
				coor.setLatitud(rs.getDouble(4));
				coor.setLongitud(rs.getDouble(5));
				coor.setFecha(rs.getTimestamp(6));
				coor.setComando(rs.getString(7));
				infoAlarmas.add(coor);
			}
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		System.out.println("finally obtieneVehiculoDao");
	try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	return infoAlarmas;
}


public List<Coordenadas> obtenerRutasDao(int idCercas)
{	
	List<Coordenadas> infoRutas= new ArrayList<Coordenadas>();
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_CONSULTA_RUTA_PRC(?)}");
			clsCall.setInt(1, idCercas);

			clsCall.execute();
			rs = clsCall.getResultSet();
			Coordenadas coor = null;
			while (rs.next()) {
				coor= new Coordenadas();
				
				coor.setLongitud(rs.getDouble(1));
				coor.setLatitud(rs.getDouble(2));
				
				infoRutas.add(coor);
			}
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		System.out.println("finally obtenerRutasDao");
	try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	return infoRutas;
}


public List<Coordenadas>  obtenerCercasDao( String fecDes, String fecHas,String nomVeh, String catVeh)
{	
	List<Coordenadas> infoCercas= new ArrayList<Coordenadas>();
	CallableStatement clsCall = null;
	ResultSet rs = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_CONSULTA_CERCAS_PRC(?,?,?,?)}");
			clsCall.setString(1, fecDes);
			clsCall.setString(2, fecHas);
			clsCall.setString(3, nomVeh);
			clsCall.setString(4, catVeh);
			
			
			
			clsCall.execute();
			rs = clsCall.getResultSet();
			Coordenadas coor = null;
			while (rs.next()) {
				coor= new Coordenadas();
				coor.setIdCoorenada(rs.getInt(1));
				coor.setIdVehiculo(rs.getInt(2));
				coor.setVehiculo(rs.getString(3));
				coor.setLatitud(rs.getDouble(4));
				coor.setLongitud(rs.getDouble(5));
				coor.setFecha(rs.getTimestamp(6));
				coor.setComando(rs.getString(7));
				infoCercas.add(coor);
			}
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		System.out.println("finally obtieneVehiculoDao");
	try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
	try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
	
	return infoCercas;
}


public void  actualizaDatosCercasDao(String nomCerca , String desCerca, String estado, int idCercas)
{	 
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTUALIZA_CERCA_PRC(?,?,?,?)}");
			clsCall.setString(1, nomCerca);
			clsCall.setString(2, desCerca);
			clsCall.setString(3, estado);
			clsCall.setInt(4, idCercas);
						
			clsCall.execute();
		
			if(clsCall != null) clsCall.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  activarInactivarVehiculoDao(String estado, int vehiculoId)
{	 
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTIVAR_INACTIVAR_VEHICULOS_PRC(?,?)}");
			clsCall.setString(1, estado);
			clsCall.setInt(2, vehiculoId);
						
			clsCall.execute();
		
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  activarInactivarUsuariosDao(String estado, int usuarioId)
{	 
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTIVAR_INACTIVAR_USUARIO_PRC(?,?)}");
			clsCall.setString(1, estado);
			clsCall.setInt(2, usuarioId);
						
			clsCall.execute();
		
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  activarInactivarCategoriaDao(String estado, int categoriaId)
{	 
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTIVAR_INACTIVAR_CATEGORIA_PRC(?,?)}");
			clsCall.setString(1, estado);
			clsCall.setInt(2, categoriaId);
						
			clsCall.execute();
		
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

public void  actualizaPerfilDao(String nomPerfil, String estado,  long perfilId)
{	 
	CallableStatement clsCall = null;
	Connection conn = null;
	try {
		conn= ConexionUtil.getConnection();
		if(conn != null){
			clsCall = conn.prepareCall("{call MAP_ACTUALIZA_PERFIL_PRC(?,?,?)}");
			clsCall.setString(1, nomPerfil);
			clsCall.setString(2, estado);
			clsCall.setLong(3, perfilId);
			
						
			clsCall.execute();
		
			
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
			try {if(conn!=null)conn.close();} catch (SQLException e) {e.printStackTrace();}
			try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
		
	}
}

	
}
