package ucsg.gmaps.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;

import Util.Coordenadas;

public class ControlMapaCercas extends GenericForwardComposer{
	
	
public void doAfterCompose(Component cmp){  	
    	
    	/*try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winMantAlarmas",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objAlarmas=(Coordenadas)arg.get("pCoordenadas");
    		if(objAlarmas==null)
    		{
    			Messagebox.show("No Existen registros","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
    		
    		
    		
    		}
    		
    		cargaMapa();
    		binder.loadAll();
    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}*/
    }

	public void cargaMapa()
	{
		/*double lng=0;
		double ltd=0;
		lng = objAlarmas.getLongitud();
		ltd = objAlarmas.getLatitud();
		myMark(ltd, lng);
		Messagebox.show("Ubcación generada con exito!!","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);*/
		
	}

}
