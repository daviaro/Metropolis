/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import dao.EtiquetaDao;
import dao.EtiquetaDaoImplement;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import model.Categoria;
import model.Etiqueta;

/**
 *
 * @author andre
 */
@Named(value = "etiquetaBean")
@ViewScoped
public class EtiquetaBean implements Serializable{
    private List<Etiqueta> etiquetas;
    private List<Categoria> categorias;
    private Etiqueta selectedEtiqueta;
    private Etiqueta createEtiqueta;
    private Categoria somCategoria;

    /**
     * Creates a new instance of EtiquetaBean
     */
    public EtiquetaBean() {
        this.selectedEtiqueta = new Etiqueta();
        this.createEtiqueta = new Etiqueta();
        this.somCategoria = new Categoria();
        
    }

    public List<Etiqueta> getEtiquetas() {
        this.etiquetas = new ArrayList<Etiqueta>();
        EtiquetaDao etiquetadao = new EtiquetaDaoImplement();
        this.etiquetas.clear();
        etiquetas = etiquetadao.findAll();
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Etiqueta getSelectedEtiqueta() {
        return selectedEtiqueta;
    }

    public void setSelectedEtiqueta(Etiqueta selectedEtiqueta) {
        this.selectedEtiqueta = selectedEtiqueta;
    }

    public Etiqueta getCreateEtiqueta() {
        return createEtiqueta;
    }

    public void setCreateEtiqueta(Etiqueta createEtiqueta) {
        this.createEtiqueta = createEtiqueta;
    }

    public Categoria getSomCategoria() {
        return somCategoria;
    }

    public void setSomCategoria(Categoria somCategoria) {
        this.somCategoria = somCategoria;
    }

    public List<Categoria> getCategorias() {
        CategoriaDao catlink = new CategoriaDaoImplement();
        this.categorias = catlink.mostrarSubCategorias();
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    
    
       
    public void btnUpdateEtiqueta(ActionEvent actionEvent){
        Etiqueta debeti = this.selectedEtiqueta;
        
        EtiquetaDao etiquetausuariodao = new EtiquetaDaoImplement();
        String msg;
        if (etiquetausuariodao.modificarEtiqueta(this.selectedEtiqueta)) {
            msg = "Se modifico correctamente la etiqueta";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excelente", msg));
        } else {
            msg = "Error al modificar la etiqueta";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
        }
    }
    
    public void btnCreateEtiqueta(ActionEvent actionEvent){
        EtiquetaDao etiquetadao = new EtiquetaDaoImplement();
        String msg;
        if (etiquetadao.insertarEtiqueta(this.createEtiqueta)) {
            msg = "Se guardo correctamente la etiqueta";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.createEtiqueta = new Etiqueta();
        } else {
            msg = "Error al crear la etiqueta";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.createEtiqueta = new Etiqueta();
        }
    }
    
    
    
}
