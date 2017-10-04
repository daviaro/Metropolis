/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jotamarios
 */
@ManagedBean(name = "tryingbean")
@SessionScoped
public class tryingbean {

    /**
     * Creates a new instance of tryingbean
     */
    private List<String> valor;
    public tryingbean() {
        
        valor = new ArrayList<>();
        valor.add("UNO");
        valor.add("DOS");
        valor.add("TRES");
        valor.add("CUATRO");
        valor.add("CINCO");
    }

    public List<String> getValor() {
        return valor;
    }

    public void setValor(List<String> valor) {
        this.valor = valor;
    }

   
    
}
