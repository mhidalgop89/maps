package ucsg.gmaps.control;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolyline;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;



import Util.CategoriaVehiculo;
import Util.Coordenadas;
import Util.Marks;

import java.awt.Panel;
import java.io.Writer;
import java.lang.Comparable;
import ConexionUtil.ConexionUtil;



public class MapaGoogle2Control extends GenericForwardComposer{

	private Fisheyebar fsb;
	private Gmaps myMap;
	private Gmarker mymark;
	private Gmarker mymark2;
	private Gmarker mymark3;
	private Gpolyline mypoly;
	int idCoordenadas_res=0;
	int idVehiculo_Res=0;//rescato el valor del vehiculo de la funcion presentaVehiculo() para enviarla al metodo que caronmap() y que refresque con el ultimo  
	private Gmarker mymark4= new Gmarker();
	private List<Gmarker> listMarks= new ArrayList<Gmarker>();
	private List<Marks> gMark =new ArrayList<Marks>();
	private List<CategoriaVehiculo> catVehLlenLis =new ArrayList<CategoriaVehiculo>();
	CategoriaVehiculo objCatVeh;
	CategoriaVehiculo objCatVehAnt=null;
	private List<Marks> gMarkAll =new ArrayList<Marks>();
	private List<CategoriaVehiculo> catVeh= new ArrayList<CategoriaVehiculo>();
	private Listbox lisVehiculo=null;
	private String _user;
	private Window winMapGoogle2;
	int idCoordenadas=0;
	private Combobox cmbCatVeh;
	private String categoria="0";
	private Gmaps myMapAll;
	private West westMapGoogle2;
	private Panel pcGmapsP;
	private Panelchildren pcGmaps;
	String id_coo_res;
	double longAnt=1000000;
	double latAnt=1000000;
	int idCatVehiculoAnt=0;
	private List<Coordenadas> infoCoor = new ArrayList<Coordenadas>();
	private List<Coordenadas> infoCoorModel = new ArrayList<Coordenadas>();
	Coordenadas coord;
	CategoriaVehiculo objCatSelected = new CategoriaVehiculo();
	AnnotateDataBinder binder;
	
	
	
	public Coordenadas getCoord() {
		return coord;
	}


	public void setCoord(Coordenadas coord) {
		this.coord = coord;
	}


	public List<Coordenadas> getInfoCoor() {
		return infoCoor;
	}


	public void setInfoCoor(List<Coordenadas> infoCoor) {
		this.infoCoor = infoCoor;
	}


	public CategoriaVehiculo getObjCatVehAnt() {
		return objCatVehAnt;
	}


	public void setObjCatVehAnt(CategoriaVehiculo objCatVehAnt) {
		this.objCatVehAnt = objCatVehAnt;
	}


	public West getWestMapGoogle2() {
		return westMapGoogle2;
	}


	public void setWestMapGoogle2(West westMapGoogle2) {
		this.westMapGoogle2 = westMapGoogle2;
	}


	public Panel getPcGmapsP() {
		return pcGmapsP;
	}


	public void setPcGmapsP(Panel pcGmapsP) {
		this.pcGmapsP = pcGmapsP;
	}


	public CategoriaVehiculo getObjCatSelected() {
		return objCatSelected;
	}


	public void setObjCatSelected(CategoriaVehiculo objCatSelected) {
		this.objCatSelected = objCatSelected;
	}


	public int getIdCatVehiculoAnt() {
		return idCatVehiculoAnt;
	}


	public void setIdCatVehiculoAnt(int idCatVehiculoAnt) {
		this.idCatVehiculoAnt = idCatVehiculoAnt;
	}


	public Gpolyline getMypoly() {
		return mypoly;
	}


	public void setMypoly(Gpolyline mypoly) {
		this.mypoly = mypoly;
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


	public int getIdVehiculo_Res() {
		return idVehiculo_Res;
	}


	public void setIdVehiculo_Res(int idVehiculo_Res) {
		this.idVehiculo_Res = idVehiculo_Res;
	}


	public int getIdCoordenadas_res() {
		return idCoordenadas_res;
	}


	public void setIdCoordenadas_res(int idCoordenadas_res) {
		this.idCoordenadas_res = idCoordenadas_res;
	}


	public String getId_coo_res() {
		return id_coo_res;
	}


	public void setId_coo_res(String id_coo_res) {
		this.id_coo_res = id_coo_res;
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


	public String get_user() {
		return _user;
	}


	public void set_user(String _user) {
		this._user = _user;
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
	
	
	
		public void fillListBox(double ltd, double lng ,String img, int idVeh,String nombreVehiculo)
	{
		
		Marks mark1= new Marks();
		mark1.setLtd(ltd);
		mark1.setLng(lng);
		mark1.setImage(img);
		mark1.setId_vehiculo(idVeh);
		mark1.setNombreVehiculo(nombreVehiculo);
		System.out.println("system llega::");
		binder.loadComponent(lisVehiculo);
		
		gMark.add(mark1);
		
		
		
	}
	
		public Date restaDia( Date fec, long cantDias )
		{
			Date fecRet = new Date();
			
			
			
			long diferenciaEnDias = cantDias;
			long tiempoActual = fec.getTime();
			long dias = diferenciaEnDias * 24 * 60 * 60 * 1000;
			fecRet = new Date(tiempoActual - dias);
			
			/*Date fechaActual =  (Date) Calendar.getInstance().getTime();
			long tiempoActual = fechaActual.getTime();
			long unDia = diferenciaEnDias * 24 * 60 * 60 * 1000;
			Date fechaAyer = new Date(tiempoActual - unDia);
			 
			System.out.println(fechaAyer.toString());*/
			System.out.println("fecRet::::    "+fecRet.toString());
			
			return fecRet;
		}

		public Date restaHora( Date fec, int cantHoras )
		{
			Date fecRet = new Date();
			
			
			
			int diferenciaEnHoras = cantHoras;
			long tiempoActual = fec.getTime();
			long horas = diferenciaEnHoras * 60 * 60 * 1000;
			fecRet = new Date(tiempoActual - horas);
			
			/*Date fechaActual =  (Date) Calendar.getInstance().getTime();
			long tiempoActual = fechaActual.getTime();
			long unDia = diferenciaEnDias * 24 * 60 * 60 * 1000;
			Date fechaAyer = new Date(tiempoActual - unDia);
			 
			System.out.println(fechaAyer.toString());*/
			System.out.println("fecRet::::    "+fecRet.toString());
			
			return fecRet;
		}
	public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winMapGoogle2",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		binder = new AnnotateDataBinder(cmp);
    		
    		fillCmbVehCat();
    		myMap.removeChild(mymark3);
    		
    		
    		binder.loadComponent(cmbCatVeh);
    		presentaVehiculo();
    		//binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }
	
	
	public void onTimer()
	{
		int ultCoor=0;
		String ultCoorStr=null;
		if (idVehiculo_Res != 0)
		{
			carOnMap();
		
		}
	}
	
	// llena en el list item de los vehiculos
	public void presentaVehiculo()
	{
		Marks mark1= new Marks();
		CategoriaVehiculo cv1= new CategoriaVehiculo(); 
		int adm=0;
		int usu_id=0;
		long hora1=(long) 0.042;
		Date fechaHoy = new Date();
		Date fecha1= restaHora(fechaHoy,1);
		Date fecha8= restaHora(fechaHoy, 8);
		Date fecha24=restaDia(fechaHoy, 1);
		Statement st_us=null;
		ResultSet rs_us=null;
		
		Statement st=null;
		ResultSet rs=null;
		MapaGoogle2Control mgc2= new MapaGoogle2Control();
		System.out.println("mg2: Presenta vehiculo()_Categoria_print: "+categoria+" --cmbCatVeh.getSelectedIndex(): "+ cmbCatVeh.getSelectedIndex());/////prueba lo que tiene categoria
		
		if (!categoria.equals("0")) 
		{System.out.println("entra: if (!categoria.equals('0'))");
		categoria= ((cmbCatVeh.getSelectedIndex() == 0)||(cmbCatVeh.getSelectedIndex()==-1))?"0":cmbCatVeh.getSelectedItem().getContext();
		}
		System.out.println("mg2: Presenta vehiculo()_Categoria_print: "+categoria+" --cmbCatVeh.getSelectedIndex(): "+ cmbCatVeh.getSelectedIndex());/////prueba lo que tiene categoria
		//System.out.println(" cmbCatVeh.getSelectedItem().getContext(): "+cmbCatVeh.getSelectedItem().getContext());//saca esta movida
		//gMark= new ArrayList<Marks>();
		Connection conn= ConexionUtil.getConnection();

		try{
			_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
			
			st_us=conn.createStatement();
			
			rs_us = st_us.executeQuery("select per_id,usu_id from usuarios where user = '"+ _user+"'");
			
			while(rs_us.next())
			{
				adm = rs_us.getInt("per_id");
				usu_id = rs_us.getInt("usu_id");
			}
			
			
			System.out.println(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user ,max(coor.co_fecha) maxFec  "+
								" from vehiculos veh	"+
								" inner join usuarios usu on veh.usu_id= usu.usu_id	"+ 
								" inner join coordenadas coor on coor.ve_id = veh.ve_id 	"+
								" inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "+usu_id+"   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "+usu_id+"  group by veh.ve_id ") ;
			
			st=conn.createStatement();
			
			rs = st.executeQuery(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user ,max(coor.co_fecha) maxFec  "+
					" from vehiculos veh	"+
					" inner join usuarios usu on veh.usu_id= usu.usu_id	"+ 
					" inner join coordenadas coor on coor.ve_id = veh.ve_id 	"+
					" inner join grupo gr  on gr.gr_id  in (   select gr_id from rel_usu_grupo  where usu_id = "+usu_id+"   ) and gr.gr_id = veh.gr_id or veh.usu_id =  "+usu_id+"  group by veh.ve_id ");
			
			/*
			rs = st.executeQuery(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user ,max(coor.co_fecha) maxFec "+
" from vehiculos veh,usuarios usu  ,coordenadas coor "+
" where veh.usu_id= usu.usu_id "+
" and coor.ve_id = veh.ve_id "+
" and veh.estado='A'  "+
" group by veh.ve_nombre ,veh.ve_categoria , veh.ve_id , veh.ve_imagen , usu.user ");*/
			
			/*rs = st.executeQuery(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user" +
					" from vehiculos veh,usuarios usu " +
					" where veh.usu_id= usu.usu_id and veh.estado='A' ;");
			
			System.out.println(" select veh.ve_nombre nombreVehiculo,veh.ve_categoria id_categoria, veh.ve_id idVehiculo, veh.ve_imagen imagen, usu.user user" +
					" from vehiculos veh,usuarios usu " +
					" where veh.usu_id= usu.usu_id and veh.estado='A' ;" );*/
			
			
			
			/* +
					" and usu.user = '"+_user+"'; ");*/
			
			//catVehLlenLis
			/*int id_categoria;
			String descripcion;
			String identificador;
			String nomVeh;
			int idUser;*/
			
			
			
			while(rs.next())
			{
			cv1.setNomVeh(rs.getString("nombreVehiculo"));
			cv1.setId_categoria(rs.getInt("id_categoria"));
			cv1.setIdVeh(rs.getInt("idVehiculo"));
			cv1.setImaVeh(rs.getString("imagen"));
			cv1.setFechaMax(rs.getDate("maxFec"));
			//if(rs.getString("user").equals(_user)||adm == 1)
			if(objCatSelected.getId_categoria() == cv1.getId_categoria() || objCatSelected.getId_categoria()==0)
				catVehLlenLis.add(cv1);
			cv1.setImgAct("/Img/greenCircle1.jpg");
			//System.out.println("fecha1: "+fecha1 + " -fecha8: "+fecha8+" fecha24: "+fecha24+ "fechaHoy: "+ fechaHoy);
			if (rs.getDate("maxFec").before(fecha1))
				cv1.setImgAct("/Img/greenCircle1.jpg");
			if (rs.getDate("maxFec").before(fecha8))
				cv1.setImgAct("/Img/yellowCircle1.jpg");
			if (rs.getDate("maxFec").before(fecha24))
				cv1.setImgAct("/Img/redCircle1.jpg");
			
			cv1=new CategoriaVehiculo();
				
			}
			

			//System.out.println("llega::::::::::::::::llega");
			binder.loadComponent(lisVehiculo );
			catVehLlenLis = new ArrayList<CategoriaVehiculo>();
		//gMark =new ArrayList<Marks>();
			
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		
		
	}
	
	
	
	
	

	public void presentaVehiculo1(double ltd, double lng)
	{System.out.println("ltd: "+ltd+" lng: "+lng);
	
		
		try{
		
		myMap.setLat(ltd);
		myMap.setLng(lng);
		//myMap.panTo(ltd, lng);
		myMap.setZoom(16);
		
		}
		catch(Exception e)
		{e.printStackTrace();}
	}
	
	
	public void nuevoRastreo()
	{
		
		Executions.sendRedirect("MapaGoogle2.zul");
		
	}
	
	
	public void carOnMap()
	{ Gmaps myMap1 = new Gmaps();
	boolean camVeh=false;
	
		//System.out.println("AquiEmpieza todo: idCatVehiculoAnt: "+idCatVehiculoAnt +" objCatVeh.getIdVeh(): "+objCatVeh.getIdVeh() );
		if(idCatVehiculoAnt==0)
		{	System.out.println("------------------------------if(idCatVehiculoAnt.equals(0))------------------------------");
			if(1==1)				
			myMap.getChildren().clear();
			
			camVeh=false;
			
		}
		else
		{System.out.println("------------------------------else------------------------------");
			if(!( objCatVeh.getIdVeh() == idCatVehiculoAnt))
				{ System.out.println("------------------------------if(!( objCatVeh.getIdentificador().equals(idCatVehiculoAnt) ))------------------------------");
				longAnt=1000000;
				latAnt=1000000;
				myMap.getChildren().clear();
				myMap.getAttributes().clear();
				
				mypoly.getChildren().clear();
				mypoly.getRoot().getChildren().clear();
				mypoly.getRoot().detach();
				myMap.removeChild(mypoly);
				camVeh=true;
				westMapGoogle2.setOpen(false);
				/*myMap= new Gmaps();
				
				winMapGoogle2.appendChild(myMap);
				myMap.appendChild(mypoly);*/
				
				//pcGmaps.appendChild(myMap);
				
				//myMap.appendChild(mymark);
				//myMap.appendChild(mymark3);
				
				binder.loadComponent(myMap);
				
				}
			else
				camVeh=false;
			
		}
		
	
		
		/*if(objCatVeh!=null)
		{	
			if(objCatVeh.getIdentificador()==null)
				{myMap.getChildren().clear();}
			else{
			if(!( objCatVeh.getIdentificador().equals(idCatVehiculoAnt) ))
					myMap.getChildren().clear();
			}
		}*/
		
		idCatVehiculoAnt=(objCatVeh.getIdVeh()==0)?0:objCatVeh.getIdVeh();
		
		
		Date _fecha=null;
		String recorrido=null;
		idVehiculo_Res=0;
		myMapAll.setVisible(false);
		winMapGoogle2.removeChild(myMapAll);
	
		
		//id_coo_res=idCoo;
		myMap.setVisible(true);
		myMap.setShowZoomCtrl(true);
		myMap.appendChild(mypoly);
	double ltd = 0;
	double lng = 0;
	double doubRecorrido=0.0;
	String imagenVehiculo="imagen..";
	String nameVeh=null;
	//idCoordenadas= Integer.parseInt(idCoo);
	Statement st=null;
	ResultSet rs=null;
	//myMap.removeChild(mymark);
	
	//mymark.setOpen(true);
	//gMark= new ArrayList<Marks>();
	Connection conn= ConexionUtil.getConnection();
	try{
		st=conn.createStatement();
		
		objCatVehAnt=objCatVeh;
		//westMapGoogle2.setOpen(false);
		
		
		System.out.println("/*sql::*/"+"select  coo.co_latitud, coo.co_longitud ,coo.ve_id, coo.co_fecha,coo.co_recorrido " +
				" from 	coordenadas coo " +
				" where	coo.ve_id = "+objCatVeh.getIdVeh()+ 
				" and 	coo.co_fecha = (select  max(co.co_fecha) 	" +
				"						from 	coordenadas co 	" +
				"						where	co.ve_id = "+ objCatVeh.getIdVeh()+" ); ");
		
		rs = st.executeQuery("select  coo.co_latitud, coo.co_longitud ,coo.ve_id, coo.co_fecha,coo.co_recorrido   " +
				" from 	coordenadas coo " +
				" where	coo.ve_id = "+objCatVeh.getIdVeh()+ 
				" and 	coo.co_fecha = (select  max(co.co_fecha) 	" +
				"						from 	coordenadas co 	" +
				"						where	co.ve_id = "+ objCatVeh.getIdVeh()+" ); ");
		
		//System.out.println("idCoordenadas: "+idCoordenadas+" idCoo:"+idCoo+" imagenVehiculo:"+imagenVehiculo);
		coord = new Coordenadas();
		while (rs.next())
		{
			ltd= rs.getDouble(1);
			lng=rs.getDouble(2);
			imagenVehiculo= objCatVeh.getImaVeh();
			nameVeh= objCatVeh.getNomVeh() ;
			idVehiculo_Res=rs.getInt(3);
			//_fecha = rs.getDate(4) ;
			_fecha = rs.getTimestamp(4);
			recorrido=rs.getString(5);
			
			System.out.println(" longAnt: "+longAnt+ " latAnt: "+latAnt+ " lng: "+lng+" ltd: "+ltd );
			
			
			
			if((longAnt!=1000000 && latAnt!=1000000)&&(lng!=longAnt || ltd!=latAnt))
			{	System.out.println("if entra polyline");
				mymark3.setLat(latAnt);
				mymark3.setLng(longAnt);
				mymark3.setOpen(true);
				//mymark3.setContent("XP");
				
				myMap.appendChild(mymark3);
				mymark3= new Gmarker();
				if(camVeh == false)
				mypoly.addPoint(ltd, lng, 3);
				
			}
			if(longAnt==1000000 && latAnt==1000000 && camVeh==false)
				{mypoly.addPoint(ltd, lng, 3);
				westMapGoogle2.setOpen(false);
				}
			
			longAnt=lng;
			latAnt=ltd;
			System.out.println("_fecha_1--::"+_fecha);
			coord.setLatitud(rs.getDouble(1));
			coord.setLongitud(rs.getDouble(2));
			coord.setFecha(rs.getDate(4));
			coord.setCo_recorrido(rs.getDouble(5));
			infoCoor.add(coord);
		}
		
		//System.out.println("llega al mark: "+"ltd: "+ltd+" lng: "+lng+ " imagenVehiculo: "+imagenVehiculo);
	
		myMap.panTo(ltd, lng);
		myMap.setZoom(19);
		mymark.setOpen(true);
		infoCoorModel = infoCoor;
		
		
		
		mymark.setIconImage(imagenVehiculo);

		//System.out.println("myMap.getAttribute.toString(): "+myMap.getAttribute("Gmarker").toString()); //no funca
		//mymark.setOpen(true);
		
		Format formatter;
		formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(recorrido.equals(null)|| recorrido==null)
			recorrido="0.0";
		else
			doubRecorrido= Double.valueOf(recorrido);
		
		mymark.setIconImageMap(imagenVehiculo);
		mymark.setLat(ltd);
		mymark.setLng(lng);
		/*mymark.setContent("Nombre veh: "+nameVeh+"  <br> Propietario: "+_user + " <br> Latitud: "+ltd+" <br> Longitud: "+ lng+" <br> Fecha: "+ formatter.format(_fecha)+" <br> Recorrido: "+ (Math.floor(doubRecorrido * 100) / 100));
		binder.loadAttribute(mymark, "Content");*/
		//binder.loadAttribute(mymark, "Context");
		binder.loadComponent(mymark);
		myMap.appendChild(mymark);
		System.out.println("_fecha_2--::"+_fecha);
	}
	catch(Exception e)
	{e.printStackTrace();}
		
		
		
	}
	public void onMapClick(double lat, double lng) {
    	
    	//MapMouseEvent event2 = new MapMouseEvent(_user, mymark2, myMapAll, lat, lng, idCoordenadas, idCoordenadas, idCoordenadas, idCoordenadas, idCoordenadas);
    	String content="";
	 //System.out.println("listen lat: "+ lat+" lng: "+lng);
	 		/*mymark2 = new Gmarker();
         	mymark2 =  
         		event.getGmarker();*/
	 
	 for(int i=0;i<infoCoorModel.size();i++)		
	 {
		 if(infoCoorModel.get(i).getLatitud()==lat && infoCoorModel.get(i).getLongitud()==lng)
		 {
			 mymark.setLat(lat);
			 mymark.setLng(lng);
			 content="<table border='1'> "+
		 		" <tr> "+
		 		" <th bgcolor='#ccccff' colspan=2 align=center>"+objCatVeh.getNomVeh()+"</th> "+
		 		" </tr> "+
		 		" <tr> "+
		 		" <td> Lat: </td><td>"+lat+"</td> "+
		 		" </tr> "+
		 		" <tr> "+
		 		" <td>Lng: </td><td>"+lng+"</td> "+
		 		" </tr> "+
		 		" <tr> "+
		 		" <td>Velocidad: </td><td>"+infoCoorModel.get(i).getVelocidad()+"</td> "+
		 		" </tr> "+
		 		" <tr> "+
		 		" <td>Fecha: </td><td>"+infoCoorModel.get(i).getFecha()+"</td> "+
		 		" </tr> "+
		 		" </table>";
			 	mymark.setContent(content);
		 		/*mymark.setContent("Latitud: "+lat+" <br> Longitud: "+lng+"<br> Nombre veh: "+objCatVeh.getNomVeh()+" <br> " +
		 							"Velocidad:	"+ infoCoor.get(i).getVelocidad()+" <br> Fecha: "+infoCoor.get(i).getFecha());*/
		 		mymark.setOpen(true);
		 		myMap.appendChild(mymark);
	    		binder.loadComponent(mymark);
	    		break;
		 }
		 
	 }
	}
	
	
	/////llena todos los carros en el mapa 
	
	public void allCarsOnMap ()
	{
		idVehiculo_Res=0;
		Marks mark1= new Marks();

		myMap.setVisible(false);
	
		myMapAll.setVisible(true);
		
		System.out.println("allCarsOnMapAll");
		
		int count=-1;
		Statement st=null;
		ResultSet rs=null;
		MapaGoogle2Control mgc2= new MapaGoogle2Control();
		Connection conn= ConexionUtil.getConnection();

		try{

			_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
			System.out.println("_user mario_enteoria: "+ _user);
			st=conn.createStatement();
			String sql= "select coo.co_latitud, coo.co_longitud, veh.Ve_imagen,veh.ve_id , veh.ve_nombre, coo.co_Id, co_fecha "+
			" from vehiculos veh, coordenadas coo "+
			" where veh.Ve_id=coo.ve_Id "+
			" and veh.Usu_id=( "+
			" select Usu_id "+
			" from usuarios "+
			" where user = '"+_user +
			"' ) "+
			" and co_fecha = (  select max(co1.co_fecha) from coordenadas co1 "+  
					" where  co1.ve_id = veh.Ve_id ) "+
			" group by ve_id;";
			System.out.println("sql allCarsOnMap: "+sql);
			rs = st.executeQuery("select coo.co_latitud, coo.co_longitud, veh.Ve_imagen,veh.ve_id , veh.ve_nombre, coo.co_Id, co_fecha "+
									" from vehiculos veh, coordenadas coo "+
									" where veh.Ve_id=coo.ve_Id "+
									" and veh.Usu_id=( "+
									" select Usu_id "+
									" from usuarios "+
									" where user = '"+_user +
									"' ) "+
									" and co_fecha = (  select max(co1.co_fecha) from coordenadas co1 "+  
											" where  co1.ve_id = veh.Ve_id ) "+
									" group by ve_id;");
			
			System.out.println("funk ocall 2");
			
			
			while(rs.next()){

			System.out.println("--allCarsOnMap(): "+" lng: "+rs.getString(1)+" ltd: "+rs.getString(2));
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
			mymark2= new Gmarker();
		
			
			}
			
			myMapAll.setZoom(5);
			
			binder.loadComponent(myMapAll);
			pcGmaps.appendChild(myMapAll);
			
		}
		catch(Exception e)
		{e.printStackTrace();}

				
		
	}
	
	
	
	public void fillCmbVehCat()
	{
		CategoriaVehiculo cVeh= new CategoriaVehiculo();
		
		int adm=0;
		int usu_id=0;
		Statement st_us=null;
		ResultSet rs_us=null;
		
		ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		categoria="0";
		
		
		try{
			
			st_us=conn.createStatement();
			rs_us = st_us.executeQuery("select per_id,usu_id from usuarios where user = '"+ _user+"'");
			
			while(rs_us.next())
			{
				
				adm = rs_us.getInt("per_id");
				usu_id=rs_us.getInt("usu_id");
			}
			
			
			st=conn.createStatement();
			/*rs=st.executeQuery( "select c.id_categoria_vehiculo, c.descripcion, c.identificador, u.usu_id, u.user user " +
					"	from categoria_vehiculo c,vehiculos v,usuarios u " +
					"	where c.id_categoria_vehiculo = v.ve_categoria " +
					"	and v.usu_id = u.usu_id ");/* +
					"	and u.user = '"+_user +"' " );*/
			
			rs= st.executeQuery("select c.id_categoria_vehiculo, c.descripcion, c.identificador,c.usu_id  " +
					" from categoria_vehiculo c " +
					" where c.estado='A' and id_categoria_vehiculo in " +
					"	( select distinct(ve_categoria) " +
					"	  from vehiculos " +
					"	  where (usu_id = "+usu_id+" or 1="+ adm+")	);");
			
			
			
			while(rs.next())
			{
				System.out.println("cmb: "+String.valueOf( rs.getInt(1))+" - "+rs.getString(2)+" - "+rs.getString(3));
			cVeh.setId_categoria(rs.getInt(1));	
			cVeh.setDescripcion(rs.getString(2));
			cVeh.setIdentificador(rs.getString(3));
			//if(rs.getString("user").equals(_user) || adm==1)
			catVeh.add(cVeh);
			
			cVeh=new CategoriaVehiculo();
			}
			
			cVeh.setId_categoria(0);	
			cVeh.setDescripcion("TODOS");
			cVeh.setIdentificador("TOD");
			catVeh.add(cVeh);
			
			cVeh=new CategoriaVehiculo();
			
			cmbCatVeh.setSelectedText(0, 1, "Escoger Cat.", true);
			System.out.println(cmbCatVeh.getSelectedIndex());
			
			binder.loadComponent(cmbCatVeh);
			binder.loadComponent( lisVehiculo );
			
			
			
		}catch(Exception e ){e.printStackTrace();}
		
		
		
		
	}
	
	public void myMark( double ltd, double lng)
	{
		System.out.println("myMark(ltd,lng): "+"ltd: "+ltd+" lng: "+ lng);
		
		
	}
	
	
	//DEMO
	
	
	public void cargaVehiculos(String identVeh, String img)
	{
		MapaGoogle2Control cnt= new MapaGoogle2Control();
		System.out.println("selected");
		System.out.println("identVeh: "+identVeh+" img: "+img);
		
		if(identVeh.equals("a"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682,-79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-2.1804098646436682);
			mymark.setLng(-79.89120483398437);
						
		}
			
		if(identVeh.equals("b"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-1.1804098646436682,-79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-1.1804098646436682);
			mymark.setLng(-79.89120483398437);
		}
		if(identVeh.equals("c"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-1.6587038068676118,-80.88134765625);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-1.6587038068676118);
			mymark.setLng(-80.88134765625);
			
		}
		if(identVeh.equals("d"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-0.6587038068676118,-80.88134765625);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);
			
			
		}
		if(identVeh.equals("e"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682,-79.89120483398437);
			myMap.setZoom(7);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);
		}
		if(identVeh.equals("f"))
		{
			//cnt.presentaVehiculo(-2.1804098646436682,-79.89120483398437);
			myMap.panTo(-2.1804098646436682,-79.89120483398437);
			myMap.setZoom(2);
			mymark.setIconImage(img);
			mymark.setLat(-0.6587038068676118);
			mymark.setLng(-80.88134765625);
		}
		
		
		
	}
	
	public static void main(String args[])
	{
		ResultSet rs=null;
		Statement st=null;
		Connection conn= ConexionUtil.getConnection();
		
		MapaGoogle2Control map= new MapaGoogle2Control();
		//map.presentaVehiculo();
		
		/*try{
			st=conn.createStatement();
			rs = st.executeQuery("SELECT * FROM vehiculos ");
			
			System.out.println("funk ocall 2");
			
			while(rs.next()){
			
			System.out.println(rs.getString(1));
			}
			
			
		}
		catch(Exception e)
		{e.printStackTrace();}*/
		
		
	}
	
	
}
