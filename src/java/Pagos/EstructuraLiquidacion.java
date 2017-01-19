/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pagos;

import dao.ComisionDaoImplement;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;
import model.Comision;
import model.Contrato;
import model.Usuario;

/**
 *
 * @author Jotamarios
 */
public class EstructuraLiquidacion {

    private Set<Contrato> contratos;
    private Usuario empleado;
    private int subtotal;
    private int comision;
    private int total;

    public EstructuraLiquidacion(Set<Contrato> contratos, Usuario empleado) {
        this.contratos = contratos;
        this.empleado = empleado;
    }

    public Set<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    @Override
    public boolean equals(Object contratoComparar) {
        Usuario usuarioComparar = ((EstructuraLiquidacion) contratoComparar).empleado;
        if (usuarioComparar.getIdUsuario() == this.empleado.getIdUsuario()) {
            return true;
        }
        return false;
    }

    public int getSubtotal() {

        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getComision() {
        ComisionDaoImplement cdi = new ComisionDaoImplement();

        double suma = 0;
        
        for (Iterator<Contrato> it = contratos.iterator(); it.hasNext();) {

            Contrato con = it.next();
            Comision comi = cdi.getComisionCotizacion(con.getCotizacion());
            float comision = comi.getValor().floatValue();            
            
            suma += con.getValorTotal() * comision;
        }
        comision = (int) Math.round(suma);
        return (int) comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public int getTotal() {
        int suma = 0;
        for (Iterator<Contrato> it = contratos.iterator(); it.hasNext();) {
            Contrato con = it.next();
            suma += con.getValorTotal();
        }
        this.total = suma;
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
