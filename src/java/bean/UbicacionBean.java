/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UbicacionDao;
import dao.UbicacionDaoImplement;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import model.Ubicacion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author chris
 */
@ManagedBean(name = "ubicacionBean")
@SessionScoped
public class UbicacionBean implements Serializable{
    
    private Logger logger = LogManager.getLogger(UbicacionBean.class);

    private List<Ubicacion> ubicaciones;
    private List<SelectItem> selectOneItemsUbicacion;
    private Ubicacion selectedUbicacion;
    private Ubicacion createdUbicacion;
    //Foto
    private String pathImages = "";
    private String destination = "/images/";

    @PostConstruct
    public void init() {
        
        logger.info("Ejecuta PostConstruct");
        
        this.selectedUbicacion = new Ubicacion();
        this.createdUbicacion = new Ubicacion();
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getContext();
        this.pathImages = servletContext.getRealPath(this.destination);
        
        logger.info("Se obtiene la ruta donde se van a despositar y leer imagenes {} ",this.pathImages);
    }

    /**
     * Creates a new instance of UbicacionBean
     */
    public UbicacionBean() {
        this.ubicaciones = new ArrayList<Ubicacion>();
    }

    public List<Ubicacion> getUbicaciones() {
        logger.info("Lista de ubicaciones");
        
        UbicacionDao ubicacionDao = new UbicacionDaoImplement();
        this.ubicaciones = ubicacionDao.mostrarUbicaciones();
        return ubicaciones;
    }

    public Ubicacion getSelectedUbicacion() {
        return selectedUbicacion;
    }

    public void setSelectedUbicacion(Ubicacion selectedUbicacion) {
        this.selectedUbicacion = selectedUbicacion;
    }

    public Ubicacion getCreatedUbicacion() {
        return createdUbicacion;
    }

    public void setCreatedUbicacion(Ubicacion createdUbicacion) {
        this.createdUbicacion = createdUbicacion;
    }

    public void btnCreateUbicacion(ActionEvent actionEvent) {
        UbicacionDao ubicacionDao = new UbicacionDaoImplement();
        String msg;
        if (ubicacionDao.insertarUbicacion(this.createdUbicacion)) {
            msg = "Se guardo correctamente la ubicacion";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            createdUbicacion = new Ubicacion();
        } else {
            msg = "Error al crear la ubicacion";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void btnUpdateUbicacion() {
        UbicacionDao ubicacionDao = new UbicacionDaoImplement();
        String msg;
        if (ubicacionDao.modificarUbicacion(this.selectedUbicacion)) {
            msg = "Se modifico correctamente la ubicacion";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            selectedUbicacion = new Ubicacion();
        } else {
            msg = "Error al modificar la ubicacion";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public List<SelectItem> getSelectOneItemsUbicacion() {
        this.selectOneItemsUbicacion = new ArrayList<SelectItem>();
        UbicacionDao ubicaciondao = new UbicacionDaoImplement();
        List<Ubicacion> ubicaciones = ubicaciondao.mostrarUbicaciones();
        for (Ubicacion ubicacion : ubicaciones) {
            SelectItem selectitem = new SelectItem(ubicacion.getIdUbicacion(), ubicacion.getBarrio());
            this.selectOneItemsUbicacion.add(selectitem);
        }
        return selectOneItemsUbicacion;
    }

}
