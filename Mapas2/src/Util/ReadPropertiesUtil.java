package Util;



import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesUtil {

	
	public static String obtenerProperty(String propiedad,String idioma)
	{
		Properties prop = new Properties();
		InputStream input = null;
		
		String valor=null;
	 
		try {
			
			String so = System.getProperty("os.name"); 
			
			if (so.contains("Windows"))
			{
				if(idioma.equals("en"))
					input = new FileInputStream("C:\\propertiesMaps\\propertiesGmaps\\messages_en.properties");
				if(idioma.equals("es"))
					input = new FileInputStream("C:\\propertiesMaps\\propertiesGmaps\\messages_es.properties");
			
			}else
			{
				input = new FileInputStream("/opt/constitucion/constitucionElectronica.properties");
			}
	 
			
	 
			// load a properties file
			prop.load(input);
			
			
			valor =prop.getProperty(propiedad);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return valor;
	}
	
	public static String obtenerConfig(String propiedad)
	{
		Properties prop = new Properties();
		InputStream input = null;
		
		String valor=null;
	 
		try {
			
			String so = System.getProperty("os.name"); 
			
			if (so.contains("Windows"))
			{
					input = new FileInputStream("C:\\propertiesMaps\\propertiesGmaps\\config.properties");
			
			}else
			{
				input = new FileInputStream("/opt/propertiesMaps/propertiesGmaps/config.properties");
			}
	 
			
	 
			// load a properties file
			prop.load(input);
			
			
			valor =prop.getProperty(propiedad);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return valor;
	}
	
	
	public static void main(String[] args) {
		System.out.println(ReadPropertiesUtil.obtenerProperty("Ingresar","en"));
	}

	
}
