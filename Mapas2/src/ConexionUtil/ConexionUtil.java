package ConexionUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//import java.util.Properties;

//import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


import org.omg.CORBA.Context;
import org.xml.sax.InputSource;


public class ConexionUtil {

	public static Connection getConnection()// throws ClassNotFoundException, SQLException
    {
        //Change these settings according to your local configuration
		Connection conn =null;
        try{

       
		String driver = "com.mysql.jdbc.Driver";
        String connectString = "jdbc:mysql://localhost:3306/gmap";
        String user = "root";//"root";
        String password =/*"parqueoPositivo2016*+";*/ "root";//admin
        Class.forName(driver);
        conn = DriverManager.getConnection(connectString, user, password);
      System.out.println("llega mySql");
        	
  	
  	/*try{
  		Properties env = new Properties();
  	    env.put("java.naming.factory.initial","com.sun.jndi.cosnaming.CNCtxFactory");

  	    //InitialContext initialContext = new InitialContext(env);
  		
  		
  	javax.naming.InitialContext initialContext = new InitialContext(env);
		DataSource dataSource = (DataSource)
		initialContext.lookup("java:/comp/env/jdbc/poolConexion");
      conn = dataSource.getConnection();
      System.out.println("Connected...");
  	}catch(NoInitialContextException e){e.printStackTrace();}
  */
  
      
      
      
 //        String driver = "com.mysql.jdbc.Driver";
//            String connectString = "jdbc:mysql://localhost:3306/gmap";
//            String user = "admin";
//            String password = "e7ec7354d5";
//            Class.forName(driver);
//            conn = DriverManager.getConnection(connectString, user, password);
//          System.out.println("llega mySql");
      
          
        
        	/*String driver = "com.mysql.jdbc.Driver";
            String connectString = "jdbc:mysql://localhost:3306/gmap";
            String user = "root";
            String password = "12345";
            Class.forName(driver);
            conn = DriverManager.getConnection(connectString, user, password);
          System.out.println("llega mySql");
          */
        }
        
        
        catch(Exception e)
        {e.printStackTrace();}
          return conn;
    }
	
	
	
	
    public static java.sql.Connection OracleConnection()
    {
    java.sql.Connection connection = null;


    try {
    // Load the JDBC driver
    String driverName = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driverName);

    // Create a connection to the database
    String serverName = "10.10.1.44";
    String portNumber = "1521";
    String sid = "Transito";
    String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    String username = "axisctg";
    String password = "oracle456";
    //"jdbc:oracle:thin:@10.10.1.44:1521:Transito"
    connection = DriverManager.getConnection(url, username, password);
    System.out.println("all right");
} catch (ClassNotFoundException e) {
    e.printStackTrace();
    // Could not find the database driver
} catch (SQLException e) {
    e.printStackTrace();
    // Could not connect to the database
}

    return connection;

    }
	
	
    
    public static java.sql.Connection OracleConnectionRep()
    {
    java.sql.Connection connection = null;


    try {
    // Load the JDBC driver
    String driverName = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driverName);

    // Create a connection to the database
    //String serverName = "10.10.1.165"; //pro
    String serverName = "10.10.1.36"; //preproducc
    
    String portNumber = "1521";
    //String sid = "CTGIMG"; //pro
    String sid = "PRTRAN"; //preproducc
    
    String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    
    //String username = "usrweb2"; //pro
    String username = "axisctg";//preproducc
    
    //String password = "NHU56TGB";//pro
    String password = "axisctg";//preproducc
    
    
    //"jdbc:oracle:thin:@10.10.1.44:1521:Transito"
    connection = DriverManager.getConnection(url, username, password);
    System.out.println("all right");
} catch (ClassNotFoundException e) {
    e.printStackTrace();
    // Could not find the database driver
} catch (SQLException e) {
    e.printStackTrace();
    // Could not connect to the database
}

    return connection;

    }
	
    
    public static void main(String arg[])
    {
    	ConexionUtil a= new ConexionUtil();
    	a.getConnection();
    }
}
