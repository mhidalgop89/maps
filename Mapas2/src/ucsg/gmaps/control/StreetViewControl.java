package ucsg.gmaps.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Doublebox;

public class StreetViewControl extends GenericForwardComposer {
	Doublebox db1;
	Doublebox db2;
	Double _lat=0.0;
	Double _lng=0.0;
AnnotateDataBinder binder;
	
public Doublebox getDb1() {
	return db1;
}
public void setDb1(Doublebox db1) {
	this.db1 = db1;
}
public Doublebox getDb2() {
	return db2;
}
public void setDb2(Doublebox db2) {
	this.db2 = db2;
}
public Double get_lat() {
	return _lat;
}
public void set_lat(Double _lat) {
	this._lat = _lat;
}
public Double get_lng() {
	return _lng;
}
public void set_lng(Double _lng) {
	this._lng = _lng;
}
public AnnotateDataBinder getBinder() {
	return binder;
}

public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}

public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("win",this,true);
    		
    		binder = new AnnotateDataBinder(cmp);
    		_lat=(Double)Executions.getCurrent().getSession().getAttribute("_lat");
    		_lng=(Double)Executions.getCurrent().getSession().getAttribute("_lng");
    		System.out.println("sv lat: "+_lat+" lng: "+_lng);
    		db1.setValue(_lat);
    		db2.setValue(_lng);
    		Clients.evalJavaScript("initialize");
    		binder.loadComponent(db1);
    		binder.loadComponent(db2);
    		binder.loadAll();
    		
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
}
