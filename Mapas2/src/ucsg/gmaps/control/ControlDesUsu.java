package ucsg.gmaps.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;

public class ControlDesUsu extends GenericForwardComposer{
	
	Button btnIniPag;
	AnnotateDataBinder binder;	
	
	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winDesUsu",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		//getUserFromIndex();
 

    		binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
  
}
	
	public void goFirstPage()
	{
		Executions.sendRedirect("index.zul");
		
	}
	
	


}
