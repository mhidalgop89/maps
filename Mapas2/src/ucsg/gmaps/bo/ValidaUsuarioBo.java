package ucsg.gmaps.bo;

import ucsg.gmaps.dao.ValidaUsuarioDao;

public class ValidaUsuarioBo {
	
	public String VerificaUsuarioBo(String user, String pass)
	{	String existe=null;
		
		ValidaUsuarioDao vu= new ValidaUsuarioDao();
		existe=vu.VerificaUsuarioDao(user, pass);
		
		return existe;
	}
	
	
	

}
