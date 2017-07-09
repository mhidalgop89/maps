package ucsg.gmaps.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConexionUtil.ConexionUtil;
import Util.Vehiculos;

import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.chart.model.CategoryModel;
public class ReportChartsDao {
	
	private CategoryModel model;
	
	public CategoryModel  obtieneVehiculoDao( int id_user)
	{	
		List<Vehiculos> infoVehiculos= new ArrayList<Vehiculos>();
		model = new DefaultCategoryModel();
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
					if(rs.getDouble(9)>0)
					model.setValue(rs.getString(1), "VEHICULOS", rs.getDouble(9));
					
					
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtieneVehiculoDao");
		try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return model;
	}


	
//	public CategoryModel  obtieneRecorridoVehiculoPorFechaDao( Date fechaDesde, Date fechaHasta)
	public CategoryModel  obtieneRecorridoVehiculoPorFechaDao( String fechaDesde, String fechaHasta)
	{	
		List<Vehiculos> infoVehiculos= new ArrayList<Vehiculos>();
		model = new DefaultCategoryModel();
		CallableStatement clsCall = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_CONSULTA_RECORRIDO_POR_FECHA_PRC(?,?)}");
				clsCall.setString(1, fechaDesde);
				clsCall.setString(2, fechaHasta);
				
				
				clsCall.execute();
				rs = clsCall.getResultSet();
				System.out.println("procedimiento: MAP_CONSULTA_RECORRIDO_POR_FECHA_PRC "+fechaDesde+" - "+fechaHasta);
				while (rs.next()) {
					
					model.setValue(rs.getString(2), rs.getDate(4).toString(), rs.getDouble(3));
					System.out.println(rs.getString(2)+" -- "+ rs.getDate(4).toString()+" -- "+ rs.getDouble(3));
					
				}
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			System.out.println("finally obtieneRecorridoVehiculoPorFechaDao");
		try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(rs!=null)rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {if(clsCall!=null)clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		return model;
	}
	 
	
	
	
	

}
