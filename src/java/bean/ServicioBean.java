/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import dao.MedicionTrabajoDaoImplement;
import dao.TrabajoDao;
import dao.TrabajoDaoImplement;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Categoria;
import model.MedicionTrabajo;
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
    private List<MedicionTrabajo> medicionTrabajo;

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
        if (selectedTrabajo != null) {
            try {
                if (selectedTrabajo.getCategoria().getNombre() == null) {
                    CategoriaDaoImplement cdi = new CategoriaDaoImplement();
                    selectedTrabajo.setCategoria(cdi.buscarCategoria(selectedTrabajo));

                }
            } catch (Exception e) {
                CategoriaDaoImplement cdi = new CategoriaDaoImplement();
                selectedTrabajo.setCategoria(cdi.buscarbyId(selectedTrabajo.getCategoria().getIdCategoria()));
                /*CategoriaDaoImplement cdi = new CategoriaDaoImplement();
                selectedTrabajo.setCategoria(cdi.buscarCategoria(selectedTrabajo));*/
            }

        }
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

    public void btnCreateServicio(ActionEvent actionEvent) {

        TrabajoDaoImplement tdi = new TrabajoDaoImplement();
        String msg;
        createdTrabajo.setFechaCreacion(new Date());
        if (tdi.insertarTrabajo(this.createdTrabajo)) {
            msg = "Se guardo correctamente el Servicio";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            createdTrabajo = new Trabajo();
        } else {
            msg = "Error al crear el servicio";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        //System.out.println(createdTrabajo);
    }

    public void btnUpdateServicio(ActionEvent actionEvent) {

        TrabajoDaoImplement tdi = new TrabajoDaoImplement();
        String msg;

        if (tdi.modificarTrabajo(this.selectedTrabajo)) {
            msg = "Se modifico correctamente el trabajo";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            selectedTrabajo = new Trabajo();
        } else {
            msg = "Error al modificar el trabajo";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public List<Categoria> getSubCategorias() {
        CategoriaDaoImplement cdi = new CategoriaDaoImplement();
        this.SubCategorias = cdi.mostrarSubCategorias();
        return this.SubCategorias;

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

    public List<MedicionTrabajo> getMedicionTrabajo() {
        if (medicionTrabajo == null || medicionTrabajo.size() == 0) {
            MedicionTrabajoDaoImplement mtdi = new MedicionTrabajoDaoImplement();
            medicionTrabajo = mtdi.mostrarMedidionTrabjo();
        }
        return medicionTrabajo;
    }

    public void setMedicionTrabajo(List<MedicionTrabajo> medicionTrabajo) {
        this.medicionTrabajo = medicionTrabajo;
    }

}
