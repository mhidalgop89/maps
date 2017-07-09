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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;






import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Clientes;
import Util.Grupo;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.Vehiculos;
import Util.cercas;

public class RegistroVehiculoModalControl extends GenericForwardComposer {
	
	private Window winmantveh;
	private Textbox txtNombre;
	private Textbox txtHoraUso;
	private Textbox txtEstado;
	private Doublebox dblRecorridoInicial;
	private List<cercas> cercas = new ArrayList<cercas>();
	private List<cercas> cercasModel = new ArrayList<cercas>();
	private cercas cerca=new cercas();
	private Combobox cmbCli;
	private Combobox cmbGru;
	private Combobox cmbCer;
	private Combobox cmbCat;
	CategoriaVehiculo objCat=new CategoriaVehiculo();
	cercas objCer;
	private List<Clientes> clientes = new ArrayList<Clientes>();
	Clientes objCli = new Clientes();
	private List<Clientes> clientesModel = new ArrayList<Clientes>();
	private List<CategoriaVehiculo> categoria = new ArrayList<CategoriaVehiculo>();
	private List<CategoriaVehiculo> categoriaModel = new ArrayList<CategoriaVehiculo>();
	
	private Vehiculos objVehiculo= new Vehiculos();
	private Doublebox txtRecorrido;
	private Textbox txtPlaca;
	private Textbox txtImei;
	private Textbox txtMantenimiento;
	private Intbox txtAnio;
	private Textbox txtAlias;
	private Textbox txtNotas;
	
	//Grupo
	private List<Grupo> infoGrupo=new ArrayList<Grupo>();
	private List<Grupo> infoGrupoModel=new ArrayList<Grupo>();
	private Grupo objGrupo=new Grupo();
	Checkbox cbxMantenimiento;
	MapUsuarioSistema objUsuarioSistema = null;
	private String estado;
	AnnotateDataBinder binder;
	
	//componentes
	private Label lblNombre;
	private Label lblUsuario;
	private Label lblCategoria;
	private Label lblImei;
	private Label lblRecorrido;
	private Label lblMantenimiento;
	private Label lblCercas;
	private Label lblGrupos;
	private Label lblHoraUso;
	private Label lblPlaca;
	
	private Label lblAnio;
	private Label lblAlias;
	private Label lblEstado;
	private Label lblRecorridoInicial;
	private Label lblNotas;
	private Button btnGuardar;
	private Button btnCancelar;
	private Button btnEstado;
	private Caption cptVehiculo;
	

	

	public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		
    		
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winmantveh",this,true);
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		getUserFromIndex();

    		FillClientes();
    		FillCategoria();
    		fillCercas();
    		fillGrupo();
    		cargarComponentes();
    		objVehiculo=(Vehiculos)arg.get("pVehiculo");
    		
    		if(objVehiculo!=null)
    		{System.out.println("entra if(objVehiculo!=null): "+objVehiculo);
    		btnEstado.setDisabled(false);
    			txtNombre.setText(objVehiculo.getNombre());
    			txtAnio.setText(objVehiculo.getVe_anio());
    			txtAlias.setText(objVehiculo.getVe_alias());
    			txtNotas.setText(objVehiculo.getVe_notas());
    			
    			cmbCli.setSelectedText(0, 1, objVehiculo.getUsuario(), true);
    			cmbCat.setSelectedText(0, 1, objVehiculo.getCategoria(), true);
    			objCat.setId_categoria(objVehiculo.getCatVeh());
    			cmbCer.setText(objVehiculo.getCercaNombre());
    			cerca.setIdCercas(objVehiculo.getIdCerca());
    			cerca.setNombreCerca(objVehiculo.getCercaNombre());
    			if(objVehiculo.getCercaNombre()==null || objVehiculo.getCercaNombre().equals(""))
    				cmbCer.setText("NINGUNA");
    			//grupo
    			cmbGru.setText(objVehiculo.getGrupo());
    			objGrupo.setGr_Id(objVehiculo.getGr_id());
    			objGrupo.setGr_descripcion(objVehiculo.getGrupo());
    			
    			
    			objCli.setUsu_id(objVehiculo.getIdUsu());
    			
    			
    			//objCer.setIdCercas(objVehiculo.getIdCerca());
    			//objCer.setNombreCerca(objVehiculo.getCercaNombre());
    			
    			txtHoraUso.setValue(String.valueOf(objVehiculo.getVe_horas_encendido()));
    			txtImei.setText(objVehiculo.getImei());
    			txtRecorrido.setText( String.valueOf(objVehiculo.getRecorrido()) );
    			txtPlaca.setText(objVehiculo.getPlaca());
    			
    			dblRecorridoInicial.setValue(objVehiculo.getRecorridoInicial());
    			dblRecorridoInicial.setReadonly(true);
    			
    			System.out.println("objVehiculo.getMantenimiento(): "+ objVehiculo.getMantenimiento());
    			if(objVehiculo.getMantenimiento().equals("SI"))
    			{System.out.println("cbxMantenimiento.setChecked(true): "+cbxMantenimiento.isChecked());
    				cbxMantenimiento.setChecked(true);
    				
    			}
    			else
    			{	
    				cbxMantenimiento.setChecked(false);
    			}
    			estado = (objVehiculo.getEstado().equals("A"))?"ACTIVO":"INACTIVO";
    			txtEstado.setText(estado);
    			
    			
    			binder.loadComponent(cbxMantenimiento);
    			
    			//txtMantenimiento.setText(objVehiculo.getMantenimiento() );
    			//cmbCli.
    		
    		}
    		else
    		{btnEstado.setDisabled(true);
    			objVehiculo=null;
    			//123
    			objCer = new cercas();
				objCer.setIdCercas(0);
    			objCer.setDescripcionCerca("NINGUNA");
    			objCer.setNombreCerca("NINGUNA");
    			cmbCer.setText("NINGUNA");
    			txtEstado.setText("ACTIVO");
    			
    			
    			
    		}
    		binder.loadAll();
    		
    		/*Session sess = HibernateSessionFactory.getSession();
    		UsuarioBo bo = new UsuarioBo();	
	
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);	    		
    		objUsuario=(FrUsuarios)arg.get("pUsuario");
    		//System.out.println("La mama de este.");
    		//System.out.println(objUsuario.getEstado());
    		if (objUsuario != null){
    		name.setReadonly(true);	
    		name.setValue(objUsuario.getIdUsuario());
    		pwd1.setValue(objUsuario.getIdContrasena());
    		pwd2.setValue(objUsuario.getIdContrasena());
    		nombreuser.setValue(objUsuario.getNombre());
    		apellidouser.setValue(objUsuario.getApellido());
	  		binder.loadAll();
    	}else{
    		name.setReadonly(false);
    		name.setFocus(true);
    		
    	}*/
    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	public void cargarComponentes()
	{

	
	lblNombre.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Nombre", objUsuarioSistema.getIdioma()));
	lblUsuario.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Usuario", objUsuarioSistema.getIdioma()));
	lblCategoria.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Categoria", objUsuarioSistema.getIdioma()));
	lblImei.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Imei", objUsuarioSistema.getIdioma()));
	lblRecorrido.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Recorrido", objUsuarioSistema.getIdioma()));
	lblMantenimiento.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Mantenimiento", objUsuarioSistema.getIdioma()));
	lblCercas.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Cercas", objUsuarioSistema.getIdioma()));
	lblGrupos.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Grupo", objUsuarioSistema.getIdioma()));
	lblHoraUso.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.HoraUso", objUsuarioSistema.getIdioma()));
	lblPlaca.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Placa", objUsuarioSistema.getIdioma()));
	lblAnio.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Anio", objUsuarioSistema.getIdioma()));
	lblAlias.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Alias", objUsuarioSistema.getIdioma()));
	lblEstado.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Estado", objUsuarioSistema.getIdioma()));
	lblRecorridoInicial.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.RecorridoInicial", objUsuarioSistema.getIdioma()));
	lblNotas.setValue(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Notas", objUsuarioSistema.getIdioma()));
	btnGuardar.setLabel(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Guardar", objUsuarioSistema.getIdioma()));
	btnCancelar.setLabel(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Cancelar", objUsuarioSistema.getIdioma()));
	btnEstado.setLabel(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.ActivarInactivar", objUsuarioSistema.getIdioma()));
	cptVehiculo.setLabel(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.Vehiculo", objUsuarioSistema.getIdioma()));
		
		
	}

	public void inactivar()
	{
		String estado="";
		estado = (objVehiculo.getEstado().equals("A"))?"I":"A";
		DatosDao dat = new DatosDao();
		dat.activarInactivarVehiculoDao(estado, objVehiculo.getVehId() );
		winmantveh.detach();
		
	/*Statement st=null;
	ResultSet rs=null;
	int update=0;
	Connection conn= ConexionUtil.getConnection();
	try {
		st=conn.createStatement();
	
	update= st.executeUpdate(" 	update vehiculos "+
			" set 	Estado='I' " 
			+" where 	ve_Id="+ objVehiculo.getVehId() +  " ;");
	conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}*/
	
	
	
	
	}
	public void fillGrupo()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		infoGrupo= new ArrayList<Grupo>();
		infoGrupoModel= new ArrayList<Grupo>();
		
		try{
			
			per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			infoGrupoModel = dat.obtenerGruVehDao();
			
			infoGrupo = infoGrupoModel;
			binder.loadComponent(cmbGru);
			
		
		}
		catch(Exception e){e.printStackTrace();}
		
	}
	
	public void fillCercas()
	{//123123
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		cercas cerVeh= new cercas();
		cercas= new ArrayList<cercas>();
		cercasModel=  new ArrayList<cercas>();
		
		try{
			cercasModel = dat.fillCercasDao(objUsuarioSistema);
			cerVeh.setIdCercas(0);
			cerVeh.setDescripcionCerca("NINGUNA");
			cerVeh.setNombreCerca("NINGUNA");
			
			cercasModel.add(0, cerVeh);
			
			//cercasModel.add(cerVeh);
			cercas = cercasModel;
			binder.loadComponent(cmbCer);
		
		}
		catch(Exception e){e.printStackTrace();}

	}
	
	
	
	public void FillClientes()
	{
		
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		clientes= new ArrayList<Clientes>();
		clientesModel=  new ArrayList<Clientes>();
		
		try{

			clientesModel = dat.FillClientesDao();
			
			clientes = clientesModel;
		/*for(int i=0;i<clientes.size();i++)
				System.out.println("clientes.get(i).getUsu_id(): "+clientes.get(i).getUsu_id());*/	
			
			binder.loadComponent(cmbCli);
		
		}
		catch(Exception e){e.printStackTrace();}

	}
	
	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (objUsuarioSistema==null)
			Executions.sendRedirect("DesconectaUsuario.zul");

	}
	
	
	public void FillCategoria()
	{
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		
		categoria= new ArrayList<CategoriaVehiculo>();
		categoriaModel=  new ArrayList<CategoriaVehiculo>();
		
		try{

			categoriaModel = dat.obtenerCatVehDao(objUsuarioSistema);
			
			categoria = categoriaModel;
			binder.loadComponent(cmbCat);
		
		}
		catch(Exception e){e.printStackTrace();}
		
	}
	
	
	public void guardarVehiculo()
	{//123123
		
		int anio=0;
		int cercaId=0;
		
		DatosDao datos= new DatosDao();
		
		try{
		if(txtNombre.getText().equals(null)|| txtNombre.getText().equals("") || cmbCli.getText().equals("")|| cmbCli.getSelectedIndex()==-1
				|| cmbCat.getText().equals("")|| cmbCat.getSelectedIndex()==-1 || txtImei.getText().equals("") || 
				txtRecorrido.getText().equals("") 
				//|| cmbCer.getSelectedIndex()==-1 
				//|| cmbCer.getText().equals("")
				||txtHoraUso.getValue().equals("")
				|| txtHoraUso==null || cmbGru.getSelectedIndex()==-1 || cmbGru.getText().equals("")
				|| dblRecorridoInicial.getText().equals(""))
		{
			Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.RegistroVehiculoModalControl.DebeLlenarTodosCampos",objUsuarioSistema.getIdioma()),
					ReadPropertiesUtil.obtenerProperty("control.VehiculoControl.Atencion",objUsuarioSistema.getIdioma()),
					Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);		
		}
		else
		{
			
			if (objVehiculo ==null)
			{
				cercaId = cerca==null?0:cerca.getIdCercas();
				anio = txtAnio.getText().equals("")?0:txtAnio.getValue() ;
				datos.guardaVehiculo(txtNombre.getValue(), objCli.getUsu_id(), objCat.getId_categoria(), "/Img/Ambulance.png", txtImei.getValue(),Double.valueOf(txtRecorrido.getValue()), cbxMantenimiento.isChecked(), 
						cercaId,//cerca.getIdCercas(), 
						Double.valueOf(txtHoraUso.getValue()),  txtPlaca.getText().toUpperCase(), objGrupo.getGr_Id(), anio, txtAlias.getText(), txtNotas.getText(),dblRecorridoInicial.getValue());
				
				
				winmantveh.detach();
			
				
			}
			else
			{			
				
				cercaId = cerca==null?0:cerca.getIdCercas();
				anio = txtAnio.getText().equals("")?0:txtAnio.getValue();
				datos.actualizaVehiculoDao(objVehiculo.getVehId(),  txtNombre.getValue(),  objCli.getUsu_id(), objCat.getId_categoria(), 
						Double.valueOf(txtRecorrido.getValue()), txtImei.getValue(), cbxMantenimiento.isChecked(), 
						cercaId,//cerca.getIdCercas(), 
						Double.valueOf(txtHoraUso.getValue()), 
						objGrupo.getGr_Id(), txtPlaca.getText().toUpperCase() , anio, txtAlias.getText(), txtNotas.getText());
				
				winmantveh.detach();	
				
			
			
			}
				
				
			}
			

		
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		//objVehiculo = new Vehiculos();
		
	}
	
	
	
	
	

	public void cerrarVentana(){
		 try{
			 winmantveh.detach();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	
	public Doublebox getDblRecorridoInicial() {
		return dblRecorridoInicial;
	}

	public void setDblRecorridoInicial(Doublebox dblRecorridoInicial) {
		this.dblRecorridoInicial = dblRecorridoInicial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Textbox getTxtEstado() {
		return txtEstado;
	}

	public void setTxtEstado(Textbox txtEstado) {
		this.txtEstado = txtEstado;
	}

	public Clientes getObjCli() {
		return objCli;
	}

	public void setObjCli(Clientes objCli) {
		this.objCli = objCli;
	}

	public List<Grupo> getInfoGrupoModel() {
		return infoGrupoModel;
	}

	public void setInfoGrupoModel(List<Grupo> infoGrupoModel) {
		this.infoGrupoModel = infoGrupoModel;
	}

	public List<cercas> getCercasModel() {
		return cercasModel;
	}

	public void setCercasModel(List<cercas> cercasModel) {
		this.cercasModel = cercasModel;
	}

	public List<Clientes> getClientesModel() {
		return clientesModel;
	}

	public void setClientesModel(List<Clientes> clientesModel) {
		this.clientesModel = clientesModel;
	}

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public List<CategoriaVehiculo> getCategoriaModel() {
		return categoriaModel;
	}

	public void setCategoriaModel(List<CategoriaVehiculo> categoriaModel) {
		this.categoriaModel = categoriaModel;
	}

	public Intbox getTxtAnio() {
		return txtAnio;
	}

	public void setTxtAnio(Intbox txtAnio) {
		this.txtAnio = txtAnio;
	}

	public Textbox getTxtAlias() {
		return txtAlias;
	}

	public void setTxtAlias(Textbox txtAlias) {
		this.txtAlias = txtAlias;
	}

	public Textbox getTxtNotas() {
		return txtNotas;
	}

	public void setTxtNotas(Textbox txtNotas) {
		this.txtNotas = txtNotas;
	}

	public Combobox getCmbGru() {
		return cmbGru;
	}

	public void setCmbGru(Combobox cmbGru) {
		this.cmbGru = cmbGru;
	}

	public List<Grupo> getInfoGrupo() {
		return infoGrupo;
	}

	public void setInfoGrupo(List<Grupo> infoGrupo) {
		this.infoGrupo = infoGrupo;
	}

	public Grupo getObjGrupo() {
		return objGrupo;
	}

	public void setObjGrupo(Grupo objGrupo) {
		this.objGrupo = objGrupo;
	}

	public Button getBtnEstado() {
		return btnEstado;
	}

	public void setBtnEstado(Button btnEstado) {
		this.btnEstado = btnEstado;
	}

	public Textbox getTxtPlaca() {
		return txtPlaca;
	}

	public void setTxtPlaca(Textbox txtPlaca) {
		this.txtPlaca = txtPlaca;
	}

	public Textbox getTxtHoraUso() {
		return txtHoraUso;
	}

	public void setTxtHoraUso(Textbox txtHoraUso) {
		this.txtHoraUso = txtHoraUso;
	}

	public cercas getObjCer() {
		return objCer;
	}

	public void setObjCer(cercas objCer) {
		this.objCer = objCer;
	}

	public cercas getCerca() {
		return cerca;
	}

	public void setCerca(cercas cerca) {
		this.cerca = cerca;
	}

	public CategoriaVehiculo getObjCat() {
		return objCat;
	}

	public void setObjCat(CategoriaVehiculo objCat) {
		this.objCat = objCat;
	}

	public List<cercas> getCercas() {
		return cercas;
	}

	public void setCercas(List<cercas> cercas) {
		this.cercas = cercas;
	}

	public Combobox getCmbCer() {
		return cmbCer;
	}

	public void setCmbCer(Combobox cmbCer) {
		this.cmbCer = cmbCer;
	}

	public Checkbox getCbxMantenimiento() {
		return cbxMantenimiento;
	}

	public void setCbxMantenimiento(Checkbox cbxMantenimiento) {
		this.cbxMantenimiento = cbxMantenimiento;
	}

	public Textbox getTxtMantenimiento() {
		return txtMantenimiento;
	}

	public void setTxtMantenimiento(Textbox txtMantenimiento) {
		this.txtMantenimiento = txtMantenimiento;
	}


	public Doublebox getTxtRecorrido() {
		return txtRecorrido;
	}

	public void setTxtRecorrido(Doublebox txtRecorrido) {
		this.txtRecorrido = txtRecorrido;
	}

	public Textbox getTxtImei() {
		return txtImei;
	}

	public void setTxtImei(Textbox txtImei) {
		this.txtImei = txtImei;
	}

	public List<CategoriaVehiculo> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<CategoriaVehiculo> categoria) {
		this.categoria = categoria;
	}

	public Combobox getCmbCat() {
		return cmbCat;
	}

	public void setCmbCat(Combobox cmbCat) {
		this.cmbCat = cmbCat;
	}

	public Vehiculos getObjVehiculo() {
		return objVehiculo;
	}

	public void setObjVehiculo(Vehiculos objVehiculo) {
		this.objVehiculo = objVehiculo;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Combobox getCmbCli() {
		return cmbCli;
	}

	public void setCmbCli(Combobox cmbCli) {
		this.cmbCli = cmbCli;
	}

	public List<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(List<Clientes> clientes) {
		this.clientes = clientes;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public Caption getCptVehiculo() {
		return cptVehiculo;
	}

	public void setCptVehiculo(Caption cptVehiculo) {
		this.cptVehiculo = cptVehiculo;
	}

	public Window getWinmantveh() {
		return winmantveh;
	}

	public void setWinmantveh(Window winmantveh) {
		this.winmantveh = winmantveh;
	}


}
