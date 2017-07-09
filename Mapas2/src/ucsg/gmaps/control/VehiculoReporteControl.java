package ucsg.gmaps.control;

import org.zkoss.bind.annotation.Command;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Tooltip;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.ReportChartsDao;
import Util.MapUsuarioSistema;


public class VehiculoReporteControl extends SelectorComposer<Window>  {
	@Wire
    Charts chart;
	
	MapUsuarioSistema objUsuarioSistema = null;
	
	public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        
        getUserFromIndex();
        ReportChartsDao rcd= new ReportChartsDao();
        chart.setModel(rcd.obtieneVehiculoDao(objUsuarioSistema.getUsu_id()));
//        chart.setModel(LineBasicData.getCategoryModel());
        
        chart.getXAxis().setMin(0);
        chart.getXAxis().getTitle().setText("Lista de Vehiculos");
        
        Tooltip tooltip = chart.getTooltip();
        tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>"
            + "<td style=\"padding:0\"><b>{point.y:.1f} mm</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        
        chart.getPlotOptions().getColumn().setPointPadding(0.2);
        chart.getPlotOptions().getColumn().setBorderWidth(0);
    }
	
	
	public void getUserFromIndex()
	{
		objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (objUsuarioSistema==null)
			Executions.sendRedirect("DesconectaUsuario.zul");
		
	
	}
	@Command
	public void volver()
	{
		Executions.sendRedirect("Reportes.zul");
		
	}
	
	public void reporteChartPie()
	{
		Window win= (Window) Executions.getCurrent().createComponents("vehiculoReportePie.zul", null,null);
		win.doModal();
		win.setClosable(true);
	}

}
