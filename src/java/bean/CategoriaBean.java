/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
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

/**
 *
 * @author chris
 */
@ManagedBean(name = "categoriaBean")
@SessionScoped
public class CategoriaBean implements Serializable {

    private List<Categoria> categorias;
    private List<Categoria> categoriasPrincipales;
    private Categoria selectedCategoria;
    private Categoria createdCategoria;
    private Categoria subCategoria;

    /**
     * Creates a new instance of categoriaBean
     */
    public CategoriaBean() {
        this.categorias = new ArrayList<Categoria>();
        categoriasPrincipales = new ArrayList<Categoria>();
    }

    @PostConstruct
    public void init() {
        selectedCategoria = new Categoria();
        createdCategoria = new Categoria();
        this.subCategoria = new Categoria("vacia", true);
        createdCategoria.setCategoria(subCategoria);
        selectedCategoria.setCategoria(subCategoria);
    }

    public List<Categoria> getCategorias() {
        CategoriaDao categoriaDao = new CategoriaDaoImplement();
        this.categorias = categoriaDao.mostrarCategorias();
        return categorias;
    }

    public Categoria getSelectedCategoria() {
        return selectedCategoria;
    }

    public void setSelectedCategoria(Categoria selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
    }

    public Categoria getCreatedCategoria() {
        return createdCategoria;
    }

    public void setCreatedCategoria(Categoria createdCategoria) {
        this.createdCategoria = createdCategoria;
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

        CategoriaDao categoriaDao = new CategoriaDaoImplement();
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

        System.out.println(createdCategoria);

    }

    public void btnUpdateCategoria(ActionEvent actionEvent) {
        CategoriaDao categoriaDao = new CategoriaDaoImplement();
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
        }
    }

}
