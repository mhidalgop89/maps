package ucsg.gmaps.control;

import java.sql.Date;

import org.zkoss.bind.annotation.Command;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Legend;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.Tooltip;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.ReportChartsDao;
import Util.MapUsuarioSistema;

public class ReporteRecorridoPorFechaControl  extends GenericForwardComposer/*extends SelectorComposer<Window>*/  {

	AnnotateDataBinder binder;
	@Wire
    Charts chart;
	private Window winChartRecorrido;
	private Datebox dtbFechaDesde;
	private Datebox dtbFechaHasta;
	
	MapUsuarioSistema objUsuarioSistema = null;
	
	public void doAfterCompose(Component cmp) throws Exception {
        super.doAfterCompose(cmp);
        cmp.setAttribute("winChartRecorrido",this,true);
        binder = new AnnotateDataBinder(cmp);
        getUserFromIndex();
      
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
	
	
	@SuppressWarnings("deprecation")
	public void reportePorFecha()
	{
		
		String fechaDesde="";
		String fechaHasta="";
		
		  ReportChartsDao rcd= new ReportChartsDao();
		 
//		  Date fechaDesde = new Date(113, 03, 17);
//		  Date fechaHasta = new Date(113, 03, 19);
		  if(dtbFechaDesde==null||dtbFechaHasta==null
				  ||dtbFechaDesde.getValue()==null||dtbFechaHasta.getValue()==null)
		  {
				Messagebox.show("Porfavor llene los datos de fecha","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);
				return;
		  }
		  
		  if(dtbFechaDesde.getValue().after(dtbFechaHasta.getValue()))
		  {
			  Messagebox.show("La fecha hasta debe ser posterior a la fecha desde","Atención!!!",Messagebox.OK,Messagebox.INFORMATION);
				return;
			  
		  }
		  fechaDesde = dtbFechaDesde.getText()+" 00:00:00";
		  fechaHasta = dtbFechaHasta.getText()+" 23:59:59";
		  
          chart.setModel(rcd.obtieneRecorridoVehiculoPorFechaDao(fechaDesde, fechaHasta));

          
//          chart.getTitle().setX(-20);
//          
//          chart.getSubtitle().setX(-20);
//      
//          chart.getYAxis().setTitle("Kilometros");
//          PlotLine plotLine = new PlotLine();
//          plotLine.setValue(0);
//          plotLine.setWidth(1);
//          plotLine.setColor("#808080");
//          chart.getYAxis().addPlotLine(plotLine);
//
//          chart.getTooltip().setValueSuffix("Km");
//
//          Legend legend = chart.getLegend();
//          legend.setLayout("vertical");
//          legend.setAlign("right");
//          legend.setVerticalAlign("middle");
//          legend.setBorderWidth(0);
	        
	        chart.getXAxis().setMin(0);
	        chart.getXAxis().getTitle().setText("Lista de Vehiculos");
	        
	        Tooltip tooltip = chart.getTooltip();
	        tooltip.setHeaderFormat("<span style=\"font-size:10px\">{point.key}</span><table>");
	        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>"
	            + "<td style=\"padding:0\"><b>{point.y:.1f} Km</b></td></tr>");
	        tooltip.setFooterFormat("</table>");
	        tooltip.setShared(true);
	        tooltip.setUseHTML(true);
	        
	        chart.getPlotOptions().getColumn().setPointPadding(0.2);
	        chart.getPlotOptions().getColumn().setBorderWidth(0);
	        
	        binder.loadComponent(chart);
		
	}


	public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}


	public Charts getChart() {
		return chart;
	}


	public void setChart(Charts chart) {
		this.chart = chart;
	}


	public Window getWinChartRecorrido() {
		return winChartRecorrido;
	}


	public void setWinChartRecorrido(Window winChartRecorrido) {
		this.winChartRecorrido = winChartRecorrido;
	}


	public MapUsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(MapUsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public Datebox getDtbFechaDesde() {
		return dtbFechaDesde;
	}


	public void setDtbFechaDesde(Datebox dtbFechaDesde) {
		this.dtbFechaDesde = dtbFechaDesde;
	}


	public Datebox getDtbFechaHasta() {
		return dtbFechaHasta;
	}


	public void setDtbFechaHasta(Datebox dtbFechaHasta) {
		this.dtbFechaHasta = dtbFechaHasta;
	}

	
	
}
