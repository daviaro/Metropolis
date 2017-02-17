/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pagos;

/**
 *
 * @author Jotamarios
 */
public class EstructuraVentas {
    
    private String mes;
    private int cantidad;

    public EstructuraVentas(String mes, int cantidad) {
        this.mes = mes;
        this.cantidad = cantidad;
    }

    
    
    
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
