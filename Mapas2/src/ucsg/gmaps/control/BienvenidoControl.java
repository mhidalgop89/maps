package ucsg.gmaps.control;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;

import Util.MapUsuarioSistema;

public class BienvenidoControl extends GenericForwardComposer{

	AnnotateDataBinder binder=null;
	MapUsuarioSistema objUsuarioSistema = null;
	private Label lblWelcome;
	
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
			lblWelcome.setValue("Welcome "+objUsuarioSistema.getUser());
			binder.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		public void getUserFromIndex()
		{
			
			objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
			
		}
		
		public void desconectar()
		{

			Executions.getCurrent().getSession().removeAttribute("usuario");
			Executions.sendRedirect("/index2.zul");
			
		}
		public AnnotateDataBinder getBinder() {
			return binder;
		}
		public void setBinder(AnnotateDataBinder binder) {
			this.binder = binder;
		}
		public MapUsuarioSistema getObjUsuarioSistema() {
			return objUsuarioSistema;
		}
		public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
			this.objUsuarioSistema = objUsuarioSistema;
		}
		public Label getLblWelcome() {
			return lblWelcome;
		}
		public void setLblWelcome(Label lblWelcome) {
			this.lblWelcome = lblWelcome;
		}
}
