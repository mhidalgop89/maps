package ucsg.gmaps.control;

import java.sql.Connection; //import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.gmaps.Ginfo;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolyline;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.util.WaitLock;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events; //import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.sys.EventListenerMap;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.acegi.ShowWindowEventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.South;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Timer;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;
import org.zkoss.zk.ui.util.Clients;

import ucsg.gmaps.dao.DatosDao;



import ConexionUtil.ConexionUtil;
import Util.CategoriaVehiculo;
import Util.Coordenadas;
import Util.MapUsuarioSistema;
import Util.Mapas;
import Util.Marks;
import Util.PosicionesMapa;
import Util.ReadPropertiesUtil;


public class MapaGoogle3Control extends GenericForwardComposer {

	private Fisheyebar fsb;
	private Gmaps myMap;
	private Gmarker mymark;
	private Gmarker mymark2;
	private Gmarker mymark3;
	private Gmarker mymark4 = new Gmarker();
	
	private Caption cptMapasGenerados;
	
	Ginfo info;
	private List<Gmarker> listMarks = new ArrayList<Gmarker>();
	private List<Marks> gMark = new ArrayList<Marks>();
	private List<Marks> gMarkAll = new ArrayList<Marks>();
	private List<CategoriaVehiculo> catVeh = new ArrayList<CategoriaVehiculo>();
	private List<CategoriaVehiculo> catVehModel = new ArrayList<CategoriaVehiculo>();
	
	private List<CategoriaVehiculo> catVehLlenLis = new ArrayList<CategoriaVehiculo>();
	private List<Coordenadas> infoCoor;// = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorFil;// = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorOnMap = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorModelOnMap = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorModel;

	private List<Coordenadas> infoCoorTotales = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorCompletas = new ArrayList<Coordenadas>();

	private List<Mapas> infoMapas;
	Div idSouthdiv1;
	Mapas objMap;
	Combobox cmbCantMap;

	CategoriaVehiculo objCatVeh;// = new CategoriaVehiculo();
	CategoriaVehiculo objVehEnUso = new CategoriaVehiculo();
	CategoriaVehiculo objCatVehAnt = new CategoriaVehiculo();
	CategoriaVehiculo objCatSelected = new CategoriaVehiculo();
	private West westMapaGoogle3;
	private South southMapaGoogle3;
	private Button btnNueRut;
	
	private Listheader lshLatitud;
	private Listheader lshLongitud;
	private Listheader lshFecha;

	private Listbox lisVehiculo = null;
	//private String _user;
	String date = "";
	private Window winMapGoogle2;
	int idCoordenadas = 0;
	private Combobox cmbCatVeh;
	private String categoria = "0";
	private Gmaps myMapAll;
	Ginfo myinfo;
	private Panelchildren pcGmaps;
	private String resVeh = null;
	Datebox fecDesde;
	Datebox fecHasta;
	Datebox fecDesdeDia;
	private Gpolyline mypoly;
	private Gpolyline mypoly2;
	private Coordenadas objCoordenadas;
	private Listbox listCoord;
	private Listbox listCantMap;
	
	private Caption cptBusquedaAvanzada;
	private Tab tabPorDia;
	private Tab tabAvanzada;
	private Label lblDia;
	private Image imgTrazarRuta6;
	private Label lbldesde;
	private Label lblHasta;
	private Image imgTrazaRuta7;
	private Listheader lshVehiculo;
	private Image imgBusquedaAvanzada;
	private Image imgReporteHoy;
	private Image imgLocalizarVehiculo;
	private Button btnFiltrar;
	
	public Button getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(Button btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	boolean mapaEnUso = false;
	private West west;
	Tabbox tbx1;
	Borderlayout bly1;
	Center cnt1;
	Panel pnl1;
	Tab plusTab;
	Tabpanel tp1;
	Tabpanels tps1;
	Groupbox grbBusAva;
	Timer timer;
	Timer timer2;
	Timer timerRuta;
	int idVehiculo_Res = 0;
	int contRuta = 0;
	Div divControles;

	int idCatVehiculoAnt = 0;
	double longAnt = 1000000;
	double latAnt = 1000000;
	Coordenadas coord;
	Coordenadas coordOnMap;
	MapUsuarioSistema objUsuarioSistema = null;
	AnnotateDataBinder binder = null;

	

	public void fillListBox(double ltd, double lng, String img, int idVeh,
			String nombreVehiculo) {

		Marks mark1 = new Marks();
		mark1.setLtd(ltd);
		mark1.setLng(lng);
		mark1.setImage(img);
		mark1.setId_vehiculo(idVeh);
		mark1.setNombreVehiculo(nombreVehiculo);
		System.out.println("system llega::");
		binder.loadComponent(lisVehiculo);

		gMark.add(mark1);

	}

	public void doAfterCompose(Component cmp) {

		try {
			super.doAfterCompose(cmp);
			cmp.setAttribute("winMapGoogle2", this, true);
			// System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
			// Ejecución de Ajax
			binder = new AnnotateDataBinder(cmp);
			getUserFromIndex();
			if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
			
			cargaComponentes();
			
			
			objCatVehAnt.setIdVeh(0);
			fillCmbVehCat();
			/*
			 * System.out.println("theme: saphire");
			 * Themes.setTheme(Executions.getCurrent(), "sapphire");
			 * Executions.sendRedirect(null);
			 */
			timer2.stop();
			cleanMap();
			mymark.setOpen(true);
			// mymark2.setOpen(true);
			// binder.loadComponent(ComAmb);
			// binder.loadComponent(cmbTramite);

			// binder.loadComponent(lisVehiculo);
			binder.loadComponent(mymark);
			binder.loadComponent(cmbCatVeh);
			binder.loadComponent(fecDesdeDia);
			
			presentaVehiculo();
			// binder.loadAll();
		} catch (Exception e) {
			System.out.println("doAfterCompose Exception");
			e.printStackTrace();
		}
	}

	public void getUserFromIndex() {
		
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
	}
	public void desconectar()
	{

		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.sendRedirect("index.zul");
		
	}
	public void cargaComponentes()
	{
		westMapaGoogle3.setTitle(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.ListaVehiculos", objUsuarioSistema.getIdioma()));
		cptBusquedaAvanzada.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.BusquedaAvanzada", objUsuarioSistema.getIdioma()));
		tabPorDia.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.PorDia", objUsuarioSistema.getIdioma()));
		tabAvanzada.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Avanzada", objUsuarioSistema.getIdioma()));
		lblDia.setValue(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Dia", objUsuarioSistema.getIdioma()));
		imgTrazarRuta6.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.PresioneParaBuscar1", objUsuarioSistema.getIdioma()));
		lbldesde.setValue(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Desde", objUsuarioSistema.getIdioma()));
		lblHasta.setValue(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Hasta", objUsuarioSistema.getIdioma()));
		imgTrazaRuta7.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.PresioneParaBuscar2", objUsuarioSistema.getIdioma()));
		lshVehiculo.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Vehiculo", objUsuarioSistema.getIdioma()));
		imgBusquedaAvanzada.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.BusquedaAvanzada2", objUsuarioSistema.getIdioma()));
		imgReporteHoy.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.ReporteHoy", objUsuarioSistema.getIdioma()));
		imgLocalizarVehiculo.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.LocalizarVehiculo", objUsuarioSistema.getIdioma()));
		btnFiltrar.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Filtrar", objUsuarioSistema.getIdioma()));
		
		lshFecha.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Fecha", objUsuarioSistema.getIdioma()));
		lshLongitud.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Longitud", objUsuarioSistema.getIdioma()));
		lshLatitud.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.Latitud", objUsuarioSistema.getIdioma()));
		cptMapasGenerados.setLabel(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.MapasGenerados", objUsuarioSistema.getIdioma()));
		
	}

	public void presentaVehiculo() {
		timer2.stop();
		cleanMap();
		
		List<CategoriaVehiculo> catVehLlenLisModel =null;
		
		DatosDao dat = new DatosDao();
		CategoriaVehiculo cVeh= new CategoriaVehiculo();
		catVehLlenLis=  new ArrayList<CategoriaVehiculo>();
		catVehLlenLisModel=  new ArrayList<CategoriaVehiculo>();
		
		try{
			
			
			catVehLlenLisModel = dat.presentaVehiculoDao(objUsuarioSistema);
			
			for(int i=0;i<catVehLlenLisModel.size();i++)
				if (catVehLlenLisModel.get(i).getId_categoria() == objCatSelected.getId_categoria() 
						|| objCatSelected.getId_categoria() == 0)
					catVehLlenLis.add(catVehLlenLisModel.get(i));
			
						
			binder.loadComponent(lisVehiculo);			

		}
		catch(Exception e){e.printStackTrace();}
		
		
		
		
		
		/*123
		CategoriaVehiculo cv1 = new CategoriaVehiculo();
		//int adm = 0;
		//int usu_id = 0;

		Statement st_us = null;
		ResultSet rs_us = null;

		Statement st = null;
		ResultSet rs = null;
		MapaGoogle2Control mgc2 = new MapaGoogle2Control();
		

		categoria = ((cmbCatVeh.getSelectedIndex() == 0) || (cmbCatVeh
				.getSelectedIndex() == -1)) ? "0" : cmbCatVeh.getSelectedItem()
				.getContext();
		
		Connection conn = ConexionUtil.getConnection();

		try {

			
			st = conn.createStatement();

			System.out
					.println(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user  "
							+ " from vehiculos veh "
							+ " inner join usuarios usu on veh.usu_id= usu.usu_id "
							+ " inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "
							+ objUsuarioSistema.getUsu_id()
							+ "   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "
							+ objUsuarioSistema.getUsu_id() + "  group by veh.ve_id ");
			rs = st
					.executeQuery(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user  "
							+ " from vehiculos veh "
							+ " inner join usuarios usu on veh.usu_id= usu.usu_id "
							+ " inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "
							+ objUsuarioSistema.getUsu_id()
							+ "   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "
							+ objUsuarioSistema.getUsu_id() + "  group by veh.ve_id");

			while (rs.next()) {
				cv1.setNomVeh(rs.getString("nombreVehiculo"));
				cv1.setId_categoria(rs.getInt("id_categoria"));
				cv1.setIdVeh(rs.getInt("idVehiculo"));
				cv1.setImaVeh(rs.getString("imagen"));
				System.out.println("cv1.getId_categoria(): "
						+ cv1.getId_categoria()
						+ " objCatSelected.getId_categoria: "
						+ objCatSelected.getId_categoria());
				// if(rs.getString("user").equals(_user)|| adm==1)
				if (cv1.getId_categoria() == objCatSelected.getId_categoria()
						|| objCatSelected.getId_categoria() == 0)
					catVehLlenLis.add(cv1);
				cv1 = new CategoriaVehiculo();

			}

			binder.loadComponent(lisVehiculo);
			catVehLlenLis = new ArrayList<CategoriaVehiculo>();
			// gMark =new ArrayList<Marks>();

		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public void presentaVehiculo1(double ltd, double lng) {
		System.out.println("ltd: " + ltd + " lng: " + lng);

		try {

			myMap.setLat(ltd);
			myMap.setLng(lng);
			// myMap.panTo(ltd, lng);
			myMap.setZoom(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTimer() {
		// if(resVeh != null)
		// TrazarRuta(resVeh);
		TrazarRuta();

	}

	public void nuevaRuta() {

		Executions.sendRedirect("MapaGoogle3.zul");
	}

	public Date restaDia(Date fec, int cantDias) {
		Date fecRet = new Date();

		int diferenciaEnDias = cantDias;
		long tiempoActual = fec.getTime();
		long dias = diferenciaEnDias * 24 * 60 * 60 * 1000;
		fecRet = new Date(tiempoActual - dias);

		/*
		 * Date fechaActual = (Date) Calendar.getInstance().getTime(); long
		 * tiempoActual = fechaActual.getTime(); long unDia = diferenciaEnDias *
		 * 24 * 60 * 60 * 1000; Date fechaAyer = new Date(tiempoActual - unDia);
		 * 
		 * System.out.println(fechaAyer.toString());
		 */
		System.out.println("fecRet::::    " + fecRet.toString());

		return fecRet;
	}

	// String idVeh
	public void TrazarRuta3() {
		timer2.stop();
		cleanMap();
		if (mapaEnUso) {
			Messagebox
					.show(
							"El mapa se encuentra en uso por el vehiculo: "
									+ objVehEnUso.getNomVeh()
									+ " \n En caso de desear una nueva busqueda, debera presionar el boton 'Nueva Ruta'.",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
			btnNueRut
					.setPopup("Presione para trazar una nueva Ruta de vehiculo");
			btnNueRut.setFocus(true);

		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			int dias = 0;
			Date fecAnt = new Date();

			if (!(fecDesde.getText().equals("") || fecHasta.getText()
					.equals(""))) {

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {
					// System.out.println("leeega: !fecAnt.before(fecDesde.getValue())<---------------------"
					// );
					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;

					// System.out.println("myMapAll.getChildren() "+myMapAll.getChildren().size());

					myMapAll.getChildren().clear();

					binder.loadComponent(myMapAll);
					myMap.setVisible(false);
					myMapAll.setVisible(true);

					// System.out.println("allCarsOnMapAll");

					int count = -1;
					int cantReg = 0;
					Statement st = null;
					ResultSet rs = null;

					Statement st1 = null;
					ResultSet rs1 = null;

					MapaGoogle2Control mgc2 = new MapaGoogle2Control();

					myMapAll.removeChild(mymark2);

					mymark2.setOpen(true);
					Connection conn = ConexionUtil.getConnection();

					try {

						//_user = (String) Executions.getCurrent().getSession().getAttribute("_user");
						
						st = conn.createStatement();

						String sql1 = null;
						st1 = conn.createStatement();

						rs1 = st1
								.executeQuery("select count(distinct(concat(coo.co_latitud,coo.co_longitud))) dist "
										+ " from coordenadas coo "
										+ " where coo.ve_id= "
										+ objCatVeh.getIdVeh()
										+ " and co_fecha>= '"
										+ fecDesde.getText()
										+ "' "
										+ " and co_fecha<= '"
										+ fecHasta.getText()
										+ "' and coo.co_Velocidad > 0.00; ");

						while (rs1.next()) {
							cantReg = rs1.getInt(1);

						}

	
						rs = st
								.executeQuery("select distinct(concat(coo.co_latitud,coo.co_longitud)) dist  ,coo.co_latitud, coo.co_longitud,  coo.co_Id, coo.co_Velocidad, coo.co_fecha "
										+ " from coordenadas coo "
										+ " where coo.ve_id= "
										+ objCatVeh.getIdVeh()
										+ " and co_fecha>= '"
										+ fecDesde.getText()
										+ "' "
										+ " and co_fecha<= '"
										+ fecHasta.getText()
										+ "'  "
										+ " group by coo.co_latitud, coo.co_longitud,  coo.co_Id  order by co_fecha desc;");

						// System.out.println("funk ocall 2");

						if (rs.equals(null) || cantReg == 0) {
							Messagebox.show("No existen Registros",
									"Atención!!!", Messagebox.OK
											| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();

						}
						myMapAll.appendChild(mypoly);
						Coordenadas coord = new Coordenadas();
						infoCoor = new ArrayList<Coordenadas>();

						// 123
						double cantTabs = 0;
						cantTabs = Math.ceil(cantReg / 80);
						boolean pasa = true;
						List<Gmaps> listMap = new ArrayList<Gmaps>();
						List<Tabpanel> listTab = new ArrayList<Tabpanel>();
						for (int i = 0; i <= cantTabs; i++) {
							Gmaps mapaRuta = new Gmaps();
							mapaRuta.setId("gmap" + String.valueOf(i));
							listMap.add(mapaRuta);
							Tabpanel newTabpanel = new Tabpanel();
							newTabpanel.setId("tabPanel" + i);
							newTabpanel.setWidth("100%");
							newTabpanel.setHeight("100%");
							listTab.add(newTabpanel);

						}
						int contTabs = 0;
						// 123 tr3
						while (rs.next()/* &&canVeh<=80 */) {
							if (canVeh == 0 || canVeh == 79)
							// if(canVeh==0 || canVeh==99)
							{
								// new tab 3
								if (canVeh == 79) {
									// myMapAll =(Gmaps)
									// listMap.get(contTabs).clone();
									// myMapAll.setId(rs.getString(2)+rs.getString(3));
									// listTab.get(contTabs).appendChild(myMapAll);
									contTabs++;
								}

								Tab newTab = new Tab("New Tab XP " /* + i */);
								newTab.setSelected(true);
								// /Tabpanel newTabpanel = new Tabpanel();
								// /newTabpanel.appendChild(listMap.get(contTabs)/*myMapAll*/);
								listTab.get(contTabs).appendChild(
										listMap.get(contTabs));
								// /newTabpanel.setWidth("100%");
								// /newTabpanel.setHeight("100%");
								// winMapGoogle2.appendChild(tbx1);
								tbx1.getTabs().insertBefore(newTab, plusTab);
								// newTabpanel.setParent(tbx1.getTabpanels());
								listTab.get(contTabs).setParent(
										tbx1.getTabpanels());
								canVeh = 0;
							}

							pasa = false;

							if (anteriorMayorCero) {

								mymark2.setOpen(true);

								mymark2.setLat(Double.parseDouble(rs
										.getString(2)));
								mymark2.setLng(Double.parseDouble(rs
										.getString(3)));
								// mymark2.setContent(
								// objCatVeh.getNomVeh()+"\n Velocidad:"+
								// rs.getDouble(5));//contentHtml
								mymark2
										.setContent("<table border='1'> "
												+ " <tr> "
												+ " <th bgcolor='#ccccff' colspan=2 align=center>"
												+ objCatVeh.getNomVeh()
												+ "</th> "
												+ " </tr> "
												+ " <tr> "
												+ " <td> Lat: </td><td>"
												+ rs.getString(2)
												+ "</td> "
												+ " </tr> "
												+ " <tr> "
												+ " <td>Lng: </td><td>"
												+ rs.getString(3)
												+ "</td> "
												+ " </tr> "
												+ " <tr> "
												+ " <td>Velocidad: </td><td>"
												+ rs.getDouble(5)
												+ "</td> "
												+ " </tr> "
												+ " <tr> "
												+ " <td>Fecha: </td><td>"
												+ rs.getDate(6)
												+ "</td> "
												+ " </tr> " + " </table>");

								mymark2.setDraggingEnabled(false);
								binder.loadComponent(mymark2);
								binder.loadAttribute(mymark2, "Context");
								binder.loadAttribute(mymark2, "Content");

								// myMapAll.appendChild(mymark2);
								listMap.get(contTabs).appendChild(mymark2);
								mymark2.setOpen(true);
								mymark2 = new Gmarker();
								// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh());
								if (objCatVehAnt.getIdVeh() == objCatVeh
										.getIdVeh()
										|| objCatVehAnt.getIdVeh() == 0) {
									mypoly.setColor("#25FC04");// #25FC04
									mypoly
											.addPoint(/* ltd, lng, */Double
													.parseDouble(rs
															.getString(2)),
													Double.parseDouble(rs
															.getString(3)), 3);
								}

								coord.setLatitud(Double.parseDouble(rs
										.getString(2)));
								coord.setLongitud(Double.parseDouble(rs
										.getString(3)));
								coord.setFecha(rs.getTimestamp(6));

								infoCoor.add(coord);
								coord = new Coordenadas();
								canVeh++;
								anteriorMayorCero = false;
								pasa = true;
							}

							if (rs.getDouble(5) > 0 && !pasa) {

								// System.out.println("--allCarsOnMap()mp2: "+" lat: "+rs.getString(2)+" lng: "+rs.getString(3));
								mymark2.setOpen(true);

								// mymark2.setIconImage(rs.getString(3));
								// mymark2.setIconImageMap(rs.getString(3));
								mymark2.setLat(Double.parseDouble(rs
										.getString(2)));
								mymark2.setLng(Double.parseDouble(rs
										.getString(3)));
								mymark2.setContext(objCatVeh.getNomVeh());
								// mymark2.setContent(objCatVeh.getNomVeh());
								mymark2.setDraggingEnabled(false);
								binder.loadComponent(mymark2);
								binder.loadAttribute(mymark2, "Context");
								// binder.loadAttribute(mymark2, "Content");
								// myMapAll.appendChild(mymark2);
								listMap.get(contTabs).appendChild(mymark2);
								mymark2.setOpen(true);
								mymark2 = new Gmarker();
								// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh()
								// +"--"+objCatVeh.getIdVeh() );
								if (objCatVehAnt.getIdVeh() == objCatVeh
										.getIdVeh()
										|| objCatVehAnt.getIdVeh() == 0) {
									mypoly.setColor("#25FC04");// #25FC04
									mypoly
											.addPoint(/* ltd, lng, */Double
													.parseDouble(rs
															.getString(2)),
													Double.parseDouble(rs
															.getString(3)), 3);

								}
								coord.setLatitud(Double.parseDouble(rs
										.getString(2)));
								coord.setLongitud(Double.parseDouble(rs
										.getString(3)));
								coord.setFecha(rs.getTimestamp(6));
								infoCoor.add(coord);
								coord = new Coordenadas();

								canVeh++;

								anteriorMayorCero = true;

							}
							objCatVehAnt = objCatVeh;

							winMapGoogle2.appendChild(bly1);
							bly1.appendChild(cnt1);
							// cnt1.appendChild(pnl1);
							pnl1.appendChild(pcGmaps);
							pcGmaps.appendChild(tbx1);
							// tbx1.appendChild(myMapAll); //Unsupported child
							// for tabbox: <Gmaps hX2Qf#myMapAll>
							tbx1.appendChild(tps1);
							tps1.appendChild(tp1);
							// tp1.appendChild(myMapAll);
							tp1.appendChild(listMap.get(contTabs));

						}

						binder.loadAll();

						// myMapAll.setZoom(9);
						// myMapAll.appendChild(mymark2);
						listMap.get(contTabs).setZoom(9);
						listMap.get(contTabs).appendChild(mymark2);
						binder.loadComponent(mymark2);

						// binder.loadComponent(myMapAll);
						binder.loadComponent(listMap.get(contTabs));
						binder.loadComponent(listCoord);

						if (cantReg > 80)
							Messagebox
									.show(
											"Se ha generado "
													+ cantReg
													+ " registros, \n Solo se muestran los 80 registros mas recientes",
											"Atención!!!", Messagebox.OK
													| Messagebox.CANCEL,
											Messagebox.INFORMATION);

					} catch (Exception e) {
						e.printStackTrace();
					}

					// objCatVehAnt = objCatVeh;
					westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
				} else {
					westMapaGoogle3.setOpen(true);

					Messagebox.show("No mas de 1 dia en rango de fechas",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
					mapaEnUso = false;

				}

			} else {
				Messagebox.show("Debe llenar campos de fecha", "Atención!!!",
						Messagebox.OK | Messagebox.CANCEL,
						Messagebox.INFORMATION);
				mapaEnUso = false;

			}

		}

	}

	public void llamaOtrasRutas() {
		timer2.stop();
		cleanMap();
		TrazarRuta4();
		// binder.loadAll();
	}

	public void TrazarRuta4() {
		timer2.stop();
		stopTimerRuta();
		cleanMap();
		if (!mapaEnUso) {
			Messagebox
					.show(
							"El mapa se encuentra en uso por el vehiculo: "
									+ objVehEnUso.getNomVeh()
									+ " \n En caso de desear una nueva busqueda, debera presionar el boton 'Nueva Ruta'.",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
			btnNueRut
					.setPopup("Presione para trazar una nueva Ruta de vehiculo");
			btnNueRut.setFocus(true);

		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			int dias = 0;
			Date fecAnt = new Date();

			if (!(fecDesde.getText().equals("") || fecHasta.getText()
					.equals(""))) {

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {

					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;


					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();

					mypoly = new Gpolyline();// 12123
					mymark2 = new Gmarker();
					myMapAll.appendChild(mymark2);


					myMap.setVisible(false);

					myMapAll.setVisible(true);

					int count = -1;
					int cantReg = 0;
					
					DatosDao dat=new DatosDao();

					
					PosicionesMapa posicionesOptimas= new PosicionesMapa();
					infoCoorTotales = new ArrayList<Coordenadas>();
					infoCoorCompletas= new ArrayList<Coordenadas>();
//					infoCoorTotales = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					infoCoorCompletas = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					if(infoCoorCompletas .size()>100)
						infoCoorTotales = posicionesOptimas.retornaPosicionesOptimas(infoCoorCompletas);
					else
						infoCoorTotales = infoCoorCompletas;
					
					
					
					cantReg = infoCoorTotales.size();
					
					MapaGoogle2Control mgc2 = new MapaGoogle2Control();


					mymark2.setOpen(true);

					try {

						//_user = (String) Executions.getCurrent().getSession().getAttribute("_user");

						if(infoCoorTotales.size()<=0){
							Messagebox.show("No existen Registros",
									"Atención!!!", Messagebox.OK
											| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();

						}
						myMapAll.appendChild(mypoly);// 123123
						Coordenadas coord = new Coordenadas();
						infoCoor = new ArrayList<Coordenadas>();
						infoCoorModel = new ArrayList<Coordenadas>();

						boolean pasa = true;
						
						int numMap = 0;
						int contVeh = 0;

						for(int i=0;i<infoCoorTotales.size();i++){
							
							if (contVeh == 100) {
								numMap++;
								contVeh = 0;
							}
							contVeh++;
							
							if (numMap == objMap.getIdGmap()) {
								
								
								mymark2 = new Gmarker();
								if (canVeh == 0) {
									mymark2.setIconImage("/Img/yellow-dot.png");
								}else{
									mymark2.setIconImage("/Img/icon-gmaps-marker-circle.png");
								}
								
								pasa = false;

								if (anteriorMayorCero) {
									mymark2.setOpen(true);

									mymark2.setLat(infoCoorTotales.get(i).getLatitud());
									mymark2.setLng(infoCoorTotales.get(i).getLongitud());

									
									mymark2.setDraggingEnabled(false);
									binder.loadComponent(mymark2);

									myMapAll.appendChild(mymark2);
									mymark2.setOpen(true);
									mymark2 = new Gmarker();
									
									if (objCatVehAnt.getIdVeh() == objCatVeh
											.getIdVeh()
											|| objCatVehAnt.getIdVeh() == 0) {
										mypoly.setColor("#25FC04");// #25FC04
										mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(), 3);
									}

									coord.setLatitud(infoCoorTotales.get(i).getLatitud());
									coord.setLongitud(infoCoorTotales.get(i).getLongitud());
									coord.setFecha(infoCoorTotales.get(i).getFecha());

									infoCoor.add(coord);
									coord = new Coordenadas();
									canVeh++;
									anteriorMayorCero = false;
									pasa = true;
								}

								if (infoCoorTotales.get(i).getVelocidad()> 0 && !pasa) {

									mymark2.setOpen(true);
									mymark2.setLat(infoCoorTotales.get(i).getLatitud());
									mymark2.setLng(infoCoorTotales.get(i).getLongitud());

									mymark2.setDraggingEnabled(false);

									/*if (canVeh == 80) {
										// mymark2.setContent(
										// objCatVeh.getNomVeh()+"\n Velocidad:"+
										// rs.getDouble(5));//contentHtml
										mymark2
												.setContent("<table border='1'> "
														+ " <tr> "
														+ " <th bgcolor='#ccccff' colspan=2 align=center>"
														+ objCatVeh.getNomVeh()
														+ "</th> "
														+ " </tr> "
														+ " <tr> "
														+ " <td> Lat: </td><td>"
														+ rs.getString(2)
														+ "</td> "
														+ " </tr> "
														+ " <tr> "
														+ " <td>Lng: </td><td>"
														+ rs.getString(3)
														+ "</td> "
														+ " </tr> "
														+ " <tr> "
														+ " <td>Velocidad: </td><td>"
														+ rs.getDouble(5)
														+ "</td> "
														+ " </tr> "
														+ " <tr> "
														+ " <td>Fecha: </td><td>"
														+ rs.getDate(6)
														+ "</td> "
														+ " </tr> "
														+ " </table>");
										binder
												.loadAttribute(mymark2,
														"Content");
									}*/

									binder.loadComponent(mymark2);
									myMapAll.appendChild(mymark2);
									mymark2.setOpen(true);
									mymark2 = new Gmarker();
									
									if (objCatVehAnt.getIdVeh() == objCatVeh
											.getIdVeh()
											|| objCatVehAnt.getIdVeh() == 0) {
										mypoly.setColor("#25FC04");// #25FC04
										mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(), 3);

									}
									coord.setLatitud(infoCoorTotales.get(i).getLatitud());
									coord.setLongitud(infoCoorTotales.get(i).getLongitud());
									coord.setFecha(infoCoorTotales.get(i).getFecha());
									infoCoor.add(coord);
									coord = new Coordenadas();

									canVeh++;

									anteriorMayorCero = true;

								}
								objCatVehAnt = objCatVeh;
							}
						}
						//infoCoor lat Ant  TrazarRuta4
						infoCoorFil = new ArrayList<Coordenadas>();
						infoCoorFil= infoCoor;
						double latAnt=0;
						double lngAnt=0;
						
						
						infoCoor = new ArrayList<Coordenadas>();
						for(int i=0;i<infoCoorFil.size();i++)
						{
							if(i==0)
								{
									infoCoor.add(infoCoorFil.get(i));
									latAnt=infoCoorFil.get(i).getLatitud();
									lngAnt=infoCoorFil.get(i).getLongitud();
								}
							else
							{
								if(infoCoorFil.get(i).getLatitud()!=latAnt && infoCoorFil.get(i).getLongitud()!=lngAnt)
									{
										infoCoor.add(infoCoorFil.get(i));
										latAnt=infoCoorFil.get(i).getLatitud();
										lngAnt=infoCoorFil.get(i).getLongitud();
									}
							}
						}
						///
						winMapGoogle2.appendChild(bly1);
						bly1.appendChild(cnt1);
						pnl1.appendChild(pcGmaps);

						myMapAll.setZoom(9);
						myMapAll.appendChild(mymark2);
						binder.loadComponent(listCoord);
					
					} catch (Exception e) {
						e.printStackTrace();
					}

					Messagebox.show("El Mapa fue generado con exito!!",
							"Atención!!!", Messagebox.OK,
							Messagebox.INFORMATION);

					infoCoorModel = infoCoor;
					if (infoCoor.size() > 0)
					{	
						divControles.setVisible(true);
						mymark2.setLat(infoCoor.get( infoCoor.size()-1).getLatitud());
						mymark2.setLng(infoCoor.get(infoCoor.size()-1).getLongitud());
						mymark2.setIconImage("/Img/gmap-marker-green.gif");
						mymark2.setOpen(true);
						myMapAll.appendChild(mymark2);
					}
					binder.loadComponent(myMapAll);
					southMapaGoogle3.setOpen(false);// 123123
					binder.loadComponent(southMapaGoogle3);
					

				} else {
					westMapaGoogle3.setOpen(true);

					Messagebox.show("No mas de 1 dia en rango de fechas",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
					mapaEnUso = false;

				}

			} else {
				Messagebox.show("Debe llenar campos de fecha", "Atención!!!",
						Messagebox.OK | Messagebox.CANCEL,
						Messagebox.INFORMATION);
				mapaEnUso = false;

			}

		}

	}

	public void busAva() {
		if (!grbBusAva.isOpen())
			grbBusAva.setOpen(true);
		else
			grbBusAva.setOpen(false);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd");
		String date = sdf.format(new Date());

	}


	public void TrazarRuta() {
		timer2.stop();
		stopTimerRuta();
		cleanMap();
		
		// if(mapaEnUso)
		if (false) {
			
			int respuesta = Messagebox.show("¿Desea generar una nueva ruta?",
					"Atención!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.INFORMATION);
			
			if (respuesta == 16)
				nuevaRuta();

		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			int dias = 0;
			Date fecAnt = new Date();

			if (!(fecDesde.getText().equals("")
					|| fecHasta.getText().equals("") )&& objCatVeh != null) {

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {
					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;



					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mymark2 = new Gmarker();
					myMapAll.appendChild(mymark2);


					binder.loadComponent(myMapAll);

					myMap.setVisible(false);

					myMapAll.setVisible(true);

					int count = -1;
					int cantReg = 0;

					DatosDao dat=new DatosDao();
					
					PosicionesMapa posicionesOptimas= new PosicionesMapa();
					infoCoorTotales = new ArrayList<Coordenadas>();
					infoCoorCompletas= new ArrayList<Coordenadas>();
//					infoCoorTotales = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					infoCoorCompletas = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					if(infoCoorCompletas .size()>100)
						infoCoorTotales = posicionesOptimas.retornaPosicionesOptimas(infoCoorCompletas);
					else
						infoCoorTotales = infoCoorCompletas;
					
					
					cantReg = infoCoorTotales.size();
					MapaGoogle2Control mgc2 = new MapaGoogle2Control();

					myMapAll.removeChild(mymark2);

					mymark2.setOpen(true);
					

					try {

						//_user = (String) Executions.getCurrent().getSession().getAttribute("_user");
						
						if(infoCoorTotales.size()<=0){
							Messagebox.show("No existen Registros",
									"Atención!!!", Messagebox.OK
											| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();
							infoMapas = new ArrayList<Mapas>();
							binder.loadComponent(listCoord);
							binder.loadComponent(listCantMap);

						}
						myMapAll.appendChild(mypoly);
						myMapAll.setShowZoomCtrl(true);
						Coordenadas coord = new Coordenadas();
						infoCoor = new ArrayList<Coordenadas>();
						infoCoorModel = new ArrayList<Coordenadas>();

						boolean pasa = true;

						Coordenadas coorTotal = new Coordenadas();
						infoCoorTotales = new ArrayList<Coordenadas>();
						//while (rs.next()) 
						
						
						for(int i=0;i<infoCoorTotales.size();i++) {
							

							if (canVeh < 100) {
								
								mymark2 = new Gmarker();
								if (canVeh == 0) {
									mymark2.setIconImage("/Img/yellow-dot.png");
								}else{
									mymark2.setIconImage("/Img/icon-gmaps-marker-circle.png");
								}
								
								pasa = false;

								if (anteriorMayorCero) {

									mymark2.setOpen(true);// 321
									mymark2.setLat(infoCoorTotales.get(i).getLatitud());
									mymark2.setLng(infoCoorTotales.get(i).getLongitud());
									
									
									binder.loadComponent(mymark2);
									myMapAll.appendChild(mymark2);
									mymark2.setOpen(true);// 321
									// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh());
									if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()|| objCatVehAnt.getIdVeh() == 0) {
										mypoly.setColor("#25FC04");// #25FC04
										mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),
														infoCoorTotales.get(i).getLongitud(),3);
									}

									coord.setLatitud(infoCoorTotales.get(i).getLatitud());
									coord.setLongitud(infoCoorTotales.get(i).getLongitud());
									coord.setFecha(infoCoorTotales.get(i).getFecha());

									infoCoor.add(coord);
									coord = new Coordenadas();
									canVeh++;
									anteriorMayorCero = false;
									pasa = true;
								}

								if (infoCoorTotales.get(i).getVelocidad() > 0 && !pasa) {

									
									mymark2.setOpen(true);// 321

									mymark2.setLat(infoCoorTotales.get(i).getLatitud());
									mymark2.setLng(infoCoorTotales.get(i).getLongitud());
									
									
									binder.loadComponent(mymark2);

									myMapAll.appendChild(mymark2);
									mymark2.setOpen(true);// 321
									
									if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()	|| objCatVehAnt.getIdVeh() == 0) {
										mypoly.setColor("#25FC04");// #25FC04
										mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(),3);

									}
									coord.setLatitud(infoCoorTotales.get(i).getLatitud());
									coord.setLongitud(infoCoorTotales.get(i).getLongitud());
									coord.setFecha(infoCoorTotales.get(i).getFecha());
									infoCoor.add(coord);
									coord = new Coordenadas();

									canVeh++;

									anteriorMayorCero = true;

								}
								objCatVehAnt = objCatVeh;

							}
						}
					////infoCoor lat Ant TrazarRuta
		
						myMapAll.setZoom(9);
						myMapAll.appendChild(mymark2);
						binder.loadComponent(mymark2);
						binder.loadComponent(myMapAll);

						/*if (infoCoor.size() > 0) {
							myMapAll.panTo(infoCoor.get(infoCoor.size() - 1)
									.getLatitud(), infoCoor.get(
									infoCoor.size() - 1).getLongitud());
							binder.loadComponent(myMapAll);
							binder.loadComponent(listCoord);
						}

						double cantTabs = 0;

						Coordenadas coorMap;
						List<Coordenadas> infoCoorMap = new ArrayList<Coordenadas>();

						for(int i=0;i<infoCoorTotales.size();i++){
							coorMap = new Coordenadas();
							coorMap.setFecha(infoCoorTotales.get(i).getFecha());
							infoCoorMap.add(coorMap);
						}
						cantTabs = Math.ceil((double) infoCoorMap
								.size() / 80);
						infoMapas = new ArrayList<Mapas>();
						for (int i = 0; i < cantTabs; i++) {
							Mapas mapG = new Mapas();
							mapG.setIdGmap(i);
							mapG.setNombreGmap("Mapa: " + (i + 1));// +" - "+infoCoorMap.get(i*80).getFecha());
							mapG.setFecha(infoCoorMap.get(i * 80).getFecha());
							infoMapas.add(mapG);

						}
						// }
						if (cantTabs > 1) {
							listCantMap.setDisabled(false);
						
							binder.loadComponent(listCantMap);
						} else {
						
							listCantMap.setDisabled(true);
							binder.loadComponent(listCantMap);
						}*/

					} catch (Exception e) {
						e.printStackTrace();
					}

					
					/*westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
					if (infoCoor.size() > 0)
					{	
						divControles.setVisible(true);
					
						mymark2.setLat(infoCoor.get( infoCoor.size()-1).getLatitud());
						mymark2.setLng(infoCoor.get(infoCoor.size()-1).getLongitud());
						mymark2.setIconImage("/Img/gmap-marker-green.gif");
						mymark2.setOpen(true);
						myMapAll.appendChild(mymark2);
	
						Clients.showNotification(
								"Información detallada de la ruta", "info",
								winMapGoogle2, "end_after", 5000);
						infoCoorModel = infoCoor;
					}*/
				} else {/*
					westMapaGoogle3.setOpen(true);

					Messagebox.show("No mas de 3 dias en rango de fechas",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
					mapaEnUso = false;*/

				}

			} else {
				Messagebox
						.show(
								"Debe llenar campos de fecha, y seleccionar un vehiculo de la lista",
								"Atención!!!", Messagebox.OK
										| Messagebox.CANCEL,
								Messagebox.INFORMATION);

				mapaEnUso = false;

			}

		}
		mymark2 = new Gmarker();

	}

	// String idVeh
	public void TrazarRuta5() {
		timer2.stop();
		stopTimerRuta();
		cleanMap();
		// if(mapaEnUso)
		if (false) {

			int respuesta = Messagebox.show("¿Desea generar una nueva ruta?",
					"Atención!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.INFORMATION);
			// System.out.println("respuesta: "+respuesta);
			if (respuesta == 16)
				nuevaRuta();

		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.format(new Date());

				Date diaPrueba = sdf.parse(date);
				Date diaAnt = restaDia(diaPrueba, 1);

				fecDesde.setText(sdf.format(diaAnt).toString() + " 23:59:59");
				fecHasta.setText(date + " 23:59:59");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int dias = 0;

			Date fecAnt = new Date();

			if (!(fecDesde.getText().equals("") || fecHasta.getText()
					.equals(""))) {

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {

					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;

					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mymark2 = new Gmarker();
					myMapAll.appendChild(mymark2);

					binder.loadComponent(myMapAll);

					myMap.setVisible(false);

					myMapAll.setVisible(true);

					// System.out.println("allCarsOnMapAll");

					int count = -1;
					int cantReg = 0;
				
					DatosDao dat=new DatosDao();
				
					PosicionesMapa posicionesOptimas= new PosicionesMapa();
					infoCoorTotales = new ArrayList<Coordenadas>();
					infoCoorCompletas= new ArrayList<Coordenadas>();
//					infoCoorTotales = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					infoCoorCompletas = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
					if(infoCoorCompletas .size()>100)
						infoCoorTotales = posicionesOptimas.retornaPosicionesOptimas(infoCoorCompletas);
					else
						infoCoorTotales = infoCoorCompletas;
					
					cantReg = infoCoorTotales.size();
					MapaGoogle2Control mgc2 = new MapaGoogle2Control();

					myMapAll.removeChild(mymark2);

					mymark2.setOpen(true);
			

					try {

						//_user = (String) Executions.getCurrent().getSession().getAttribute("_user");

						

						if (infoCoorTotales.size()<=0) {
							Messagebox.show("No existen Registros",
									"Atención!!!", Messagebox.OK
											| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();
							infoMapas = new ArrayList<Mapas>();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();
							binder.loadComponent(listCantMap);
							binder.loadComponent(listCoord);
						} else {
							myMapAll.appendChild(mypoly);
							myMapAll.setShowZoomCtrl(true);
							Coordenadas coord = new Coordenadas();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();

							boolean pasa = true;
							
							//while (rs.next())
							for(int i=0;i<infoCoorTotales.size();i++) 
							{


								if (canVeh < 100) {
									mymark2 = new Gmarker();
									if (canVeh == 0) {
										mymark2.setIconImage("/Img/yellow-dot.png");
									}else{
										mymark2.setIconImage("/Img/icon-gmaps-marker-circle.png");
									}
									pasa = false;

									if (anteriorMayorCero) {
										// System.out.println("--allCarsOnMap()mp: "+" lat: "+rs.getString(2)+" lng: "+rs.getString(3));
										mymark2.setOpen(true);// 321

										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										

										mymark2.setDraggingEnabled(false);
										binder.loadComponent(mymark2);

										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
									
										// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh());
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()
												|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(),3);
										}

										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());

										infoCoor.add(coord);
										coord = new Coordenadas();
										canVeh++;
										anteriorMayorCero = false;
										pasa = true;
									}// end if(anteriormayoracero)

									if (infoCoorTotales.get(i).getVelocidad()  > 0 && !pasa) {

										// System.out.println("--allCarsOnMap()mp2: "+" lat: "+rs.getString(2)+" lng: "+rs.getString(3));
										mymark2.setOpen(true);// 321

										// mymark2.setIconImage(rs.getString(3));
										// mymark2.setIconImageMap(rs.getString(3));
										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										

										mymark2.setDraggingEnabled(false);
										binder.loadComponent(mymark2);

										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
										
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()
												|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(),3);
										}
										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());
										infoCoor.add(coord);
										coord = new Coordenadas();

										canVeh++;

										anteriorMayorCero = true;

									}
									objCatVehAnt = objCatVeh;
								}
							}

							myMapAll.setZoom(9);
							myMapAll.panTo(infoCoor.get(infoCoor.size() - 1)
									.getLatitud(), infoCoor.get(
									infoCoor.size() - 1).getLongitud());
							myMapAll.appendChild(mymark2);
							binder.loadComponent(mymark2);

							binder.loadComponent(myMapAll);
							binder.loadComponent(listCoord);
							// ////////////////////////////////////////////////////////
							double cantTabs = 0;
							// if(cantReg>80){
							Coordenadas coorMap;// = new Coordenadas();
							List<Coordenadas> infoCoorMap = new ArrayList<Coordenadas>();

							//rs.beforeFirst();
							//while (rs.next()) 
								for(int i=0;i<infoCoorTotales.size();i++){
								coorMap = new Coordenadas();
								coorMap.setFecha(infoCoorTotales.get(i).getFecha());
								infoCoorMap.add(coorMap);
							}

							// 123

							cantTabs = Math
									.ceil((double) infoCoorMap
											.size() / 100);// infoMapas
							System.out.println("infoCoorMap.size(): "
									+ infoCoorMap.size() + " cantTabs:"
									+ cantTabs + " cantReg: " + cantReg);
							infoMapas = new ArrayList<Mapas>();
							for (int i = 0; i < cantTabs; i++) {
								Mapas mapG = new Mapas();
								mapG.setIdGmap(i);
								mapG.setNombreGmap("Mapa: " + (i + 1));// +" - "+infoCoorMap.get(i*80).getFecha());
								mapG.setFecha(infoCoorMap.get(i * 100)
										.getFecha());
								infoMapas.add(mapG);

							}
							// }
							if (cantTabs > 1) {
								listCantMap.setDisabled(false);
								// listCantMap.setText("Mapa: 1 - "+infoCoor.get(0).getFecha().toString());
								binder.loadComponent(listCantMap);
							} else {
								// cmbCantMap.setText("Mapa: 1 - "+infoCoor.get(0).getFecha().toString());
								listCantMap.setDisabled(true);
								binder.loadComponent(listCantMap);
							}

						
						}// 1234656789
					} catch (Exception e) {
						e.printStackTrace();
					}

					// objCatVehAnt = objCatVeh;
					infoCoorModel = infoCoor;
					westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
					if (infoCoor.size() > 0){
						divControles.setVisible(true);
					
					mymark2.setLat(infoCoor.get( infoCoor.size()-1).getLatitud());
					mymark2.setLng(infoCoor.get(infoCoor.size()-1).getLongitud());
					mymark2.setIconImage("/Img/gmap-marker-green.gif");
					mymark2.setOpen(true);
					myMapAll.appendChild(mymark2);}

				} else {
					westMapaGoogle3.setOpen(true);

					Messagebox.show("No mas de 3 dias en rango de fechas",
							"Atención!!!", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.INFORMATION);
					mapaEnUso = false;

				}

			} else {
				Messagebox.show("Debe llenar campos de fecha", "Atención!!!",
						Messagebox.OK | Messagebox.CANCEL,
						Messagebox.INFORMATION);
				mapaEnUso = false;

			}

		}
		mymark2 = new Gmarker();

	}
	
	
	// Busqueda por dia
	public void TrazarRuta7() {
		timer2.stop();
		stopTimerRuta();
		cleanMap();
		
//		if (false) {
//			int respuesta = Messagebox.show("¿Desea generar una nueva ruta?",
//					"Atención!!!", Messagebox.YES | Messagebox.NO,
//					Messagebox.INFORMATION);
//			if (respuesta == 16)
//				nuevaRuta();
//
//		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			

			int dias = 0;

			Date fecAnt = new Date();

			if (!(fecDesde.getText().equals("") || fecHasta.getText()
					.equals("")) && objCatVeh != null) {
				
				
				try {
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// date = sdf.format(new Date());
					//date = fecDesdeDia.getText();//si
					date = fecDesde.getText();// no
					System.out.println("date-->:"+date+"-"+fecDesdeDia+"-"+fecDesdeDia.getValue()+"-"+fecDesdeDia.getText()+"--"+fecDesde.getText()
					+"fhasta:"+sdf.format(fecHasta.getValue()).toString());
					Date diaPrueba = sdf.parse(date);
					Date diaAnt = restaDia(diaPrueba, 1);
					// System.out.println("dia Ant:::::::"+
					// sdf.format(diaAnt).toString());
					
					//fecDesde.setText(sdf.format(diaAnt).toString() + " 23:59:59");//si
					fecDesde.setText(sdf.format(diaPrueba).toString() + " 23:59:59");//no
					
					//fecHasta.setText(date + " 23:59:59");
					fecHasta.setText(sdf.format(fecHasta.getValue()).toString() + " 23:59:59");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {

					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;

					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mymark2 = new Gmarker();
					myMapAll.appendChild(mymark2);


					binder.loadComponent(myMapAll);

					myMap.setVisible(false);

					myMapAll.setVisible(true);

					int cantReg = 0;
					
					DatosDao dat=new DatosDao();
					MapaGoogle2Control mgc2 = new MapaGoogle2Control();

					myMapAll.removeChild(mymark2);

					mymark2.setOpen(true);

					try {
						
						PosicionesMapa posicionesOptimas= new PosicionesMapa();
						infoCoorTotales = new ArrayList<Coordenadas>();
						infoCoorCompletas= new ArrayList<Coordenadas>();
//						infoCoorTotales = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
						System.out.println("trazarRuta7: id_veh:"+objCatVeh.getIdVeh()+"-"+fecDesde.getText()+"-"+fecHasta.getText());
						infoCoorCompletas = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
						System.out.println("size coord:"+infoCoorCompletas.size());
						if(infoCoorCompletas .size()>100)
							infoCoorTotales = posicionesOptimas.retornaPosicionesOptimas(infoCoorCompletas);
						else
							infoCoorTotales = infoCoorCompletas;
						System.out.println("size infoCoorTotales:"+infoCoorTotales.size());
						

						if (infoCoorTotales.size()<=0) {
							Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.NoExistenRegistros", objUsuarioSistema.getIdioma()),
									ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", objUsuarioSistema.getIdioma()), 
									Messagebox.OK,//| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();
							infoMapas = new ArrayList<Mapas>();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();
							binder.loadComponent(listCantMap);
							binder.loadComponent(listCoord);
						} else {
							myMapAll.appendChild(mypoly);
							myMapAll.setShowZoomCtrl(true);
							Coordenadas coord = new Coordenadas();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();

							boolean pasa = true;

							

							for(int i=0;i<infoCoorTotales.size();i++) {
								System.out.println("1="+i+"-->"+infoCoorTotales.get(i).getLatitud()+"-"+infoCoorTotales.get(i).getLongitud());

								if (canVeh < 100) {
									
									mymark2 = new Gmarker();
									if (canVeh == 0) {
										mymark2.setIconImage("/Img/yellow-dot.png");
									}else{
										mymark2.setIconImage("/Img/icon-gmaps-marker-circle.png");
									}
									
									pasa = false;

									if (anteriorMayorCero) {

										mymark2.setOpen(true);// 321
										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										
										
										binder.loadComponent(mymark2);
										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
										// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh());
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),
															infoCoorTotales.get(i).getLongitud(),3);
										}

										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());

										infoCoor.add(coord);
										coord = new Coordenadas();
										canVeh++;
										anteriorMayorCero = false;
										pasa = true;
									}

									if (/*infoCoorTotales.get(i).getVelocidad() > 0 &&*/ !pasa) {

										
										mymark2.setOpen(true);// 321

										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										
										
										binder.loadComponent(mymark2);

										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
										
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()	|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(),3);

										}
										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());
										infoCoor.add(coord);
										coord = new Coordenadas();

										canVeh++;

										anteriorMayorCero = true;

									}
									objCatVehAnt = objCatVeh;

								}
							}
							
							///////////////////
							////infoCoor lat Ant TrazarRuta6
							infoCoorFil = new ArrayList<Coordenadas>();
							infoCoorFil= infoCoor;
							double latAnt=0;
							double lngAnt=0;
							System.out.println("infoCoorFil:"+infoCoorFil.size());
							
							infoCoor = new ArrayList<Coordenadas>();
							for(int i=0;i<infoCoorFil.size();i++)
							{
								if(i==0)
									{
										infoCoor.add(infoCoorFil.get(i));
										latAnt=infoCoorFil.get(i).getLatitud();
										lngAnt=infoCoorFil.get(i).getLongitud();
									}
								else
								{
									if(infoCoorFil.get(i).getLatitud()!=latAnt && infoCoorFil.get(i).getLongitud()!=lngAnt)
										{
											infoCoor.add(infoCoorFil.get(i));
											latAnt=infoCoorFil.get(i).getLatitud();
											lngAnt=infoCoorFil.get(i).getLongitud();
										}
								}
							}
							
							
							/////////////
							
							myMapAll.setZoom(9);
							myMapAll.panTo(infoCoor.get(infoCoor.size() - 1).getLatitud(), infoCoor.get(infoCoor.size() - 1).getLongitud());
							myMapAll.appendChild(mymark2);
							binder.loadComponent(mymark2);

							binder.loadComponent(myMapAll);
							binder.loadComponent(listCoord);
							// ////////////////////////////////////////////////////////
							double cantTabs = 0;
							// if(cantReg>80){
							//Se almacena la fecha en infoCoorMap, para luego darsela de atributo al cuadro de mapas generados, donde lleva como 
							//descripcion la fecha
							Coordenadas coorMap;// = new Coordenadas();
							List<Coordenadas> infoCoorMap = new ArrayList<Coordenadas>();
							
							
							for(int i=0;i<infoCoorTotales.size();i++) {
								coorMap = new Coordenadas();
								coorMap.setFecha(infoCoorTotales.get(i).getFecha());
								infoCoorMap.add(coorMap);
								
							}
							
							
							//123*+
							cantTabs = Math
									.ceil(/* cantReg */(double) infoCoorMap
											.size() / 100);// infoMapas
							System.out.println("infoCoorMap.size(): "
									+ infoCoorMap.size() + " cantTabs:"
									+ cantTabs + " cantReg: " + cantReg);
							infoMapas = new ArrayList<Mapas>();
							for (int i = 0; i < cantTabs; i++) {
								Mapas mapG = new Mapas();
								mapG.setIdGmap(i);
								mapG.setNombreGmap("Mapa: " + (i + 1));// +" - "+infoCoorMap.get(i*80).getFecha());
								mapG.setFecha(infoCoorMap.get(i * 100)
										.getFecha());
								infoMapas.add(mapG);

							}

							if (cantTabs > 1) {
								listCantMap.setDisabled(false);

								binder.loadComponent(listCantMap);
							} else {

								listCantMap.setDisabled(true);
								binder.loadComponent(listCantMap);
							}

						}// 1234656789
					} catch (Exception e) {
						e.printStackTrace();
					}

					// objCatVehAnt = objCatVeh;
					infoCoorModel = infoCoor;
					westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
					if (infoCoor.size() > 0)
					{	divControles.setVisible(true);
					
					mymark2.setLat(infoCoor.get( infoCoor.size()-1).getLatitud());
					mymark2.setLng(infoCoor.get(infoCoor.size()-1).getLongitud());
					mymark2.setIconImage("/Img/gmap-marker-green.gif");
					mymark2.setOpen(true);
					myMapAll.appendChild(mymark2);}

				} else {
					westMapaGoogle3.setOpen(true);

					Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.NoMasDe3Dias", objUsuarioSistema.getIdioma()),
							ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", objUsuarioSistema.getIdioma()), 
							Messagebox.OK,// | Messagebox.CANCEL,
							Messagebox.INFORMATION);
					mapaEnUso = false;

				}

			} else {
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.DebeLlenarCamposFechayEscogerVehiculo", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", objUsuarioSistema.getIdioma()),
						Messagebox.OK ,//| Messagebox.CANCEL,
						Messagebox.INFORMATION);
				mapaEnUso = false;

			}

//		} fin if (false)
		mymark2 = new Gmarker();
	}
	
	
	

	// Busqueda por dia
	public void TrazarRuta6() {
		timer2.stop();
		stopTimerRuta();
		cleanMap();
		// if(mapaEnUso)
		if (false) {
			// int
			// respuesta=Messagebox.show("El mapa se encuentra en uso por el vehiculo: "+
			// objVehEnUso.getNomVeh()+" \n En caso de desear una nueva busqueda, debera presionar el boton 'Nueva Ruta'.","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			int respuesta = Messagebox.show("¿Desea generar una nueva ruta?",
					"Atención!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.INFORMATION);
			// System.out.println("respuesta: "+respuesta);
			if (respuesta == 16)
				nuevaRuta();

		} else {
			mapaEnUso = true;
			objVehEnUso = objCatVeh;

			
			int dias = 0;

			Date fecAnt = new Date();

//			if (!(fecDesde.getText().equals("") || fecHasta.getText()
//					.equals(""))&& objCatVeh != null) {
			if (!(fecDesdeDia.getText().equals("") )&& objCatVeh != null) {
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// date = sdf.format(new Date());
					date = fecDesdeDia.getText();
					Date diaPrueba = sdf.parse(date);
					Date diaAnt = restaDia(diaPrueba, 1);
					// System.out.println("dia Ant:::::::"+
					// sdf.format(diaAnt).toString());
					fecDesde.setText(sdf.format(diaAnt).toString() + " 23:59:59");
					fecHasta.setText(date + " 23:59:59");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				

				fecAnt = restaDia(fecHasta.getValue(), 3);

				if ((fecAnt.before(fecDesde.getValue()) == true)
						|| fecAnt.equals(fecDesde.getValue())
						|| fecAnt == fecDesde.getValue()
						|| fecAnt.getTime() == fecDesde.getValue().getTime()
						|| fecAnt.toString().equals(
								fecDesde.getValue().toString())
						|| (fecAnt.getDay() == fecDesde.getValue().getDay()
								&& fecAnt.getMonth() == fecDesde.getValue()
										.getMonth() && fecAnt.getYear() == fecDesde
								.getValue().getYear())) {

					int canVeh = 0;
					Marks mark1 = new Marks();
					boolean anteriorMayorCero = true;

					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mymark2 = new Gmarker();
					myMapAll.appendChild(mymark2);


					binder.loadComponent(myMapAll);

					myMap.setVisible(false);

					myMapAll.setVisible(true);

					int cantReg = 0;
					
					DatosDao dat=new DatosDao();
					MapaGoogle2Control mgc2 = new MapaGoogle2Control();

					myMapAll.removeChild(mymark2);

					mymark2.setOpen(true);


					try {
						
						PosicionesMapa posicionesOptimas= new PosicionesMapa();
						infoCoorTotales = new ArrayList<Coordenadas>();
						infoCoorCompletas= new ArrayList<Coordenadas>();
//						infoCoorTotales = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
						infoCoorCompletas = dat.TrazaRuta6Dao(objCatVeh.getIdVeh(), fecDesde.getText(), fecHasta.getText());
						if(infoCoorCompletas .size()>100)
							infoCoorTotales = posicionesOptimas.retornaPosicionesOptimas(infoCoorCompletas);
						else
							infoCoorTotales = infoCoorCompletas;
						
						System.out.println("xxxxxxxxxxxxxxxxxinfoCoorTotales: "+infoCoorTotales.size());
						if (infoCoorTotales.size()<=0) {
							Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.NoExistenRegistros", objUsuarioSistema.getIdioma()),
									"Atención!!!", Messagebox.OK
											| Messagebox.CANCEL,
									Messagebox.INFORMATION);
							mapaEnUso = false;
							mgc2 = new MapaGoogle2Control();
							infoMapas = new ArrayList<Mapas>();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();
							binder.loadComponent(listCantMap);
							binder.loadComponent(listCoord);
						} else {
							myMapAll.appendChild(mypoly);
							myMapAll.setShowZoomCtrl(true);
							Coordenadas coord = new Coordenadas();
							infoCoor = new ArrayList<Coordenadas>();
							infoCoorModel = new ArrayList<Coordenadas>();

							boolean pasa = true;

							

							for(int i=0;i<infoCoorTotales.size();i++) {
								

								if (canVeh < 100) {
									
									mymark2 = new Gmarker();
									if (canVeh == 0) {
										mymark2.setIconImage("/Img/yellow-dot.png");
									}else{
										mymark2.setIconImage("/Img/icon-gmaps-marker-circle.png");
									}
									
									pasa = false;

									if (anteriorMayorCero) {

										mymark2.setOpen(true);// 321
										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										
										
										binder.loadComponent(mymark2);
										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
										// System.out.println("objCatVehAnt.getIdVeh() : "+objCatVehAnt.getIdVeh());
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),
															infoCoorTotales.get(i).getLongitud(),3);
										}

										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());

										infoCoor.add(coord);
										coord = new Coordenadas();
										System.out.println("pares canVeh: "+canVeh);
										canVeh++;
										anteriorMayorCero = false;
										pasa = true;
									}

									if (infoCoorTotales.get(i).getVelocidad() > 0 && !pasa) {

										
										mymark2.setOpen(true);// 321

										mymark2.setLat(infoCoorTotales.get(i).getLatitud());
										mymark2.setLng(infoCoorTotales.get(i).getLongitud());
										
										
										binder.loadComponent(mymark2);

										myMapAll.appendChild(mymark2);
										mymark2.setOpen(true);// 321
										
										if (objCatVehAnt.getIdVeh() == objCatVeh.getIdVeh()	|| objCatVehAnt.getIdVeh() == 0) {
											mypoly.setColor("#25FC04");// #25FC04
											mypoly.addPoint(infoCoorTotales.get(i).getLatitud(),infoCoorTotales.get(i).getLongitud(),3);

										}
										coord.setLatitud(infoCoorTotales.get(i).getLatitud());
										coord.setLongitud(infoCoorTotales.get(i).getLongitud());
										coord.setFecha(infoCoorTotales.get(i).getFecha());
										infoCoor.add(coord);
										coord = new Coordenadas();
										System.out.println("impares canVeh: "+canVeh);
										canVeh++;

										anteriorMayorCero = true;

									}
									objCatVehAnt = objCatVeh;

								}
							}
							
							///////////////////
							////infoCoor lat Ant TrazarRuta6
							infoCoorFil = new ArrayList<Coordenadas>();
							infoCoorFil= infoCoor;
							double latAnt=0;
							double lngAnt=0;
							
							
							infoCoor = new ArrayList<Coordenadas>();
							for(int i=0;i<infoCoorFil.size();i++)
							{
								if(i==0)
									{
										infoCoor.add(infoCoorFil.get(i));
										latAnt=infoCoorFil.get(i).getLatitud();
										lngAnt=infoCoorFil.get(i).getLongitud();
									}
								else
								{
									if(infoCoorFil.get(i).getLatitud()!=latAnt && infoCoorFil.get(i).getLongitud()!=lngAnt)
										{
											infoCoor.add(infoCoorFil.get(i));
											latAnt=infoCoorFil.get(i).getLatitud();
											lngAnt=infoCoorFil.get(i).getLongitud();
										}
								}
							}
							
							
							/////////////
							
							myMapAll.setZoom(9);
							myMapAll.panTo(infoCoor.get(infoCoor.size() - 1).getLatitud(), infoCoor.get(infoCoor.size() - 1).getLongitud());
							myMapAll.appendChild(mymark2);
							binder.loadComponent(mymark2);

							binder.loadComponent(myMapAll);
							binder.loadComponent(listCoord);
							// ////////////////////////////////////////////////////////
							double cantTabs = 0;
							// if(cantReg>80){
							//Se almacena la fecha en infoCoorMap, para luego darsela de atributo al cuadro de mapas generados, donde lleva como 
							//descripcion la fecha
							Coordenadas coorMap;// = new Coordenadas();
							List<Coordenadas> infoCoorMap = new ArrayList<Coordenadas>();
							
							
							for(int i=0;i<infoCoorTotales.size();i++) {
								coorMap = new Coordenadas();
								coorMap.setFecha(infoCoorTotales.get(i).getFecha());
								infoCoorMap.add(coorMap);
								
							}
							
							
							//123*+
							cantTabs = Math
									.ceil(/* cantReg */(double) infoCoorMap
											.size() / 100);// infoMapas
							System.out.println("infoCoorMap.size(): "
									+ infoCoorMap.size() + " cantTabs:"
									+ cantTabs + " cantReg: " + cantReg);
							infoMapas = new ArrayList<Mapas>();
							for (int i = 0; i < cantTabs; i++) {
								Mapas mapG = new Mapas();
								mapG.setIdGmap(i);
								mapG.setNombreGmap("Mapa: " + (i + 1));// +" - "+infoCoorMap.get(i*80).getFecha());
								mapG.setFecha(infoCoorMap.get(i * 100)
										.getFecha());
								infoMapas.add(mapG);

							}

							if (cantTabs > 1) {
								listCantMap.setDisabled(false);

								binder.loadComponent(listCantMap);
							} else {

								listCantMap.setDisabled(true);
								binder.loadComponent(listCantMap);
							}

						}// 1234656789
					} catch (Exception e) {
						e.printStackTrace();
					}

					// objCatVehAnt = objCatVeh;
					infoCoorModel = infoCoor;
					westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
					if (infoCoor.size() > 0)
					{	divControles.setVisible(true);
					
					mymark2.setLat(infoCoor.get( infoCoor.size()-1).getLatitud());
					mymark2.setLng(infoCoor.get(infoCoor.size()-1).getLongitud());
					mymark2.setIconImage("/Img/gmap-marker-green.gif");
					mymark2.setOpen(true);
					myMapAll.appendChild(mymark2);}

				} else {
					westMapaGoogle3.setOpen(true);

					Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.NoMasDe3Dias", objUsuarioSistema.getIdioma()),
							ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", objUsuarioSistema.getIdioma()), 
							//Messagebox.OK | Messagebox.CANCEL,
							Messagebox.OK,
							Messagebox.INFORMATION);
					mapaEnUso = false;

				}

			} else {
				Messagebox.show(ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.DebeLlenarCamposFechayEscogerVehiculo", objUsuarioSistema.getIdioma()),
						ReadPropertiesUtil.obtenerProperty("control.MapaGoogle3Control.trazarRuta6.Atencion", objUsuarioSistema.getIdioma()),
//						Messagebox.OK | Messagebox.CANCEL,
						Messagebox.OK ,
						Messagebox.INFORMATION);
				mapaEnUso = false;

			}

		}
		mymark2 = new Gmarker();
	}

	public void TrazarRuta2() {
		timer2.stop();
		cleanMap();
		int canVeh = 0;
		Marks mark1 = new Marks();
		boolean anteriorMayorCero = true;
		System.out.println("myMapAll.getChildren() "
				+ myMapAll.getChildren().size());
		myMapAll.getChildren().clear();
		binder.loadComponent(myMapAll);
		myMap.setVisible(false);
		myMapAll.setVisible(true);
		// System.out.println("allCarsOnMapAll");

		int count = -1;
		int cantReg = 0;
		Statement st = null;
		ResultSet rs = null;

		Statement st1 = null;
		ResultSet rs1 = null;

		MapaGoogle2Control mgc2 = new MapaGoogle2Control();

		myMapAll.removeChild(mymark2);

		mymark2.setOpen(true);
		Connection conn = ConexionUtil.getConnection();

		try {
			//_user = (String) Executions.getCurrent().getSession().getAttribute(	"_user");
			
			st = conn.createStatement();

			String sql1 = null;
			st1 = conn.createStatement();

			rs1 = st1
					.executeQuery("select count(distinct(concat(coo.co_latitud,coo.co_longitud))) dist "
							+ " from coordenadas coo "
							+ " where coo.ve_id= "
							+ objVehEnUso.getIdVeh()
							+ " and co_fecha>= '"
							+ fecDesde.getText()
							+ "' "
							+ " and co_fecha<= '"
							+ fecHasta.getText()
							+ "' and coo.co_Velocidad > 0.00; ");

			while (rs1.next()) {
				cantReg = rs1.getInt(1);

			}

			rs = st
					.executeQuery("select distinct(concat(coo.co_latitud,coo.co_longitud)) dist  ,coo.co_latitud, coo.co_longitud,  coo.co_Id, coo.co_Velocidad "
							+ " from coordenadas coo "
							+ " where coo.ve_id= "
							+ objCatVeh.getIdVeh()
							+ " and co_fecha>= '"
							+ fecDesde.getText()
							+ "' "
							+ " and co_fecha<= '"
							+ fecHasta.getText()
							+ "'  "
							+ " group by coo.co_latitud, coo.co_longitud,  coo.co_Id  order by co_fecha desc , coo.co_Velocidad;");

			// System.out.println("funk ocall 2");

			if (rs.equals(null) || cantReg == 0) {
				Messagebox.show("No existen Registros", "Atención!!!",
						Messagebox.OK | Messagebox.CANCEL,
						Messagebox.INFORMATION);
				mgc2 = new MapaGoogle2Control();

			}
			myMapAll.appendChild(mypoly);
			Coordenadas coord = new Coordenadas();
			infoCoor = new ArrayList<Coordenadas>();
			while (rs.next() && canVeh <= 80) {

				if (anteriorMayorCero) {
					// System.out.println("--allCarsOnMap()mp: "+" lat: "+rs.getString(2)+" lng: "+rs.getString(3));
					// 4444444444444444444444444444444444
					// mypoly.addPoint(/*ltd,
					// lng,*/Double.parseDouble(rs.getString(2)),Double.parseDouble(rs.getString(3)),
					// 3);
					coord.setLatitud(Double.parseDouble(rs.getString(2)));
					coord.setLongitud(Double.parseDouble(rs.getString(3)));
					infoCoor.add(coord);
					coord = new Coordenadas();
					canVeh++;
					anteriorMayorCero = false;
				}

				if (rs.getDouble(5) > 0) {

					coord.setLatitud(Double.parseDouble(rs.getString(2)));
					coord.setLongitud(Double.parseDouble(rs.getString(3)));
					infoCoor.add(coord);
					coord = new Coordenadas();

					canVeh++;

					anteriorMayorCero = true;

				}
			}
			myMapAll.setZoom(9);
			myMapAll.appendChild(mymark2);
			binder.loadComponent(mymark2);
			binder.loadComponent(myMapAll);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void onMapClick(double lat, double lng) {
		timer2.stop();
		cleanMap();
		
		
		String content = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// System.out.println("listen lat: "+ lat+" lng: "+lng+" size: "+
		// infoCoor.size());
		/*
		 * mymark2 = new Gmarker(); mymark2 = event.getGmarker();
		 *//* &lt;a href=&quot;http://www.zkoss.org&quot;&gt;ZK&lt;/a&gt; */
		for (int i = 0; i < infoCoorModel.size(); i++) {
			if (infoCoorModel.get(i).getLatitud() == lat
					&& infoCoorModel.get(i).getLongitud() == lng) {// System.out.println("listen2 lat: "+
				// lat+" lng: "+lng);
				
				Executions.getCurrent().getSession().setAttribute("_lat", lat);
				Executions.getCurrent().getSession().setAttribute("_lng", lng);
				mymark2.setLat(lat);
				mymark2.setLng(lng);
				content = "<table border='1'> " + " <tr> "
						+ " <th bgcolor='#ccccff' colspan=2 align=center>"
						+ objCatVeh.getNomVeh()
						+ "</th> "
						+ " </tr> "
						+ " <tr> "
						+ " <td> Lat: </td><td>"
						+ lat
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Lng: </td><td>"
						+ lng
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Velocidad: </td><td>"
						+ infoCoorModel.get(i).getVelocidad()
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Fecha: </td><td>"
						+ sdf.format(infoCoorModel.get(i).getFecha())
						+ "</td> " + " </tr> " + " </table>";
				mymark2.setIconImage("/Img/gmarker_blue.png");
				/*
				 * mymark2.setIconSizeHeight(0); mymark2.setHeight("0");
				 * mymark2.setWidth("0"); mymark2.setStyle("opacity:0;");
				 */
				System.out.println("mymark2.setIconSizeHeight(0);");
				mymark2.setContent(content);
				/*
				 * mymark2.setContent("Latitud: "+lat+" <br> Longitud: "+lng+"<br> Nombre veh: "
				 * +objCatVeh.getNomVeh()+" <br> " + "Velocidad:	"+
				 * infoCoor.get(
				 * i).getVelocidad()+" <br> Fecha: "+infoCoor.get(i).getFecha()
				 * + "<br> Recorrido: "+infoCoor.get(i).getCo_recorrido()+
				 * "<br> <a href='index1.zul' TARGET='_new'>Street View</a>");
				 */
				mymark2.setOpen(true);
				myMapAll.appendChild(mymark2);
				binder.loadComponent(mymark2);
				break;
			}

		}

		/*
		 * myinfo.setContent("Latitud: "+lat+" <br> \n Longitud: "+lng);
		 * myinfo.setLat(lat); myinfo.setLng(lng); myinfo.setOpen(true);
		 * myMapAll.appendChild(myinfo); binder.loadComponent(myinfo);
		 */

		/*
		 * if(mymark2 != null) { System.out.println("listen 2");
		 * 
		 * mymark2.setContent("XP"); mymark2.setOpen(true);
		 * //myMapAll.appendChild(mymark2); //binder.loadComponent(myMapAll); }
		 */
	}

	public void pruebaCall() {
		System.out.println("pruebaCall()");
	}

	public void onEvent(Event e) throws Exception {
		if (e.getTarget() == myMapAll) {
			if (e.getTarget() == mymark2) {
				mymark2.setOpen(true);
			}
		}
	}

	public void stop() {
		// WaitLock lock = null;
		Clients.clearBusy(listCoord);
		// Clients.clearBusy(myMapAll);
	}

	public void esperar() {
		// Events.echoEvent("onAddNameEvent", timer, null);
		Clients.showBusy(listCoord, "procesando...");
		timer.start();
	}

	public void stopTimerRuta() {
		myMapAll.removeChild(mymark2);
		myMapAll.removeChild(mypoly);// 123123
		myMapAll.getChildren().clear();
		mypoly = new Gpolyline();// 12123
		mypoly.setColor("#25FC04");
		mymark2 = new Gmarker();

		myMap.setVisible(false);
		myMapAll.setVisible(true);
		mymark2.setOpen(true);
		myMapAll.appendChild(mymark2);

		binder.loadComponent(mymark2);
		binder.loadComponent(mypoly);
		binder.loadComponent(myMapAll);
		timerRuta.stop();
		contRuta = 0;
	}

	// Play
	public void playBtn() {
		System.out.println("playBtn: " + contRuta + " infoCoorCompletas.size(): "
				+ infoCoorCompletas.size());
		timer2.stop();
		if (infoCoorCompletas != null) {
			if (contRuta == infoCoorCompletas.size()) {
				stopTimerRuta();
				timerRuta.start();
			} else if (contRuta < infoCoorCompletas.size()) {
				if (contRuta == 0) {
					
					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mypoly.setColor("#25FC04");
					mymark2 = new Gmarker();

					myMap.setVisible(false);
					myMapAll.setVisible(true);
					mymark2.setOpen(true);
					myMapAll.appendChild(mymark2);

					binder.loadComponent(mymark2);
					binder.loadComponent(mypoly);
					binder.loadComponent(myMapAll);

					System.out.println("playBtn infoCoor.size(): "
							+ infoCoorCompletas.size());
					myMapAll.removeChild(mymark2);
					myMapAll.removeChild(mypoly);// 123123
					myMapAll.getChildren().clear();
					mypoly = new Gpolyline();// 12123
					mypoly.setColor("#25FC04");
					// mymark2 = new Gmarker();

					myMap.setVisible(false);
					myMapAll.setVisible(true);
					mymark2.setOpen(true);
					myMapAll.appendChild(mymark2);

					binder.loadComponent(mymark2);
					binder.loadComponent(mypoly);
					binder.loadComponent(myMapAll);
				}

				try {
					myMapAll.appendChild(mypoly);
					myMapAll.appendChild(mymark2);

					System.out.println("contRuta: " + contRuta);
					mymark2
							.setLat(infoCoorCompletas.get(
									infoCoorCompletas.size() - contRuta - 1)
									.getLatitud());
					mymark2.setLng(infoCoorCompletas.get(
							infoCoorCompletas.size() - contRuta - 1)
							.getLongitud());
					mypoly.addPoint(
							infoCoorCompletas.get(
									infoCoorCompletas.size() - contRuta - 1)
									.getLatitud(), infoCoorCompletas.get(
											infoCoorCompletas.size() - contRuta - 1)
									.getLongitud(), 3);

					myMapAll.appendChild(mypoly);
					myMapAll.appendChild(mymark2);
					binder.loadComponent(myMapAll);

					/*
					 * if(infoCoor.size()>0){
					 * myMapAll.panTo(infoCoor.get(infoCoor
					 * .size()-1).getLatitud(),
					 * infoCoor.get(infoCoor.size()-1).getLongitud());
					 * binder.loadComponent(myMapAll);}
					 */

					myMapAll.panTo(
							infoCoorCompletas.get(
									infoCoorCompletas.size() - contRuta - 1)
									.getLatitud(), infoCoorCompletas.get(
											infoCoorCompletas.size() - contRuta - 1)
									.getLongitud());

					/*
					 * if(contRuta == infoCoor.size()-1) { timerRuta.stop();
					 * contRuta=0; }else
					 */
					contRuta++;

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (contRuta == infoCoorCompletas.size()) {
					timerRuta.stop();
				}
			}
		} else
			timerRuta.stop();

	}
	
	
	
	

	// Rewind
	public void backTrack() {
		System.out.println("backTrack: " + contRuta);
		timerRuta.stop();
		if (contRuta > 1) {
			contRuta = contRuta - 1;
			System.out.println("backTrack infoCoor.size(): "
					+ infoCoorCompletas.size());
			myMapAll.removeChild(mymark2);
			myMapAll.removeChild(mypoly);// 123123
			myMapAll.getChildren().clear();
			mypoly = new Gpolyline();// 12123
			mypoly.setColor("#25FC04");
			// mymark2 = new Gmarker();

			myMap.setVisible(false);
			myMapAll.setVisible(true);
			mymark2.setOpen(true);

			for (int i = 0; i <= contRuta - 1; i++) {
				mymark2.setLat(infoCoorCompletas.get(
						infoCoorCompletas.size() - i - 1).getLatitud());
				mymark2.setLng(infoCoorCompletas.get(
						infoCoorCompletas.size() - i - 1).getLongitud());
				mypoly.addPoint(infoCoorCompletas.get(
						infoCoorCompletas.size() - i - 1).getLatitud(),
						infoCoorCompletas.get(infoCoorCompletas.size() - i - 1)
								.getLongitud(), 3);
			}

			myMapAll.appendChild(mymark2);
			myMapAll.appendChild(mypoly);
			binder.loadComponent(mymark2);
			binder.loadComponent(mypoly);
			binder.loadComponent(myMapAll);

		}

	}

	public void esperarCanMap() {
		// Events.echoEvent("onAddNameEvent", timer, null);
		Clients.showBusy(listCantMap, "procesando...");
		timer.start();
	}

	// @Listen("onAddNameEvent = #timer")
	public void processingFiles() {
		timer.start();
	}

	public void acercaVeh() {
		timer2.stop();
		cleanMap();
		/*
		 * myMapAll.removeChild(mymark2); mymark2.setOpen(false);
		 * binder.removeBinding(mymark2, "content");
		 * myMapAll.removeChild(mymark2); //myMapAll.appendChild(mymark2);
		 * binder.loadComponent(myMapAll);
		 */
		String content = "";
		content = "<table border='1'> " + " <tr> "
				+ " <th bgcolor='#ccccff' colspan=2 align=center>"
				+ objCatVeh.getNomVeh()
				+ "</th> "
				+ " </tr> "
				+ " <tr> "
				+ " <td> Lat: </td><td>"
				+ objCoordenadas.getLatitud()
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>Lng: </td><td>"
				+ objCoordenadas.getLongitud()
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>Velocidad: </td><td>"
				+ objCoordenadas.getVelocidad()
				+ "</td> "
				+ " </tr> "
				+ " <tr> "
				+ " <td>Fecha: </td><td>"
				+ objCoordenadas.getFecha()
				+ "</td> "
				+ " </tr> "
				+ " </table>";
		myMapAll.removeAttribute("Content");
		myMapAll.removeChild(mymark2);
		myMapAll.removeChild(mypoly);// 123123
		myMapAll.getChildren().clear();
		TrazarRuta2();
		mymark2 = new Gmarker();
		mymark2.setLat(objCoordenadas.getLatitud());
		mymark2.setLng(objCoordenadas.getLongitud());
		mymark2.setContent(content);

		binder.loadAttribute(mymark2, "content");
		// mymark2.setIconImage("/Img/Ambulance.png");
		myMapAll.setZoom(15);
		// System.out.println(" acerca veh objCoordenadas.getLatitud(): "+objCoordenadas.getLatitud()+"---"+"objCoordenadas.getLongitud(): "+objCoordenadas.getLongitud());

		mymark2.setOpen(true);
		myMapAll.appendChild(mymark2);
		myMapAll.panTo(objCoordenadas.getLatitud(), objCoordenadas
				.getLongitud());

		// mymark2= new Gmarker();
		binder.loadComponent(mymark2);
		binder.loadComponent(myMapAll);

		// binder.loadComponent(listCoord);
		System.out.println("hi: acercaVeh<--------------------------========"
				+ infoCoor.size());

	}

	public void allCarsOnMap() {
		// private List<Gmarker> listMarks;
		// int idCoordenadas=0;

		Marks mark1 = new Marks();
		// myMap = new Gmaps();

		// winMapGoogle2.removeChild(myMapAll);
		// try{
		myMap.setVisible(false);
		// winMapGoogle2.removeChild(myMap);

		// myMapAll.removeChild(mymark2);
		// pcGmaps.removeChild(myMapAll);
		// myMapAll= new Gmaps();
		// myMapAll.setWidth("100%");
		// myMapAll.setHeight("100%");

		myMapAll.setVisible(true);

		// myMapAll= new Gmaps();

		// binder.loadComponent(myMapAll);

		System.out.println("allCarsOnMapAll");

		int count = -1;
		Statement st = null;
		ResultSet rs = null;
		MapaGoogle2Control mgc2 = new MapaGoogle2Control();
		// gMark= new ArrayList<Marks>();
		Connection conn = ConexionUtil.getConnection();

		try {

			//_user = (String) Executions.getCurrent().getSession().getAttribute(	"_user");
			
			st = conn.createStatement();
			rs = st
					.executeQuery("select coo.co_latitud, coo.co_longitud, veh.Ve_imagen,veh.ve_id , veh.ve_nombre, coo.co_Id, max(co_fecha) "
							+ " from vehiculos veh, coordenadas coo "
							+ " where veh.Ve_id=coo.ve_Id "
							+ " and veh.Usu_id=( "
							+ " select Usu_id "
							+ " from usuarios "
							+ " where user = '"
							+ objUsuarioSistema.getUser()
							+ "' ) " + " group by ve_id;");

			System.out.println("funk ocall 2");

			while (rs.next()) {
				// listMarks= new ArrayList<Gmarker>();

				// mymark2= new Gmarker();
				System.out.println("--allCarsOnMap(): " + " lng: "
						+ rs.getString(1) + " ltd: " + rs.getString(2));
				mymark2.setOpen(true);

				mymark2.setIconImage(rs.getString(3));
				mymark2.setIconImageMap(rs.getString(3));
				mymark2.setLat(Double.parseDouble(rs.getString(1)));
				mymark2.setLng(Double.parseDouble(rs.getString(2)));
				mymark2.setContext(rs.getString(5));
				mymark2.setContent(rs.getString(5));
				mymark2.setDraggingEnabled(false);

				binder.loadComponent(mymark2);
				binder.loadAttribute(mymark2, "Context");
				binder.loadAttribute(mymark2, "Content");
				myMapAll.appendChild(mymark2);
				mymark2.setOpen(true);
				mymark2 = new Gmarker();

			}

			myMapAll.setZoom(5);

			binder.loadComponent(myMapAll);
			pcGmaps.appendChild(myMapAll);

			// myMapAll= new Gmaps();//para que no se repita
			// winMapGoogle2.appendChild(myMapAll);

			// myMapAll.redraw( null);
			// mymark2.redraw(null);
			// binder.loadComponent(mymark2 );
			// binder.loadAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillCmbVehCat() {
		
		
		
		DatosDao dat = new DatosDao();
		CategoriaVehiculo cVeh= new CategoriaVehiculo();
		catVeh=  new ArrayList<CategoriaVehiculo>();
		catVehModel=  new ArrayList<CategoriaVehiculo>();
		
		try{
			
			
			catVehModel = dat.obtenerCatVehDao(objUsuarioSistema);
			cVeh.setId_categoria(0);	
			cVeh.setDescripcion("TODOS");
			cVeh.setIdentificador("TOD");
			catVehModel.add(cVeh);
			catVeh = catVehModel;			
			binder.loadComponent(cmbCatVeh);			

		}
		catch(Exception e){e.printStackTrace();}
		
		
		/*CategoriaVehiculo cVeh = new CategoriaVehiculo();
		catVeh = new ArrayList<CategoriaVehiculo>();

		Statement st_us = null;
		ResultSet rs_us = null;
		int adm = 0;
		int usu_id = 0;

		ResultSet rs = null;
		Statement st = null;
		Connection conn = ConexionUtil.getConnection();
		categoria = "0";

		try {

			st = conn.createStatement();
			
			st_us = conn.createStatement();
			rs_us = st_us
					.executeQuery("select per_id, usu_id from usuarios where user = '"
							+ _user + "'");

			while (rs_us.next()) {

				adm = rs_us.getInt("per_id");
				usu_id = rs_us.getInt("usu_id");
			}

			System.out
					.println("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id   "
							+ "from categoria_vehiculo c "
							+ "where c.estado = 'A' and "
							+ "(c.usu_id = "
							+ usu_id
							+ " or "
							+ "exists (select id_re_per_prm  from rel_perfil_permiso rpp inner join permisos prm on "
							+ "rpp.prm_id = prm.prm_id where per_id = "
							+ adm
							+ " and prm_valor ='2.4'  ))group by id_categoria_vehiculo");

			rs = st
					.executeQuery("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id   "
							+ "from categoria_vehiculo c "
							+ "where c.estado = 'A' and "
							+ "(c.usu_id = "
							+ usu_id
							+ " or "
							+ "exists (select id_re_per_prm  from rel_perfil_permiso rpp inner join permisos prm on "
							+ "rpp.prm_id = prm.prm_id where per_id = "
							+ adm
							+ " and prm_valor ='2.4'  ))group by id_categoria_vehiculo");

			while (rs.next()) {

				cVeh.setId_categoria(rs.getInt(1));
				cVeh.setDescripcion(rs.getString(2));
				cVeh.setIdentificador(rs.getString(3));
			
				catVeh.add(cVeh);

				cVeh = new CategoriaVehiculo();
			}

			cVeh.setId_categoria(0);
			cVeh.setDescripcion("TODOS");
			cVeh.setIdentificador("TOD");
			catVeh.add(cVeh);

			cVeh = new CategoriaVehiculo();

			cmbCatVeh.setSelectedText(0, 1, "Escoger Cat.", true);

			binder.loadComponent(cmbCatVeh);
			binder.loadComponent(lisVehiculo);

		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}

	public void myMark(double ltd, double lng) {
		System.out
				.println("myMark(ltd,lng): " + "ltd: " + ltd + " lng: " + lng);

	}

	public void onTimer2() {
		int ultCoor = 0;
		String ultCoorStr = null;
		if (idVehiculo_Res != 0) {
			carOnMap();

		}
	}

	public void cleanMap() {
		/*
		 * myMap.removeChild(mymark); myMap.removeChild(mypoly2);//123123
		 * myMap.getChildren().clear(); mypoly2 = new Gpolyline();//12123
		 * 
		 * //pcGmaps.appendChild(myMap); myMap.appendChild(mypoly2);
		 * 
		 * mypoly2.getChildren().clear();
		 * mypoly2.getRoot().getChildren().clear(); mypoly2.getRoot().detach();
		 * 
		 * mymark = new Gmarker();
		 */

	}

	public void onMapClick2(double lat, double lng) {

		// MapMouseEvent event2 = new MapMouseEvent(_user, mymark2, myMapAll,
		// lat, lng, idCoordenadas, idCoordenadas, idCoordenadas, idCoordenadas,
		// idCoordenadas);
		String content = "";
		// System.out.println("listen lat: "+ lat+" lng: "+lng);
		/*
		 * mymark2 = new Gmarker(); mymark2 = event.getGmarker();
		 */

		for (int i = 0; i < infoCoorModelOnMap.size(); i++) {
			if (infoCoorModelOnMap.get(i).getLatitud() == lat
					&& infoCoorModelOnMap.get(i).getLongitud() == lng) {
				mymark3.setLat(lat);
				mymark3.setLng(lng);
				content = "<table border='1'> " + " <tr> "
						+ " <th bgcolor='#ccccff' colspan=2 align=center>"
						+ objCatVeh.getNomVeh()
						+ "</th> "
						+ " </tr> "
						+ " <tr> "
						+ " <td> Lat: </td><td>"
						+ lat
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Lng: </td><td>"
						+ lng
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Velocidad: </td><td>"
						+ infoCoorModelOnMap.get(i).getVelocidad()
						+ "</td> "
						+ " </tr> "
						+ " <tr> "
						+ " <td>Fecha: </td><td>"
						+ infoCoorModelOnMap.get(i).getFecha()
						+ "</td> "
						+ " </tr> " + " </table>";
				mymark3.setContent(content);
				/*
				 * mymark.setContent("Latitud: "+lat+" <br> Longitud: "+lng+"<br> Nombre veh: "
				 * +objCatVeh.getNomVeh()+" <br> " + "Velocidad:	"+
				 * infoCoor.get(
				 * i).getVelocidad()+" <br> Fecha: "+infoCoor.get(i)
				 * .getFecha());
				 */
				mymark3.setOpen(true);
				myMap.appendChild(mymark3);
				binder.loadComponent(mymark3);
				break;
			}

		}
	}

	public void carOnMap() {
		Gmaps myMap1 = new Gmaps();
		boolean camVeh = false;
		stopTimerRuta();
		// ////////////////////////
		/*
		 * myMap.removeChild(mymark); myMap.removeChild(mypoly2);//123123
		 * myMap.getChildren().clear(); mypoly2 = new Gpolyline();//12123 mymark
		 * = new Gmarker(); myMap.appendChild(mymark);
		 * binder.loadComponent(myMap); myMap.setVisible(true);
		 * myMapAll.setVisible(false);
		 */

		// System.out.println("AquiEmpieza todo: idCatVehiculoAnt: "+idCatVehiculoAnt
		// +" objCatVeh.getIdVeh(): "+objCatVeh.getIdVeh() );
		if (idCatVehiculoAnt == 0) {
			System.out
					.println("------------------------------if(idCatVehiculoAnt.equals(0))------------------------------");

			myMap.getChildren().clear();
			// cleanMap();//XP
			camVeh = false;

			mypoly2.getChildren().clear();
			mypoly2.getRoot().getChildren().clear();
			mypoly2.getRoot().detach();
			myMap.removeChild(mypoly2);
			myMap.removeChild(mymark);// 123
			mypoly2 = new Gpolyline();
			mypoly2.setColor("#3333cc");
			myMap.appendChild(mypoly2);
			myMap.appendChild(mymark);

			infoCoorOnMap = new ArrayList<Coordenadas>();
			infoCoorModelOnMap = new ArrayList<Coordenadas>();

			westMapaGoogle3.setOpen(false);
			southMapaGoogle3.setOpen(false);
			Clients.showNotification(
					"Expandir la barra para observar mas opciones", "info",
					winMapGoogle2, "top_left", 7000);

		} else {
			System.out
					.println("------------------------------else------------------------------");
			if (!(objCatVeh.getIdVeh() == idCatVehiculoAnt)) {
				System.out
						.println("------------------------------if(!( objCatVeh.getIdentificador().equals(idCatVehiculoAnt) ))------------------------------");
				longAnt = 1000000;
				latAnt = 1000000;
				myMap.getChildren().clear();
				myMap.getAttributes().clear();
				// cleanMap();//XP
				mypoly2.getChildren().clear();
				mypoly2.getRoot().getChildren().clear();
				mypoly2.getRoot().detach();
				myMap.removeChild(mypoly2);
				myMap.removeChild(mymark);// 123
				mypoly2 = new Gpolyline();
				mypoly2.setColor("#3333cc");
				myMap.appendChild(mypoly2);
				myMap.appendChild(mymark);

				infoCoorOnMap = new ArrayList<Coordenadas>();
				infoCoorModelOnMap = new ArrayList<Coordenadas>();

				camVeh = true;
				westMapaGoogle3.setOpen(false);
				southMapaGoogle3.setOpen(false);
				Clients.showNotification(
						"Expandir la barra para observar mas opciones", "info",
						winMapGoogle2, "top_left", 7000);
				/*
				 * myMap= new Gmaps();
				 * 
				 * winMapGoogle2.appendChild(myMap); myMap.appendChild(mypoly);
				 */

				// pcGmaps.appendChild(myMap);
				// myMap.appendChild(mymark);
				// myMap.appendChild(mymark3);
				binder.loadComponent(myMap);

			} else
				camVeh = false;

		}

		/*
		 * if(objCatVeh!=null) { if(objCatVeh.getIdentificador()==null)
		 * {myMap.getChildren().clear();} else{ if(!(
		 * objCatVeh.getIdentificador().equals(idCatVehiculoAnt) ))
		 * myMap.getChildren().clear(); } }
		 */

		idCatVehiculoAnt = (objCatVeh.getIdVeh() == 0) ? 0 : objCatVeh
				.getIdVeh();

		Date _fecha = null;
		String recorrido = null;
		idVehiculo_Res = 0;
		myMapAll.setVisible(false);
		winMapGoogle2.removeChild(myMapAll);

		// id_coo_res=idCoo;
		myMap.setVisible(true);
		myMap.setShowZoomCtrl(true);
		mypoly2.setColor("#3333cc");
		myMap.appendChild(mypoly2);
		double ltd = 0;
		double lng = 0;
		double doubRecorrido = 0.0;
		String imagenVehiculo = "imagen..";
		String nameVeh = null;
		
		//Statement st = null;
		//ResultSet rs = null;
		//Connection conn = ConexionUtil.getConnection();
		DatosDao dat = new DatosDao();
		List<Coordenadas> infoCoorOnMapTot = new ArrayList<Coordenadas>();
		infoCoorOnMapTot = dat.VehiculoSobreMapaDao(objCatVeh.getIdVeh());
		
		
		
		
		try {
		/*	//st = conn.createStatement();
			myMap.appendChild(mypoly2);
			objCatVehAnt = objCatVeh;
			

			System.out
					.println(" "
							+ "select  coo.co_latitud, coo.co_longitud ,coo.ve_id, coo.co_fecha,coo.co_recorrido   "
							+ " from 	coordenadas coo "
							+ " where	coo.ve_id = "
							+ objCatVeh.getIdVeh()
							+ " and 	coo.co_fecha = (select  max(co.co_fecha) 	"
							+ "						from 	coordenadas co 	"
							+ "						where	co.ve_id = "
							+ objCatVeh.getIdVeh()
							+ " )  group by coo.ve_id;  ");

			rs = st
					.executeQuery("select  coo.co_latitud, coo.co_longitud ,coo.ve_id, coo.co_fecha,coo.co_recorrido   "
							+ " from 	coordenadas coo "
							+ " where	coo.ve_id = "
							+ objCatVeh.getIdVeh()
							+ " and 	coo.co_fecha = (select  max(co.co_fecha) 	"
							+ "						from 	coordenadas co 	"
							+ "						where	co.ve_id = "
							+ objCatVeh.getIdVeh()
							+ " )  group by coo.ve_id;  ");*/

			// System.out.println("idCoordenadas: "+idCoordenadas+" idCoo:"+idCoo+" imagenVehiculo:"+imagenVehiculo);
			coordOnMap = new Coordenadas();
			//while (rs.next()) 
			for(int i=0;i<infoCoorOnMapTot.size();i++){
				ltd = infoCoorOnMapTot.get(i).getLatitud();//rs.getDouble(1);
				lng = infoCoorOnMapTot.get(i).getLongitud();// rs.getDouble(2);
				imagenVehiculo = objCatVeh.getImaVeh();
				nameVeh = objCatVeh.getNomVeh();
				idVehiculo_Res = infoCoorOnMapTot.get(i).getIdVehiculo(); //rs.getInt(3);
				_fecha = infoCoorOnMapTot.get(i).getFecha(); //rs.getTimestamp(4);
				recorrido =String.valueOf(infoCoorOnMapTot.get(i).getCo_recorrido());// rs.getString(5);


				if ((longAnt != 1000000 && latAnt != 1000000)
						&& (lng != longAnt || ltd != latAnt)) { 
					mymark3.setLat(latAnt);
					mymark3.setLng(longAnt);
					mymark3.setOpen(true);
					// mymark3.setContent("XP");

					myMap.appendChild(mymark3);
					mymark3 = new Gmarker();
					if (camVeh == false)
						mypoly2.addPoint(ltd, lng, 3);

				}
				if (longAnt == 1000000 && latAnt == 1000000 && camVeh == false) {
					mypoly2.addPoint(ltd, lng, 3);
					westMapaGoogle3.setOpen(false);
					southMapaGoogle3.setOpen(false);
				}

				mypoly2.addPoint(ltd, lng, 3);
				longAnt = lng;
				latAnt = ltd;
				
				coordOnMap.setLatitud(infoCoorOnMapTot.get(i).getLatitud());
				coordOnMap.setLongitud(infoCoorOnMapTot.get(i).getLongitud());

				SimpleDateFormat sdf1 = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				//System.out.println("muestra hora ?: " + rs.getTimestamp(4));

				coordOnMap.setFecha(infoCoorOnMapTot.get(i).getFecha());
				coordOnMap.setCo_recorrido(infoCoorOnMapTot.get(i).getCo_recorrido());
				infoCoorOnMap.add(coordOnMap);
			}

			// System.out.println("llega al mark: "+"ltd: "+ltd+" lng: "+lng+
			// " imagenVehiculo: "+imagenVehiculo);

			myMap.panTo(ltd, lng);
			myMap.setZoom(19);
			mymark.setOpen(true);
			infoCoorModelOnMap = infoCoorOnMap;

			mymark.setIconImage(imagenVehiculo);// XP

			// System.out.println("myMap.getAttribute.toString(): "+myMap.getAttribute("Gmarker").toString());
			// //no funca
			// mymark.setOpen(true);

			Format formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if (/* recorrido.equals(null)|| */recorrido == null)
				recorrido = "0.0";
			else
				doubRecorrido = Double.valueOf(recorrido);

			mymark.setIconImageMap(imagenVehiculo);
			mymark.setLat(ltd);
			mymark.setLng(lng);
			/*
			 * mymark.setContent("Nombre veh: "+nameVeh+"  <br> Propietario: "+_user
			 * + " <br> Latitud: "+ltd+" <br> Longitud: "+ lng+" <br> Fecha: "+
			 * formatter.format(_fecha)+" <br> Recorrido: "+
			 * (Math.floor(doubRecorrido * 100) / 100));
			 * binder.loadAttribute(mymark, "Content");
			 */
			// binder.loadAttribute(mymark, "Context");
			binder.loadComponent(mymark);
			myMap.appendChild(mymark);
			// System.out.println("_fecha_2--::"+_fecha);
			timer2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public List<Coordenadas> getInfoCoorTotales() {
		return infoCoorTotales;
	}

	public void setInfoCoorTotales(List<Coordenadas> infoCoorTotales) {
		this.infoCoorTotales = infoCoorTotales;
	}

	public Div getDivControles() {
		return divControles;
	}

	public void setDivControles(Div divControles) {
		this.divControles = divControles;
	}

	public Timer getTimerRuta() {
		return timerRuta;
	}

	public void setTimerRuta(Timer timerRuta) {
		this.timerRuta = timerRuta;
	}

	public int getContRuta() {
		return contRuta;
	}

	public void setContRuta(int contRuta) {
		this.contRuta = contRuta;
	}

	public Timer getTimer2() {
		return timer2;
	}

	public void setTimer2(Timer timer2) {
		this.timer2 = timer2;
	}

	public List<Coordenadas> getInfoCoorOnMap() {
		return infoCoorOnMap;
	}

	public void setInfoCoorOnMap(List<Coordenadas> infoCoorOnMap) {
		this.infoCoorOnMap = infoCoorOnMap;
	}

	public List<Coordenadas> getInfoCoorModelOnMap() {
		return infoCoorModelOnMap;
	}

	public void setInfoCoorModelOnMap(List<Coordenadas> infoCoorModelOnMap) {
		this.infoCoorModelOnMap = infoCoorModelOnMap;
	}

	public Gpolyline getMypoly2() {
		return mypoly2;
	}

	public void setMypoly2(Gpolyline mypoly2) {
		this.mypoly2 = mypoly2;
	}

	public int getIdVehiculo_Res() {
		return idVehiculo_Res;
	}

	public void setIdVehiculo_Res(int idVehiculo_Res) {
		this.idVehiculo_Res = idVehiculo_Res;
	}

	public int getIdCatVehiculoAnt() {
		return idCatVehiculoAnt;
	}

	public void setIdCatVehiculoAnt(int idCatVehiculoAnt) {
		this.idCatVehiculoAnt = idCatVehiculoAnt;
	}

	public double getLongAnt() {
		return longAnt;
	}

	public void setLongAnt(double longAnt) {
		this.longAnt = longAnt;
	}

	public double getLatAnt() {
		return latAnt;
	}

	public void setLatAnt(double latAnt) {
		this.latAnt = latAnt;
	}

	public Coordenadas getCoord() {
		return coord;
	}

	public void setCoord(Coordenadas coord) {
		this.coord = coord;
	}

	public Coordenadas getCoordOnMap() {
		return coordOnMap;
	}

	public void setCoordOnMap(Coordenadas coordOnMap) {
		this.coordOnMap = coordOnMap;
	}

	public Div getIdSouthdiv1() {
		return idSouthdiv1;
	}

	public void setIdSouthdiv1(Div idSouthdiv1) {
		this.idSouthdiv1 = idSouthdiv1;
	}

	public Ginfo getInfo() {
		return info;
	}

	public void setInfo(Ginfo info) {
		this.info = info;
	}

	public List<Coordenadas> getInfoCoorModel() {
		return infoCoorModel;
	}

	public void setInfoCoorModel(List<Coordenadas> infoCoorModel) {
		this.infoCoorModel = infoCoorModel;
	}

	public Datebox getFecDesdeDia() {
		return fecDesdeDia;
	}

	public void setFecDesdeDia(Datebox fecDesdeDia) {
		this.fecDesdeDia = fecDesdeDia;
	}

	public Ginfo getMyinfo() {
		return myinfo;
	}

	public void setMyinfo(Ginfo myinfo) {
		this.myinfo = myinfo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Groupbox getGrbBusAva() {
		return grbBusAva;
	}

	public void setGrbBusAva(Groupbox grbBusAva) {
		this.grbBusAva = grbBusAva;
	}

	public Listbox getListCantMap() {
		return listCantMap;
	}

	public void setListCantMap(Listbox listCantMap) {
		this.listCantMap = listCantMap;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Combobox getCmbCantMap() {
		return cmbCantMap;
	}

	public void setCmbCantMap(Combobox cmbCantMap) {
		this.cmbCantMap = cmbCantMap;
	}

	public List<Mapas> getInfoMapas() {
		return infoMapas;
	}

	public void setInfoMapas(List<Mapas> infoMapas) {
		this.infoMapas = infoMapas;
	}

	public Mapas getObjMap() {
		return objMap;
	}

	public void setObjMap(Mapas objMap) {
		this.objMap = objMap;
	}

	public Tab getPlusTab() {
		return plusTab;
	}

	public void setPlusTab(Tab plusTab) {
		this.plusTab = plusTab;
	}

	public Tabpanels getTps1() {
		return tps1;
	}

	public void setTps1(Tabpanels tps1) {
		this.tps1 = tps1;
	}

	public Tabpanel getTp1() {
		return tp1;
	}

	public void setTp1(Tabpanel tp1) {
		this.tp1 = tp1;
	}

	public Borderlayout getBly1() {
		return bly1;
	}

	public void setBly1(Borderlayout bly1) {
		this.bly1 = bly1;
	}

	public Center getCnt1() {
		return cnt1;
	}

	public void setCnt1(Center cnt1) {
		this.cnt1 = cnt1;
	}

	public Panel getPnl1() {
		return pnl1;
	}

	public void setPnl1(Panel pnl1) {
		this.pnl1 = pnl1;
	}

	public Tabbox getTbx1() {
		return tbx1;
	}

	public void setTbx1(Tabbox tbx1) {
		this.tbx1 = tbx1;
	}

	public Button getBtnNueRut() {
		return btnNueRut;
	}

	public void setBtnNueRut(Button btnNueRut) {
		this.btnNueRut = btnNueRut;
	}

	public West getWestMapaGoogle3() {
		return westMapaGoogle3;
	}

	public void setWestMapaGoogle3(West westMapaGoogle3) {
		this.westMapaGoogle3 = westMapaGoogle3;
	}

	public South getSouthMapaGoogle3() {
		return southMapaGoogle3;
	}

	public void setSouthMapaGoogle3(South southMapaGoogle3) {
		this.southMapaGoogle3 = southMapaGoogle3;
	}

	public West getWest() {
		return west;
	}

	public void setWest(West west) {
		this.west = west;
	}

	public CategoriaVehiculo getObjVehEnUso() {
		return objVehEnUso;
	}

	public void setObjVehEnUso(CategoriaVehiculo objVehEnUso) {
		this.objVehEnUso = objVehEnUso;
	}

	public CategoriaVehiculo getObjCatVehAnt() {
		return objCatVehAnt;
	}

	public void setObjCatVehAnt(CategoriaVehiculo objCatVehAnt) {
		this.objCatVehAnt = objCatVehAnt;
	}

	public boolean isMapaEnUso() {
		return mapaEnUso;
	}

	public void setMapaEnUso(boolean mapaEnUso) {
		this.mapaEnUso = mapaEnUso;
	}

	public CategoriaVehiculo getObjCatSelected() {
		return objCatSelected;
	}

	public void setObjCatSelected(CategoriaVehiculo objCatSelected) {
		this.objCatSelected = objCatSelected;
	}

	public Coordenadas getObjCoordenadas() {
		return objCoordenadas;
	}

	public void setObjCoordenadas(Coordenadas objCoordenadas) {
		this.objCoordenadas = objCoordenadas;
	}

	public Listbox getListCoord() {
		return listCoord;
	}

	public void setListCoord(Listbox listCoord) {
		this.listCoord = listCoord;
	}

	public List<Coordenadas> getInfoCoor() {
		return infoCoor;
	}

	public void setInfoCoor(List<Coordenadas> infoCoor) {
		this.infoCoor = infoCoor;
	}

	public Gpolyline getMypoly() {
		return mypoly;
	}

	public void setMypoly(Gpolyline mypoly) {
		this.mypoly = mypoly;
	}

	public CategoriaVehiculo getObjCatVeh() {
		return objCatVeh;
	}

	public void setObjCatVeh(CategoriaVehiculo objCatVeh) {
		this.objCatVeh = objCatVeh;
	}

	public List<CategoriaVehiculo> getCatVehLlenLis() {
		return catVehLlenLis;
	}

	public void setCatVehLlenLis(List<CategoriaVehiculo> catVehLlenLis) {
		this.catVehLlenLis = catVehLlenLis;
	}

	public String getResVeh() {
		return resVeh;
	}

	public void setResVeh(String resVeh) {
		this.resVeh = resVeh;
	}

	public Datebox getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Datebox fecDesde) {
		this.fecDesde = fecDesde;
	}

	public Datebox getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Datebox fecHasta) {
		this.fecHasta = fecHasta;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Combobox getCmbCatVeh() {
		return cmbCatVeh;
	}

	public void setCmbCatVeh(Combobox cmbCatVeh) {
		this.cmbCatVeh = cmbCatVeh;
	}

	public List<CategoriaVehiculo> getCatVeh() {
		return catVeh;
	}

	public void setCatVeh(List<CategoriaVehiculo> catVeh) {
		this.catVeh = catVeh;
	}

	public Panelchildren getPcGmaps() {
		return pcGmaps;
	}

	public void setPcGmaps(Panelchildren pcGmaps) {
		this.pcGmaps = pcGmaps;
	}

	public Gmaps getMyMapAll() {
		return myMapAll;
	}

	public void setMyMapAll(Gmaps myMapAll) {
		this.myMapAll = myMapAll;
	}

	public Window getWinMapGoogle2() {
		return winMapGoogle2;
	}

	public void setWinMapGoogle2(Window winMapGoogle2) {
		this.winMapGoogle2 = winMapGoogle2;
	}

	public List<Marks> getGMarkAll() {
		return gMarkAll;
	}

	public void setGMarkAll(List<Marks> markAll) {
		gMarkAll = markAll;
	}

	public int getIdCoordenadas() {
		return idCoordenadas;
	}

	public void setIdCoordenadas(int idCoordenadas) {
		this.idCoordenadas = idCoordenadas;
	}

	public List<Gmarker> getListMarks() {
		return listMarks;
	}

	public void setListMarks(List<Gmarker> listMarks) {
		this.listMarks = listMarks;
	}
	public List<CategoriaVehiculo> getCatVehModel() {
		return catVehModel;
	}

	public void setCatVehModel(List<CategoriaVehiculo> catVehModel) {
		this.catVehModel = catVehModel;
	}

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Listbox getLisVehiculo() {
		return lisVehiculo;
	}

	public void setLisVehiculo(Listbox lisVehiculo) {
		this.lisVehiculo = lisVehiculo;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public List<Marks> getGMark() {
		return gMark;
	}

	public void setGMark(List<Marks> mark) {
		gMark = mark;
	}

	public Gmarker getMymark2() {
		return mymark2;
	}

	public void setMymark2(Gmarker mymark2) {
		this.mymark2 = mymark2;
	}

	public Gmarker getMymark3() {
		return mymark3;
	}

	public void setMymark3(Gmarker mymark3) {
		this.mymark3 = mymark3;
	}

	public Gmarker getMymark4() {
		return mymark4;
	}

	public void setMymark4(Gmarker mymark4) {
		this.mymark4 = mymark4;
	}

	public Gmarker getMymark() {
		return mymark;
	}

	public void setMymark(Gmarker mymark) {
		this.mymark = mymark;
	}

	public Gmaps getMyMap() {
		return myMap;
	}

	public void setMyMap(Gmaps myMap) {
		this.myMap = myMap;
	}

	public Fisheyebar getFsb() {
		return fsb;
	}

	public void setFsb(Fisheyebar fsb) {
		this.fsb = fsb;
	}
	

	// DEMO

	public void cargaVehiculos(String identVeh, String img) {
		MapaGoogle2Control cnt = new MapaGoogle2Control();
		System.out.println("selected");
		System.out.println("identVeh: " + identVeh + " img: " + img);

		if (identVeh.equals("a")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682, -79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-2.1804098646436682);
			mymark.setLng(-79.89120483398437);

		}

		if (identVeh.equals("b")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-1.1804098646436682, -79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-1.1804098646436682);
			mymark.setLng(-79.89120483398437);
		}
		if (identVeh.equals("c")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-1.6587038068676118, -80.88134765625);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-1.6587038068676118);
			mymark.setLng(-80.88134765625);

		}
		if (identVeh.equals("d")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-0.6587038068676118, -80.88134765625);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);

		}
		if (identVeh.equals("e")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682, -79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);
		}
		if (identVeh.equals("f")) {
			// cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682, -79.89120483398437);
			myMap.setZoom(2);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);
		}

	}

	public List<Marks> getgMark() {
		return gMark;
	}

	public void setgMark(List<Marks> gMark) {
		this.gMark = gMark;
	}

	public List<Marks> getgMarkAll() {
		return gMarkAll;
	}

	public void setgMarkAll(List<Marks> gMarkAll) {
		this.gMarkAll = gMarkAll;
	}

	public List<Coordenadas> getInfoCoorFil() {
		return infoCoorFil;
	}

	public void setInfoCoorFil(List<Coordenadas> infoCoorFil) {
		this.infoCoorFil = infoCoorFil;
	}

	public List<Coordenadas> getInfoCoorCompletas() {
		return infoCoorCompletas;
	}

	public void setInfoCoorCompletas(List<Coordenadas> infoCoorCompletas) {
		this.infoCoorCompletas = infoCoorCompletas;
	}

	public Caption getCptBusquedaAvanzada() {
		return cptBusquedaAvanzada;
	}

	public void setCptBusquedaAvanzada(Caption cptBusquedaAvanzada) {
		this.cptBusquedaAvanzada = cptBusquedaAvanzada;
	}

	public Tab getTabPorDia() {
		return tabPorDia;
	}

	public void setTabPorDia(Tab tabPorDia) {
		this.tabPorDia = tabPorDia;
	}

	public Tab getTabAvanzada() {
		return tabAvanzada;
	}

	public void setTabAvanzada(Tab tabAvanzada) {
		this.tabAvanzada = tabAvanzada;
	}

	public Label getLblDia() {
		return lblDia;
	}

	public void setLblDia(Label lblDia) {
		this.lblDia = lblDia;
	}

	public Image getImgTrazarRuta6() {
		return imgTrazarRuta6;
	}

	public void setImgTrazarRuta6(Image imgTrazarRuta6) {
		this.imgTrazarRuta6 = imgTrazarRuta6;
	}

	public Label getLbldesde() {
		return lbldesde;
	}

	public void setLbldesde(Label lbldesde) {
		this.lbldesde = lbldesde;
	}

	public Label getLblHasta() {
		return lblHasta;
	}

	public void setLblHasta(Label lblHasta) {
		this.lblHasta = lblHasta;
	}

	public Image getImgTrazaRuta7() {
		return imgTrazaRuta7;
	}

	public void setImgTrazaRuta7(Image imgTrazaRuta7) {
		this.imgTrazaRuta7 = imgTrazaRuta7;
	}

	public Listheader getLshVehiculo() {
		return lshVehiculo;
	}

	public void setLshVehiculo(Listheader lshVehiculo) {
		this.lshVehiculo = lshVehiculo;
	}

	public Image getImgBusquedaAvanzada() {
		return imgBusquedaAvanzada;
	}

	public void setImgBusquedaAvanzada(Image imgBusquedaAvanzada) {
		this.imgBusquedaAvanzada = imgBusquedaAvanzada;
	}

	public Image getImgReporteHoy() {
		return imgReporteHoy;
	}

	public void setImgReporteHoy(Image imgReporteHoy) {
		this.imgReporteHoy = imgReporteHoy;
	}

	public Image getImgLocalizarVehiculo() {
		return imgLocalizarVehiculo;
	}

	public void setImgLocalizarVehiculo(Image imgLocalizarVehiculo) {
		this.imgLocalizarVehiculo = imgLocalizarVehiculo;
	}

	

	public Listheader getLshLatitud() {
		return lshLatitud;
	}

	public void setLshLatitud(Listheader lshLatitud) {
		this.lshLatitud = lshLatitud;
	}

	public Listheader getLshLongitud() {
		return lshLongitud;
	}

	public void setLshLongitud(Listheader lshLongitud) {
		this.lshLongitud = lshLongitud;
	}

	public Listheader getLshFecha() {
		return lshFecha;
	}

	public void setLshFecha(Listheader lshFecha) {
		this.lshFecha = lshFecha;
	}

	public Caption getCptMapasGenerados() {
		return cptMapasGenerados;
	}

	public void setCptMapasGenerados(Caption cptMapasGenerados) {
		this.cptMapasGenerados = cptMapasGenerados;
	}

	public static void main(String args[]) {
		ResultSet rs = null;
		Statement st = null;
		Connection conn = ConexionUtil.getConnection();

		MapaGoogle2Control map = new MapaGoogle2Control();

		// restaDia();

		// map.presentaVehiculo();

		/*
		 * try{ st=conn.createStatement(); rs =
		 * st.executeQuery("SELECT * FROM vehiculos ");
		 * 
		 * System.out.println("funk ocall 2");
		 * 
		 * while(rs.next()){
		 * 
		 * System.out.println(rs.getString(1)); }
		 * 
		 * 
		 * } catch(Exception e) {e.printStackTrace();}
		 */

	}

}