/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.JornadaDao;
import dao.JornadaDaoImplement;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Jornada;

/**
 *
 * @author chris
 */
@ManagedBean(name = "jornadaBean")
@RequestScoped
public class JornadaBean {

        
    private List<Jornada> jornadas;
    private Jornada selectedJornada;
    private Jornada createdJornada;
    
    
    /**
     * Creates a new instance of categoriaBean
     */
    public JornadaBean() {
        this.selectedJornada = new Jornada();
        this.createdJornada = new Jornada();
        
        this.jornadas= new ArrayList<Jornada>();
    }

    public List<Jornada> getJornadas() {
        JornadaDao jornadaDao = new JornadaDaoImplement();
        this.jornadas = jornadaDao.mostrarJornadas();
        return jornadas;
    }

    public Jornada getSelectedJornada() {
        return selectedJornada;
    }

    public void setSelectedJornada(Jornada selectedJornada) {
        this.selectedJornada = selectedJornada;
    }

    public Jornada getCreatedJornada() {
        return createdJornada;
    }

    public void setCreatedJornada(Jornada createdJornada) {
        this.createdJornada = createdJornada;
    }
    
    
    
    public void btnCreateJornada(ActionEvent actionEvent){
        JornadaDao jornadaDao = new JornadaDaoImplement();
        String msg;
        if(jornadaDao.insertarJornada(this.createdJornada)){
            msg="Se guardo correctamente la jornada";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            createdJornada = new Jornada();
        }else{
            msg="Error al crear la jornada";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
    
    public void btnUpdateJornada(ActionEvent actionEvent){
        JornadaDao jornadaDao = new JornadaDaoImplement();
        String msg;
        if(jornadaDao.modificarJornada(this.selectedJornada)){
            msg="Se modifico correctamente la jornada";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            selectedJornada = new Jornada();
        }else{
            msg="Error al modificar la jornada";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
     
    
}
