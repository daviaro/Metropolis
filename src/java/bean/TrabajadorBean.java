/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.TrabajadorDao;
import dao.TrabajadorDaoImplement;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author andre
 */
@Named(value = "trabajadorBean")
@ViewScoped
public class TrabajadorBean implements Serializable{
    
    private Integer trabajadoresCount;

    /**
     * Creates a new instance of TrabajadorBean
     */
    public TrabajadorBean() {
    }
    
    public Integer getTrabajadoresCount() {
        TrabajadorDao linkDao = new TrabajadorDaoImplement();
        trabajadoresCount = linkDao.countAll();
        return trabajadoresCount;            
    }
    
}
