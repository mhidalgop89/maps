package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gpolyline;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Coordenadas;
import Util.MapUsuarioSistema;
import Util.ReadPropertiesUtil;
import Util.cercas;

public class RegistroMantenimientoCercasModalControl extends GenericForwardComposer{
	
	cercas objCerca= new cercas();
	Textbox nomCer;
	Textbox desCer;
	Textbox usuCer;
	Textbox estCer;
	Combobox cmbEstado;
	Gpolyline mypoly;
	Gmaps mymap;
	Groupbox grbManCer;
	Window winMantCercas;
	List<Coordenadas>infoCoordModal;
	private MapUsuarioSistema objUsuarioSistema = null;
	private Caption cptMantenimientoCerca;
	private Label lblNombre;
	private Label lblDescripcion;
	private Label lblUsuario;
	private Label lblEstado;
	private Button btnActualizar;
	private Button btnCancelar;
	AnnotateDataBinder binder;
	
	
	


	public void doAfterCompose(Component cmp){  	
	    	
	    	try
	    	{
	    		
	    		
	    		super.doAfterCompose(cmp);
	    		cmp.setAttribute("winMantCercas",this,true);
	    		//Ejecución de Ajax
	    		binder = new AnnotateDataBinder(cmp);
	    		objCerca=(cercas)arg.get("pCerca");
	    		if(objCerca!=null)
	    		{
	    		nomCer.setText(objCerca.getNombreCerca() );
	    		desCer.setText(objCerca.getDescripcionCerca());
	    		
	    		
	    		if(objCerca.getEstado().equals("A") )
	    			cmbEstado.setSelectedIndex(0);
	    		if(objCerca.getEstado().equals("I") )
	    			cmbEstado.setSelectedIndex(1);
	    		
	    		
	    		usuCer.setText( objCerca.getUsuario() );
	    		
	    		
	    		binder.loadAll();
	    		}
	    		getUserFromIndex();
	    		   
	    		if (objUsuarioSistema==null)
				{
						desconectar();
						return;
				}
	    		rutaMapa();
	    		cargarComponentes();
	    		binder.loadComponent(mymap);
	    		grbManCer.setOpen(true);
	    		binder.loadComponent(grbManCer);
	    		
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	
	public void cargarComponentes()
	{
		lblNombre.setValue( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Nombre", objUsuarioSistema.getIdioma()));
		lblDescripcion.setValue( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Descripcion", objUsuarioSistema.getIdioma()));
		lblUsuario.setValue( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Usuario", objUsuarioSistema.getIdioma()));
		lblEstado.setValue( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Estado", objUsuarioSistema.getIdioma()));
		
		btnActualizar.setLabel( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Actualizar", objUsuarioSistema.getIdioma()));
		btnCancelar.setLabel( ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.Cancelar", objUsuarioSistema.getIdioma()));
		

	}

	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	}
	

	public void desconectar()
	{
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.sendRedirect("index.zul");	
	}
	
	public void rutaMapa()
	{
		DatosDao dat = new DatosDao();
		int per_id=0;
		int usu_id=0;
		double lng=0;
		double ltd=0;
		boolean primerR=true;
		
		
		infoCoordModal =  new ArrayList<Coordenadas>();
		
		try{
			infoCoordModal = dat.obtenerRutasDao(objCerca.getIdCercas() );
			for(int i=0;i<infoCoordModal.size();i++)
			{
				if(primerR)
				{
					lng=infoCoordModal.get(i).getLongitud();
					ltd= infoCoordModal.get(i).getLatitud();
					primerR=false;
					mymap.panTo(ltd, lng);
				}
				mypoly.addPoint(infoCoordModal.get(i).getLatitud(),infoCoordModal.get(i).getLongitud(), 3);
			}
			mypoly.addPoint(ltd, lng, 3);
			Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.RegistroMantenimientoCercasModalControl.PresioneOk", objUsuarioSistema.getIdioma()),
					ReadPropertiesUtil.obtenerProperty("control.RegistroMantenimientoCercasModalControl.Atencion", objUsuarioSistema.getIdioma()),
					Messagebox.OK,Messagebox.INFORMATION);
		
		}
		catch(Exception e){e.printStackTrace();}		
		
		
		/*
	
			CategoriaVehiculo cVeh= new CategoriaVehiculo();
			ResultSet rs=null;
			Statement st=null;
			Connection conn= ConexionUtil.getConnection();
			boolean primerR=true;
			double lng=0;
			double ltd=0;
			
			
			try{
				
				st=conn.createStatement();
				rs=st.executeQuery(   "select ced_longitud, ced_latitud from cerca_detalle where ce_id= "+ objCerca.getIdCercas() +" order  by ced_orden" );
				System.out.println( "select ced_longitud, ced_latitud from cerca_detalle where ce_id= "+ objCerca.getIdCercas() +" order  by ced_orden"  );
				
				while(rs.next())
				{
					//System.out.println("cmb: "+String.valueOf( rs.getInt(1))+" - "+rs.getString(2)+" - "+rs.getString(3));
					
					if(primerR)
					{
						lng= rs.getDouble(1);
						ltd= rs.getDouble(2);
						primerR=false;
						mymap.panTo(ltd, lng);
					}
					mypoly.addPoint(rs.getDouble(2), rs.getDouble(1), 3);

				}
				
				mypoly.addPoint(ltd, lng, 3);
				
				
				Messagebox.show("Cerca generada con exito!!","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);
			}catch(Exception e ){e.printStackTrace();}*/
			
		
		
	}
	
	
	public void guardarCercas()
	{
		
		DatosDao dat = new DatosDao();
		dat.actualizaDatosCercasDao( nomCer.getText(), desCer.getText(), cmbEstado.getSelectedItem().getValue().toString(), objCerca.getIdCercas());
		Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.RegistrosMAntenimientosCercasModalControl.ActualizacionExitosa", objUsuarioSistema.getIdioma()),
				ReadPropertiesUtil.obtenerProperty("control.RegistroMantenimientoCercasModalControl.Atencion", objUsuarioSistema.getIdioma()),
				Messagebox.OK,Messagebox.INFORMATION);
		winMantCercas.detach();
		/*
		ResultSet rs;
		Statement st;
		int update=0;
		Connection conn= ConexionUtil.getConnection();
		try{
			123
			st=conn.createStatement();
			System.out.println("upda: "+"update cercas set ce_nombre= '"+ nomCer.getText() +"', ce_descripcion = '"+ desCer.getText() +"', estado='"+cmbEstado.getSelectedItem().getValue() +"' where ce_id ="+ objCerca.getIdCercas());
			update= st.executeUpdate("update cercas set ce_nombre= '"+ nomCer.getText() +"', ce_descripcion = '"+ desCer.getText() +"', estado='"+cmbEstado.getSelectedItem().getValue() +"' where ce_id ="+ objCerca.getIdCercas() );
			
		}catch(Exception e){e.printStackTrace();}
		
		if(update==1) {
			Messagebox.show("Actualizacion exitosa","Atencion",Messagebox.OK,Messagebox.INFORMATION);
			
			winMantCercas.detach();
		}*/
		
		
	}
	
	public void cerrarVentana()
	{
		
		winMantCercas.detach();
		
	}
	
	 public Groupbox getGrbManCer() {
			return grbManCer;
		}


		public void setGrbManCer(Groupbox grbManCer) {
			this.grbManCer = grbManCer;
		}


		public Window getWinMantCercas() {
			return winMantCercas;
		}


		public void setWinMantCercas(Window winMantCercas) {
			this.winMantCercas = winMantCercas;
		}


		public Gmaps getMymap() {
			return mymap;
		}


		public void setMymap(Gmaps mymap) {
			this.mymap = mymap;
		}


		public Gpolyline getMypoly() {
			return mypoly;
		}


		public void setMypoly(Gpolyline mypoly) {
			this.mypoly = mypoly;
		}


		public Combobox getCmbEstado() {
			return cmbEstado;
		}


		public void setCmbEstado(Combobox cmbEstado) {
			this.cmbEstado = cmbEstado;
		}


		public cercas getObjCerca() {
			return objCerca;
		}


		public void setObjCerca(cercas objCerca) {
			this.objCerca = objCerca;
		}


		public Textbox getNomCer() {
			return nomCer;
		}


		public void setNomCer(Textbox nomCer) {
			this.nomCer = nomCer;
		}


		public Textbox getDesCer() {
			return desCer;
		}


		public void setDesCer(Textbox desCer) {
			this.desCer = desCer;
		}


		public Textbox getUsuCer() {
			return usuCer;
		}


		public void setUsuCer(Textbox usuCer) {
			this.usuCer = usuCer;
		}


		public Textbox getEstCer() {
			return estCer;
		}


		public void setEstCer(Textbox estCer) {
			this.estCer = estCer;
		}


		public AnnotateDataBinder getBinder() {
			return binder;
		}


		public void setBinder(AnnotateDataBinder binder) {
			this.binder = binder;
		}

		public List<Coordenadas> getInfoCoordModal() {
			return infoCoordModal;
		}

		public void setInfoCoordModal(List<Coordenadas> infoCoordModal) {
			this.infoCoordModal = infoCoordModal;
		}

		public MapUsuarioSistema getObjUsuarioSistema() {
			return objUsuarioSistema;
		}

		public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
			this.objUsuarioSistema = objUsuarioSistema;
		}

		public Caption getCptMantenimientoCerca() {
			return cptMantenimientoCerca;
		}

		public void setCptMantenimientoCerca(Caption cptMantenimientoCerca) {
			this.cptMantenimientoCerca = cptMantenimientoCerca;
		}

		public Label getLblNombre() {
			return lblNombre;
		}

		public void setLblNombre(Label lblNombre) {
			this.lblNombre = lblNombre;
		}

		public Label getLblDescripcion() {
			return lblDescripcion;
		}

		public void setLblDescripcion(Label lblDescripcion) {
			this.lblDescripcion = lblDescripcion;
		}

		public Label getLblUsuario() {
			return lblUsuario;
		}

		public void setLblUsuario(Label lblUsuario) {
			this.lblUsuario = lblUsuario;
		}

		public Label getLblEstado() {
			return lblEstado;
		}

		public void setLblEstado(Label lblEstado) {
			this.lblEstado = lblEstado;
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
