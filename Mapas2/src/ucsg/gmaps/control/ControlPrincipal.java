package ucsg.gmaps.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.West;

import Util.MapUsuarioSistema;
import Util.Perfiles;
import Util.ReadPropertiesUtil;
import ucsg.gmaps.dao.PerfilesDao;
import ucsg.gmaps.dao.RelPerfilPermisoDAO;
import ConexionUtil.ConexionUtil;



public class ControlPrincipal  extends GenericForwardComposer{

	//private Iframe frmPrincipal;
	private Include frmPrincipal;
	Label labelUser;
	//private String _user;
	private West westPrincipal;
	private MapUsuarioSistema objUsuarioSistema = null;
	Fisheye febPass;
	Fisheye febInicio;
	Fisheye febVehiculos;
	Fisheye febCategorias;
	Fisheye febCercas;
	Fisheye febGrupos;
	Fisheye febUsuarios;
	Fisheye febPerfiles;
	Fisheye febReportes;
	Fisheye febDesconectar;
	Fisheye febSrvRutas;
	
	AnnotateDataBinder binder;
	
	
	
	public void cargaFrameDinamico(String ruta,String prm_valor) throws InterruptedException
	{
		Perfiles perfil = new Perfiles();
		
		//Messagebox.show("Estas Seguro de Ver la Opción","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
		Executions.getCurrent().getSession().setAttribute("_userControlPrincipal",objUsuarioSistema.getUser());
		PerfilesDao per= new PerfilesDao(); 
				
		perfil = per.buscarPerfPorUsuario(objUsuarioSistema.getUser());
		
		RelPerfilPermisoDAO rpp= new RelPerfilPermisoDAO();

		System.out.println("prm_valor: "+prm_valor+ "--"+ perfil.getPerId());
		if(rpp.buscarPermisosDePerfilporIdPermiso(perfil, prm_valor))
				{
					//westPrincipal.setOpen(false);// 777
					//Executions.getCurrent().sendRedirect("ServicioRutas.zul", "blank");
			//if(prm_valor.equals("11"))
			//	Messagebox.show("Acepto terminos y condiciones","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);
			
				frmPrincipal.setSrc(ruta);
				
				}
		else
			{
			westPrincipal.setOpen(true);
//			Messagebox.show("No tiene permisos sobre la opcion","Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			Messagebox.show(ReadPropertiesUtil.obtenerProperty("NoTienePermiso", objUsuarioSistema.getIdioma()),"Atención!!!",Messagebox.OK|Messagebox.CANCEL,Messagebox.INFORMATION);
			}
	}
	
	
public void doAfterCompose(Component cmp){  	
    	
    	try
    	{
    		super.doAfterCompose(cmp);
    		cmp.setAttribute("winPrincipal",this,true);
    		//System.out.println("doAfterCompose cmpasfasfasfasfasfasfsafasasfsafasas");
    		//Ejecución de Ajax
    		
    		getUserFromIndex();
    		if (objUsuarioSistema==null)
			{
					desconectar();
					return;
			}
    		
//    		labelUser.setValue("Bienvenido: "+objUsuarioSistema.getUser());
//    		febPass.setLabel("Bienvenido: "+objUsuarioSistema.getUser());
    		labelUser.setValue(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Bienvenido", objUsuarioSistema.getIdioma())+objUsuarioSistema.getUser());
    		febPass.setLabel(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Bienvenido", objUsuarioSistema.getIdioma())+objUsuarioSistema.getUser());
    		febPass.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.CambiarPass", objUsuarioSistema.getIdioma()));
    		febInicio.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Inicio", objUsuarioSistema.getIdioma()));
    		febVehiculos.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Vehiculos", objUsuarioSistema.getIdioma()));
    		febCategorias.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Categorias", objUsuarioSistema.getIdioma()));
    		febCercas.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Cercas", objUsuarioSistema.getIdioma()));
    		febGrupos.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Grupos", objUsuarioSistema.getIdioma()));
    		febUsuarios.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Usuarios", objUsuarioSistema.getIdioma()));
    		febPerfiles.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Perfiles", objUsuarioSistema.getIdioma()));
    		febReportes.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Reportes", objUsuarioSistema.getIdioma()));
    		febSrvRutas.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.ServicioRutas", objUsuarioSistema.getIdioma()));
    		febDesconectar.setTooltiptext(ReadPropertiesUtil.obtenerProperty("control.ControlPrincipal.Desconectar", objUsuarioSistema.getIdioma()));
    		cargaFrameDinamico("MapaGoogle3.zul","5");
    		
    		binder = new AnnotateDataBinder(cmp);

    		//binder.loadComponent(CmbItm);
    		binder.loadAll();
    	}
    	catch(Exception e){
    		System.out.println("doAfterCompose Exception");
    		e.printStackTrace();
    	}
    }

	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (objUsuarioSistema==null)
			desconectar();
		
		/*_user=(String)Executions.getCurrent().getSession().getAttribute("_user");
		if (_user==null)
			Executions.sendRedirect("DesconectaUsuario.zul");
			//desconectar();
			
			
		System.out.println("_user: "+objUsuarioSistema.getUser());*/
		
	}
	public void desconectar()
	{
		
		
		Executions.getCurrent().getSession().removeAttribute("usuario");
		
//	_user=null;
//	Executions.getCurrent().getSession().setAttribute("_user",_user);
//	Executions.getCurrent().getSession().setAttribute("_desconectar","S");
	Executions.sendRedirect("/Login/index.html");
		
	}
	
	

	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Label getLabelUser() {
		return labelUser;
	}

	public void setLabelUser(Label labelUser) {
		this.labelUser = labelUser;
	}

	public Fisheye getFebPass() {
		return febPass;
	}

	public void setFebPass(Fisheye febPass) {
		this.febPass = febPass;
	}

	public West getWestPrincipal() {
		return westPrincipal;
	}

	public void setWestPrincipal(West westPrincipal) {
		this.westPrincipal = westPrincipal;
	}




	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	/*public Iframe getFrmPrincipal() {
		return frmPrincipal;
	}

	public void setFrmPrincipal(Iframe frmPrincipal) {
		this.frmPrincipal = frmPrincipal;
	}*/


	public Fisheye getFebInicio() {
		return febInicio;
	}


	public Include getFrmPrincipal() {
		return frmPrincipal;
	}


	public void setFrmPrincipal(Include frmPrincipal) {
		this.frmPrincipal = frmPrincipal;
	}


	public void setFebInicio(Fisheye febInicio) {
		this.febInicio = febInicio;
	}


	public Fisheye getFebVehiculos() {
		return febVehiculos;
	}


	public void setFebVehiculos(Fisheye febVehiculos) {
		this.febVehiculos = febVehiculos;
	}


	public Fisheye getFebCategorias() {
		return febCategorias;
	}


	public void setFebCategorias(Fisheye febCategorias) {
		this.febCategorias = febCategorias;
	}


	public Fisheye getFebCercas() {
		return febCercas;
	}


	public void setFebCercas(Fisheye febCercas) {
		this.febCercas = febCercas;
	}


	public Fisheye getFebGrupos() {
		return febGrupos;
	}


	public void setFebGrupos(Fisheye febGrupos) {
		this.febGrupos = febGrupos;
	}


	public Fisheye getFebUsuarios() {
		return febUsuarios;
	}


	public void setFebUsuarios(Fisheye febUsuarios) {
		this.febUsuarios = febUsuarios;
	}


	public Fisheye getFebPerfiles() {
		return febPerfiles;
	}


	public void setFebPerfiles(Fisheye febPerfiles) {
		this.febPerfiles = febPerfiles;
	}


	public Fisheye getFebReportes() {
		return febReportes;
	}


	public void setFebReportes(Fisheye febReportes) {
		this.febReportes = febReportes;
	}


	public Fisheye getFebDesconectar() {
		return febDesconectar;
	}


	public void setFebDesconectar(Fisheye febDesconectar) {
		this.febDesconectar = febDesconectar;
	}


	public Fisheye getFebSrvRutas() {
		return febSrvRutas;
	}


	public void setFebSrvRutas(Fisheye febSrvRutas) {
		this.febSrvRutas = febSrvRutas;
	}
	
	
	
	
}
