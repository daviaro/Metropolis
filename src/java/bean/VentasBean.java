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
import org.primefaces.model.chart.CartesianChartModel;
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
    @PostConstruct
    public void init(){
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
        lcm.setTitle("titulo");
        
        
        
        
    }
    
    private LineChartModel  getModelo(){
        LineChartModel  model = new LineChartModel ();
        LineChartSeries lcs = new LineChartSeries();
        ContratoDaoImplement cdi = new ContratoDaoImplement();
        List<EstructuraVentas> lista = cdi.getContratosXmes();
        for(int i = 0; i< lista.size();i++){
            lcs.set(lista.get(i).getMes(), lista.get(i).getCantidad());
        }
        model.addSeries(lcs);
        return model;
    }
    
    
}
