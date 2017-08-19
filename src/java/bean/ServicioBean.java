/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import dao.TrabajoDao;
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
 * @author chris
 */
@ManagedBean(name = "servicioBean")
@SessionScoped
public class ServicioBean implements Serializable {

    private List<Trabajo> trabajos;
    private List<Categoria> categoriasPrincipales;
    private List<Categoria> SubCategorias;
    private Trabajo selectedTrabajo;
    private Trabajo createdTrabajo;
    private Categoria subCategoria;

    /**
     * Creates a new instance of categoriaBean
     */
    public ServicioBean() {
        this.trabajos = new ArrayList<Trabajo>();
        categoriasPrincipales = new ArrayList<Categoria>();
    }

    @PostConstruct
    public void init() {
        //selectedTrabajo = new Trabajo();
        createdTrabajo = new Trabajo();
        /*this.subCategoria = new Categoria("vacia", true);
        createdTrabajo.setCategoria(subCategoria);
        selectedTrabajo.setCategoria(subCategoria);*/
    }

    public List<Trabajo> getTrabajos() {
        TrabajoDao trabajoDao = new TrabajoDaoImplement();
        this.trabajos = trabajoDao.mostrarTrabajos();
        return trabajos;
    }

    public Trabajo getSelectedTrabajo() {
        return selectedTrabajo;
    }

    public void setSelectedTrabajo(Trabajo selectedTrabajo) {
        this.selectedTrabajo = selectedTrabajo;
    }

    public Trabajo getCreatedTrabajo() {
        return createdTrabajo;
    }

    public void setCreatedTrabajo(Trabajo createdTrabajo) {
        this.createdTrabajo = createdTrabajo;
    }

    public List<Categoria> getCategoriasPrincipales() {
        CategoriaDao categoriaDao = new CategoriaDaoImplement();
        this.categoriasPrincipales = categoriaDao.mostrarCategoriasPrincipales();
        return categoriasPrincipales;
    }

    public void setCategoriasPrincipales(List<Categoria> categoriasPrincipales) {
        this.categoriasPrincipales = categoriasPrincipales;
    }

    public void btnCreateCategoria(ActionEvent actionEvent) {

        /*CategoriaDao categoriaDao = new CategoriaDaoImplement();
        String msg;
        if (categoriaDao.insertarCategoria(this.createdCategoria)) {
            msg = "Se guardo correctamente la categoria";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            createdCategoria = new Categoria();
        } else {
            msg = "Error al crear la categoria";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        System.out.println(createdCategoria);*/
    }

    public void btnUpdateCategoria(ActionEvent actionEvent) {
        /*CategoriaDao categoriaDao = new CategoriaDaoImplement();
        String msg;
        if (categoriaDao.modificarCategoria(this.selectedCategoria)) {
            msg = "Se modifico correctamente la categoria";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            selectedCategoria = new Categoria();
        } else {
            msg = "Error al modificar la categoria";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }*/
    }

    public List<Categoria> getSubCategorias() {
        CategoriaDaoImplement cdi = new CategoriaDaoImplement();
        return cdi.mostrarSubCategorias();

    }

    public void setSubCategorias(List<Categoria> SubCategorias) {
        this.SubCategorias = SubCategorias;
    }

    public String getNombreCategoria(Trabajo trabajo) {
        CategoriaDaoImplement cdi = new CategoriaDaoImplement();

        return cdi.buscarbyId(trabajo.getCategoria().getIdCategoria()).getNombre();

    }

    public Categoria MostrarCategoria() {
        if (this.selectedTrabajo != null) {
            if (this.selectedTrabajo.getCategoria() == null) {
                TrabajoDaoImplement tdi = new TrabajoDaoImplement();
                this.selectedTrabajo = tdi.buscarTrabajoById(this.selectedTrabajo.getIdTrabajo());

            }

            return this.selectedTrabajo.getCategoria();
        }
        return null;
    }

}
