package ucsg.gmaps.control;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.chart.Chart;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.plotOptions.PieDataLabels;
import org.zkoss.chart.plotOptions.PiePlotOptions;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import ucsg.gmaps.dao.DatosDao;
import Util.MapUsuarioSistema;
import Util.Vehiculos;

public class VehiculoReportePieControl extends SelectorComposer<Window>  {

	@Wire
    Charts chart;
	MapUsuarioSistema objUsuarioSistema = null;
	private List<Vehiculos> veh= new ArrayList<Vehiculos>();
	
	 public void doAfterCompose(Window comp) throws Exception {
	        super.doAfterCompose(comp);
	        getUserFromIndex();
	        Series series = chart.getSeries();
	        series.setType("pie");
	        series.setName("Porcentaje de uso");  
	        
	        DatosDao dat = new DatosDao();
	        int per_id=0;
			int usu_id=0;
	        per_id = objUsuarioSistema.getPer_id(); 
			usu_id = objUsuarioSistema.getUsu_id();
			veh = dat.obtieneVehiculoDao(usu_id);
			double totalRecorrido=0;
			for(int i=0;i<veh.size();i++)
			{
				totalRecorrido=totalRecorrido+veh.get(i).getRecorrido();
			}
			
			for(int i=0;i<veh.size();i++)
			{
				series.addPoint(new Point(veh.get(i).getNombre(), ((veh.get(i).getRecorrido()*100)/totalRecorrido)  ));
			}
			Chart chartOptional = chart.getChart();
	        chartOptional.setBackgroundColor("");
	        chartOptional.setPlotBorderWidth(0);
	        chartOptional.setPlotShadow(false);
	        
	        chart.getTooltip().setPointFormat(
	            "{series.name}: <b>{point.percentage:.1f}%</b>");
	        
	        PiePlotOptions plotOptions = chart.getPlotOptions().getPie();
	        
	        plotOptions.setAllowPointSelect(true);
	        plotOptions.setCursor("pointer");
	        PieDataLabels dataLabels = (PieDataLabels)plotOptions.getDataLabels();
	        dataLabels.setEnabled(true);
	        dataLabels.setColor("#000000");
	        dataLabels.setConnectorColor("#000000");
	        dataLabels.setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
	 
	 
	 }
	 public void getUserFromIndex()
		{
			objUsuarioSistema =(MapUsuarioSistema)Executions.getCurrent().getSession().getAttribute("usuario");
			
			if (objUsuarioSistema==null)
				Executions.sendRedirect("DesconectaUsuario.zul");
			
		
		}
}
