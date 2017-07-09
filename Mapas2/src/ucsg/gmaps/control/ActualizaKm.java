package ucsg.gmaps.control;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import Util.CategoriaVehiculo;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;

public class ActualizaKm extends GenericForwardComposer  {
	private Vehiculos objVehiculo;
	Label lblKmTot;
	Doublebox txtKmAct;
	Window winActKm;
	double kmTotal=0;
	double kmActual=0;
	MapUsuarioSistema objUsuarioSistema = null;
	
	//componentes
	private Caption cptActualizaKm;
	private Label lblKmTotal;
	private Label lblKmActual;
	private Button btnActualizar;
	private Button btnCancelar;
	
	AnnotateDataBinder binder;
	
	
	public void doAfterCompose(Component cmp){
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winRegGru",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		objVehiculo=(Vehiculos)arg.get("pVehiculo");
    		
    		getUserFromIndex();
			if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		
    		
    		if(objVehiculo!=null)
    		{
    			kmTotal= objVehiculo.getRecorrido();
    		lblKmTot.setValue(String.valueOf(kmTotal));
    			
    		binder.loadAll();
    		}
    		cargarComponentes();
    	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	
	public void cargarComponentes()
	{
		cptActualizaKm.setLabel(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.ActualizaKm", objUsuarioSistema.getIdioma()));
		lblKmTotal.setValue(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.KmTotal", objUsuarioSistema.getIdioma()));
		lblKmActual.setValue(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.KmActual", objUsuarioSistema.getIdioma()));
		btnActualizar.setLabel(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.Actualizar", objUsuarioSistema.getIdioma()));
		btnCancelar.setLabel(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.Cancelar", objUsuarioSistema.getIdioma()));
	}
	
	public void desconectar()
	{

		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.sendRedirect("index.zul");
		
	}
	
public void getUserFromIndex() {
		
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	}
	
	public void guardarKm()
	{	

		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		double variacion=0;
		double variacionBase=0;
		double gps=0;
		double recorrido=0;
		double recoridoTotal=0;
		
		
		try{
			
			//restar input de usuario
			
			//kmActual = Double.valueOf( txtKmAct.getText() );//adrian
			//variacionbase= variacionbase-variacion;//adrian
			kmActual = txtKmAct.getValue();
			
			variacionBase = objVehiculo.getVariacion();
			variacion = kmActual - kmTotal;
			variacionBase = variacionBase+variacion;
			recorrido =kmTotal+variacionBase;
			dat.actualizaRecorridoDao(objVehiculo.getVehId(), recorrido);
			
			//recorrido = kmTotal-kmActual;//ya no
			//recoridoTotal = objVehiculo.getRecorridoInicial()-recorrido;//ya no
			//dat.actualizaGrupoDao(objVehiculo.getVehId(), recoridoTotal);//ya no
			Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.KmActualizado", objUsuarioSistema.getIdioma()),
					ReadPropertiesUtil.obtenerProperty("control.ActualizaKm.Atencion", objUsuarioSistema.getIdioma()),
					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			//binder.loadComponent(cmbCatVeh);
			winActKm.detach();
		
		}
		catch(Exception e){e.printStackTrace();}
		
	}
	
	public void cerrarVentana()
	{
		winActKm.detach();
		
	}
	
	
	public double getKmActual() {
		return kmActual;
	}

	public void setKmActual(double kmActual) {
		this.kmActual = kmActual;
	}

	public double getKmTotal() {
		return kmTotal;
	}

	public void setKmTotal(double kmTotal) {
		this.kmTotal = kmTotal;
	}

	public Window getWinActKm() {
		return winActKm;
	}

	public void setWinActKm(Window winActKm) {
		this.winActKm = winActKm;
	}

	public Vehiculos getObjVehiculo() {
		return objVehiculo;
	}

	public void setObjVehiculo(Vehiculos objVehiculo) {
		this.objVehiculo = objVehiculo;
	}

	public Label getLblKmTot() {
		return lblKmTot;
	}

	public void setLblKmTot(Label lblKmTot) {
		this.lblKmTot = lblKmTot;
	}

	public Doublebox getTxtKmAct() {
		return txtKmAct;
	}

	public void setTxtKmAct(Doublebox txtKmAct) {
		this.txtKmAct = txtKmAct;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public Caption getCptActualizaKm() {
		return cptActualizaKm;
	}


	public void setCptActualizaKm(Caption cptActualizaKm) {
		this.cptActualizaKm = cptActualizaKm;
	}


	public Label getLblKmTotal() {
		return lblKmTotal;
	}


	public void setLblKmTotal(Label lblKmTotal) {
		this.lblKmTotal = lblKmTotal;
	}


	public Label getLblKmActual() {
		return lblKmActual;
	}


	public void setLblKmActual(Label lblKmActual) {
		this.lblKmActual = lblKmActual;
	}


	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public Button getBtnActualizar() {
		return btnActualizar;
	}


	public void setBtnActualizar(Button btnActualizar) {
		this.btnActualizar = btnActualizar;
	}


	public Button getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


}
