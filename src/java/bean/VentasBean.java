/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Pagos.EstructuraVentas;
import dao.ContratoDaoImplement;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Jotamarios
 */
@ManagedBean(name = "ventasBean")
@SessionScoped
public class VentasBean {

    /**
     * Creates a new instance of VentasBean
     */
    
    private LineChartModel lcm;
    public VentasBean() {
        iniciarModelo();
    }
   
    public LineChartModel getLcm() {
        return lcm;
    }

    public void setLcm(LineChartModel lcm) {
        this.lcm = lcm;
    }

    private void iniciarModelo() {
        
        lcm = getModelo();
        lcm.setTitle("Linear Chart");
        lcm.setLegendPosition("e");
        
        Axis yAxis = lcm.getAxis(AxisType.Y);
        lcm.setShowPointLabels(true);        
        
        lcm.getAxes().put(AxisType.X, new CategoryAxis("Mes/Año"));
        yAxis = lcm.getAxis(AxisType.Y);
        yAxis.setLabel("Contratos");
        //yAxis.setMin(0);
        //yAxis.setMax(200);;
        
    }
    
    private LineChartModel  getModelo(){
        LineChartModel  model = new LineChartModel ();
        ChartSeries lcs = new ChartSeries();
        ContratoDaoImplement cdi = new ContratoDaoImplement();
        List<EstructuraVentas> lista = cdi.getContratosXmes();
        //List<Object> lista  = cdi.getContratosXmes();
        /*lcs.set(2, 5);
        lcs.set(1, 7);
        lcs.set(3, 7);
        lcs.set(4, 8);*/
        for(int i = 0; i< lista.size();i++){
            lcs.set(Integer.parseInt(lista.get(i).getMes()), lista.get(i).getCantidad());
        }
        model.addSeries(lcs);
        //model.setExtender("ext");
        return model;
    }
    
    
}
