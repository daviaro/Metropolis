/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import dao.TrabajoDaoImplement;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Categoria;
import model.Trabajo;

/**
 *
 * @author Jotamarios
 */
@ManagedBean(name = "trabajoBean")
@SessionScoped
public class TrabajoBean implements Serializable {

    /**
     * Creates a new instance of TrabajoBean
     */
    
    private List<Trabajo> trabajos;
    private List<Categoria> categoriasTrabajo;
    private Trabajo trabajoSelected;
    private Trabajo trabajoCrear;
    
    public TrabajoBean() {
        trabajos =  new ArrayList<>();
    }

    public List<Trabajo> getTrabajos() {
        
        TrabajoDaoImplement tdi = new TrabajoDaoImplement();
        this.trabajos=tdi.mostrarTrabajos();
        
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public Trabajo getTrabajoSelected() {
        return trabajoSelected;
    }

    public void setTrabajoSelected(Trabajo trabajoSelected) {
        this.trabajoSelected = trabajoSelected;
    }
    public String getNombreCategoria(Trabajo trabajo){
        CategoriaDaoImplement cdi = new CategoriaDaoImplement();
               
        return cdi.buscarbyId(trabajo.getCategoria().getIdCategoria()).getNombre();
        
    }

    public Trabajo getTrabajoCrear() {
        return trabajoCrear;
    }

    public void setTrabajoCrear(Trabajo trabajoCrear) {
        this.trabajoCrear = trabajoCrear;
    }

    public List<Categoria> getCategoriasTrabajo() {
        return categoriasTrabajo;
    }

    public void setCategoriasTrabajo(List<Categoria> categoriasTrabajo) {
        this.categoriasTrabajo = categoriasTrabajo;
    }
    
    public void btnUpdateTrabajo(ActionEvent actionEvent) {
       
        TrabajoDaoImplement tdi = new TrabajoDaoImplement();
        String msg;
        
        if (tdi.modificarTrabajo(this.trabajoSelected)) {
            msg = "Se modifico correctamente el trabajo";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            trabajoSelected = new Trabajo();
        } else {
            msg = "Error al modificar el trabajo";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
}
