package ucsg.gmaps.webservices;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import ConexionUtil.ConexionUtil;

public class RegistraCoordenadas {
	
	
	public void  insertaCoordenadaDao(String latitud, String longitud, String altitud, String imei)
	{	
		CallableStatement clsCall = null;
		Connection conn = null;
		try {
			conn= ConexionUtil.getConnection();
			if(conn != null){
				clsCall = conn.prepareCall("{call MAP_INSERTAR_COORDENADAS_MOBIL(?,?,?,?,?,?,?,?,?,?)}");
				clsCall.setDouble(1, Double.valueOf(latitud));
				clsCall.setDouble(2, Double.valueOf(longitud));
				clsCall.setDouble(3, Double.valueOf(altitud));
				clsCall.setTimestamp(4,	 new java.sql.Timestamp( new Date().getTime()));
				clsCall.setDouble(5, 0);
				clsCall.setDouble(6, 10);
				clsCall.setString(7, "POS");
				clsCall.setInt(8, 0);
				clsCall.setInt(9, 0);
				clsCall.setString(10, imei);
				
				
				clsCall.execute();
			
				
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			
			try {clsCall.close();} catch (SQLException e) {e.printStackTrace();}
			
		}
	}
	
	
	public static void main(String[] args) {
		RegistraCoordenadas rc = new RegistraCoordenadas();
		rc.insertaCoordenadaDao("0.0", "0.0", "0.0", "355019073413719");
		
	}

}
