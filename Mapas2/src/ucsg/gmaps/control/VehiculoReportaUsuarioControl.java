package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import Util.Clientes;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.VehiculoUsuario;
import Util.Vehiculos;

public class VehiculoReportaUsuarioControl extends GenericForwardComposer{
	AnnotateDataBinder binder;
	Vehiculos objVehiculo;
	Window winVehUser;
	private List<Clientes> usu= new ArrayList<Clientes>();
	private List<Clientes> usuModel= new ArrayList<Clientes>();
	Clientes objUsuario;
	boolean checkTodo=true;
	private List<VehiculoUsuario> infoVehUsu= new ArrayList<VehiculoUsuario>();
	MapUsuarioSistema objUsuarioSistema = null;
	Listbox listcli;
	Textbox txtFilUsu;
	Textbox txtNomVeh;
	Textbox txtPla;
	Checkbox chbSeleccionaUsuario;
	
	//componentes
	private Caption cptReportaVehUsuario;
	private Label lblNomVehiculo;
	private Label lblPlaca;
	private Listheader lshUsuario;
	private Button btnGuardar;
	private Button btnCancelar;
	
public void doAfterCompose(Component cmp){  	
    	
		try
		{
			super.doAfterCompose(cmp);
			cmp.setAttribute("winMantCli",this,true);
			//Ejecución de Ajax
			binder = new AnnotateDataBinder(cmp);
			getUserFromIndex();
			
			if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
			objVehiculo =(Vehiculos)arg.get("pVehiculo");
			getUsuarios();//VehiculoUsuario
			if(objVehiculo!=null)
			{	
				getSelectedUser();
				txtNomVeh.setText(objVehiculo.getNombre());
				txtPla.setText(objVehiculo.getPlaca());
				txtNomVeh.setReadonly(true);
				txtPla.setReadonly(true);
				for (int i=0;i<usuModel.size();i++)
				{
					for(int j=0;j<infoVehUsu.size();j++)
					{
						if(usuModel.get(i).getUsu_id()== infoVehUsu.get(j).getUsuId())
							usu.get(i).setSelected(true);
					}
					if(!usuModel.get(i).isSelected())
	    				checkTodo=false;
				}
			}
			chbSeleccionaUsuario.setChecked(checkTodo);
			
			cargarComponentes();
			
			binder.loadAll();
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }

public void cargarComponentes()
{
	cptReportaVehUsuario.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.ReportaVehUsu", objUsuarioSistema.getIdioma()));
	lblNomVehiculo.setValue(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.NombreVehiculo", objUsuarioSistema.getIdioma()));
	lblPlaca.setValue(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.Placa", objUsuarioSistema.getIdioma()));
	lshUsuario.setValue(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.Usuario", objUsuarioSistema.getIdioma()));
	btnGuardar.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.Guardar", objUsuarioSistema.getIdioma()));
	btnCancelar.setLabel(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.Cancelar", objUsuarioSistema.getIdioma()));
	
}


public void desconectar()
{

	Executions.getCurrent().getSession().removeAttribute("usuario");
	Executions.sendRedirect("index.zul");
	
}



public void checkEverything()
{
	 boolean notSelected=false;
	 for(int i=0;i<usu.size()&&!notSelected;i++)
		{
			if(!usu.get(i).isSelected())
				notSelected=true;
		}
	 
	 if(notSelected)
		 for(int i=0;i<usu.size();i++)
			 usu.get(i).setSelected(true);
	 else
		 for(int i=0;i<usu.size();i++)
			 usu.get(i).setSelected(false);
	 
	 binder.loadComponent(listcli);
}

public void getUserFromIndex()
{
	objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
}

public void getUsuarios()
{
	
	DatosDao dat = new DatosDao();
	
	
	usu=  new ArrayList<Clientes>();
	usuModel =  new ArrayList<Clientes>();
	try{
		usuModel = dat.obtieneUsuariosDao();
		usu = usuModel;
		binder.loadComponent(listcli);
	
	}
	catch(Exception e){e.printStackTrace();}	

}

public void getSelectedUser()
{
	DatosDao dat = new DatosDao();

	infoVehUsu= new ArrayList<VehiculoUsuario>();
	try{
		infoVehUsu = dat.obtieneVehiculoUsuarioDao(objVehiculo);
		}
	catch(Exception e){e.printStackTrace();}	
	
}
//eliminaVehiculoUsuarioDao

public void guardarUsuario()
{
	DatosDao dat = new DatosDao();
	dat.eliminaVehiculoUsuarioDao(objVehiculo);
	for(int i=0;i< usu.size();i++)
	{
		/*
		 * if(listcli.getItems().get(i).isSelected())
			dat.insertaVehiculoUsuarioDao(objVehiculo, usu.get(i));
		*/
		if(usu.get(i).isSelected())
			dat.insertaVehiculoUsuarioDao(objVehiculo, usu.get(i));
		
	}
	
	Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.ActualizadoExito", objUsuarioSistema.getIdioma()),
			ReadPropertiesUtil.obtenerProperty("control.VehiculoReportaUasuarioControl.Atencion", objUsuarioSistema.getIdioma()),
			Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
	
	winVehUser.detach();

}

public void filtraUsuario()
{
	if(!txtFilUsu.equals(""))
	{//System.out.println("txtFilDes: "+ txtFilDes.getText()+" txtFilIden:  "+txtFilIden.getText());
		usu = new ArrayList<Clientes>();
		
		for(int i=0; i< usuModel.size();i++)
		{
			if(		usuModel.get(i).getUsuario().toLowerCase().contains(txtFilUsu.getText().toLowerCase()))	
				usu.add(usuModel.get(i));
		}
		
		binder.loadComponent(listcli);
		
	}	
}

public void cerrarVentana()
{
	winVehUser.detach();
}


public boolean isCheckTodo() {
	return checkTodo;
}

public void setCheckTodo(boolean checkTodo) {
	this.checkTodo = checkTodo;
}

public Checkbox getChbSeleccionaUsuario() {
	return chbSeleccionaUsuario;
}

public void setChbSeleccionaUsuario(Checkbox chbSeleccionaUsuario) {
	this.chbSeleccionaUsuario = chbSeleccionaUsuario;
}

public Textbox getTxtNomVeh() {
	return txtNomVeh;
}

public void setTxtNomVeh(Textbox txtNomVeh) {
	this.txtNomVeh = txtNomVeh;
}

public Textbox getTxtPla() {
	return txtPla;
}

public void setTxtPla(Textbox txtPla) {
	this.txtPla = txtPla;
}

public Textbox getTxtFilUsu() {
	return txtFilUsu;
}

public void setTxtFilUsu(Textbox txtFilUsu) {
	this.txtFilUsu = txtFilUsu;
}

public Clientes getObjUsuario() {
	return objUsuario;
}

public void setObjUsuario(Clientes objUsuario) {
	this.objUsuario = objUsuario;
}

public List<VehiculoUsuario> getInfoVehUsu() {
	return infoVehUsu;
}

public void setInfoVehUsu(List<VehiculoUsuario> infoVehUsu) {
	this.infoVehUsu = infoVehUsu;
}

public AnnotateDataBinder getBinder() {
	return binder;
}

public void setBinder(AnnotateDataBinder binder) {
	this.binder = binder;
}

public Vehiculos getObjVehiculo() {
	return objVehiculo;
}

public void setObjVehiculo(Vehiculos objVehiculo) {
	this.objVehiculo = objVehiculo;
}

public Window getWinVehUser() {
	return winVehUser;
}

public void setWinVehUser(Window winVehUser) {
	this.winVehUser = winVehUser;
}
public List<Clientes> getUsu() {
	return usu;
}

public void setUsu(List<Clientes> usu) {
	this.usu = usu;
}

public List<Clientes> getUsuModel() {
	return usuModel;
}

public void setUsuModel(List<Clientes> usuModel) {
	this.usuModel = usuModel;
}

public MapUsuarioSistema getObjUsuarioSistema() {
	return objUsuarioSistema;
}

public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
	this.objUsuarioSistema = objUsuarioSistema;
}

public Listbox getListcli() {
	return listcli;
}

public void setListcli(Listbox listcli) {
	this.listcli = listcli;
}

public Caption getCptReportaVehUsuario() {
	return cptReportaVehUsuario;
}

public void setCptReportaVehUsuario(Caption cptReportaVehUsuario) {
	this.cptReportaVehUsuario = cptReportaVehUsuario;
}

public Label getLblNomVehiculo() {
	return lblNomVehiculo;
}

public void setLblNomVehiculo(Label lblNomVehiculo) {
	this.lblNomVehiculo = lblNomVehiculo;
}

public Label getLblPlaca() {
	return lblPlaca;
}

public void setLblPlaca(Label lblPlaca) {
	this.lblPlaca = lblPlaca;
}

public Listheader getLshUsuario() {
	return lshUsuario;
}

public void setLshUsuario(Listheader lshUsuario) {
	this.lshUsuario = lshUsuario;
}

public Button getBtnGuardar() {
	return btnGuardar;
}

public void setBtnGuardar(Button btnGuardar) {
	this.btnGuardar = btnGuardar;
}

public Button getBtnCancelar() {
	return btnCancelar;
}

public void setBtnCancelar(Button btnCancelar) {
	this.btnCancelar = btnCancelar;
}

}
