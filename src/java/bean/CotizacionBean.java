/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ContratoDao;
import dao.ContratoDaoImplement;
import dao.CotizacionDao;
import dao.CotizacionDaoImplement;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Contrato;
import model.Cotizacion;
import model.Oferta;
import model.Usuario;

/**
 *
 * @author andre
 */
@ManagedBean(name = "cotizacionBean")
@SessionScoped
public class CotizacionBean implements Serializable {

    private List<Cotizacion> cotizacionesPendientes;
    private List<Cotizacion> cotizacionesContratadas;
    private List<Cotizacion> cotizacionesRealizadas;
    private List<Contrato> trabajosContratados;
    private List<Cotizacion> cotizacionesAceptadas;
    private List<Cotizacion> cotizacionesContraofertas;
    
    private Usuario usuarioRegistrado;
    private Cotizacion cotizacion;
    private Contrato contrato;
    private Integer ratingTrabajo;
    
    /**
     * Creates a new instance of CotizacionBean
     */
    public CotizacionBean() {
        this.cotizacionesPendientes = new ArrayList<>();
        this.cotizacionesContratadas = new ArrayList<>();
        this.cotizacionesRealizadas = new ArrayList<>();
        this.trabajosContratados = new ArrayList<>();
        this.cotizacionesAceptadas = new ArrayList<>();
        this.cotizacionesContraofertas = new ArrayList<>();
        this.cotizacion = new Cotizacion();
    }

    public List<Cotizacion> getCotizacionesPendientes() {
        this.cotizacionesPendientes = new ArrayList<>();

        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        CotizacionDao cotizaciondao = new CotizacionDaoImplement();
        for (Iterator iterator1 = usuarioRegistrado.getOfertas().iterator(); iterator1.hasNext();) {
            Oferta oferta = (Oferta) iterator1.next();
            List<Cotizacion> cotizacionesTemp = cotizaciondao.findAllbyOfertaPendiente(oferta);
            if (!cotizacionesTemp.isEmpty()) {
                this.cotizacionesPendientes.addAll(cotizacionesTemp);

            }
        }
        return this.cotizacionesPendientes;
    }
    
    public void ActualizarCalificacion(){
        ContratoDaoImplement cdi = new ContratoDaoImplement();
        contrato.setCalificacion(ratingTrabajo);
        cdi.modificarContrato(contrato);
    }
    
    public void setCotizacionesPendientes(List<Cotizacion> cotizacionesPendientes) {
        this.cotizacionesPendientes = cotizacionesPendientes;
    }

    public List<Cotizacion> getCotizacionesContratadas() {
        this.cotizacionesContratadas = new ArrayList<>();
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        CotizacionDao cotizaciondao = new CotizacionDaoImplement();
        for (Iterator iterator1 = usuarioRegistrado.getOfertas().iterator(); iterator1.hasNext();) {
            Oferta oferta = (Oferta) iterator1.next();
            List<Cotizacion> cotizacionesTemp = cotizaciondao.findAllbyOfertaContratada(oferta);
            if (!cotizacionesTemp.isEmpty()) {
                this.cotizacionesContratadas.addAll(cotizacionesTemp);

            }
        }
        return this.cotizacionesContratadas;
    }

    public void setCotizacionesContratadas(List<Cotizacion> cotizacionesContratadas) {
        this.cotizacionesContratadas = cotizacionesContratadas;
    }

    public List<Cotizacion> getCotizacionesRealizadas() {
        this.cotizacionesRealizadas = new ArrayList<>();
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        CotizacionDao cotizaciondao = new CotizacionDaoImplement();
        this.cotizacionesRealizadas = cotizaciondao.findAllbyCotizacionesRealizadas(usuarioRegistrado);

        return this.cotizacionesRealizadas;
    }

    public void setCotizacionesRealizadas(List<Cotizacion> cotizacionesRealizadas) {
        this.cotizacionesRealizadas = cotizacionesRealizadas;
    }

    public List<Contrato> getTrabajosContratados() {
        this.trabajosContratados = new ArrayList<>();
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        ContratoDao contratoDao = new ContratoDaoImplement();
        this.trabajosContratados = contratoDao.findAllContratadoByUsuario(usuarioRegistrado);

        return this.trabajosContratados;
    }

    public void setTrabajosContratados(List<Contrato> trabajosContratados) {
        this.trabajosContratados = trabajosContratados;
    }

    public List<Cotizacion> getCotizacionesAceptadas() {
        this.cotizacionesAceptadas = new ArrayList<>();
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        CotizacionDao cotizaciondao = new CotizacionDaoImplement();
        this.cotizacionesAceptadas = cotizaciondao.findAllbyCotizacionesAceptadas(usuarioRegistrado);

        return this.cotizacionesAceptadas;
    }

    public void setCotizacionesAceptadas(List<Cotizacion> cotizacionesAceptadas) {
        this.cotizacionesAceptadas = cotizacionesAceptadas;
    }

    public List<Cotizacion> getCotizacionesContraofertas() {
        this.cotizacionesContraofertas = new ArrayList<>();
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        // Fin buscarUsuario

        CotizacionDao cotizaciondao = new CotizacionDaoImplement();
        this.cotizacionesContraofertas = cotizaciondao.findAllbyCotizacionesContraofertadas(usuarioRegistrado);

        return this.cotizacionesContraofertas;
    }

    public void setCotizacionesContraofertas(List<Cotizacion> cotizacionesContraofertas) {
        this.cotizacionesContraofertas = cotizacionesContraofertas;
    }
    
    
    
    

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public void btnAceptarCotizacion(ActionEvent actionEvent) {

        CotizacionDao cotizacionDao = new CotizacionDaoImplement();
        String msg;
        this.cotizacion.setRespuesta(true);
        this.cotizacion.setFechaRespuesta(new Date());
        //Crear Contrato para cotizacion
        if (cotizacionDao.actualizarCotizacion(this.cotizacion)) {

            msg = "Se modifico correctamente la cotizacion";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", msg));
            setAll();

        } else {
            this.cotizacion.setRespuesta(false);
            msg = "Error al modificar la cotizacion";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", msg));
        }
    }
    
     public void btnAceptarContraoferta(ActionEvent actionEvent) {

        CotizacionDao cotizacionDao = new CotizacionDaoImplement();
        String msg;
        this.cotizacion.setRespuesta(true);
        this.cotizacion.setFechaRespuesta(new Date());
         System.out.println("================================");
         System.out.println("CONTRAOFERTA:");
         System.out.println("Valor contra: "+this.cotizacion.getValorContrapropuesta());
         System.out.println("Fecha contra: "+this.cotizacion.getFechaContraPropuestaRespuesta());
        //Crear Contrato para cotizacion
        if (cotizacionDao.actualizarCotizacion(this.cotizacion)) {

            msg = "Se modifico correctamente la cotizacion";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", msg));
            setAll();

        } else {
            this.cotizacion.setRespuesta(false);
            msg = "Error al modificar la cotizacion";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", msg));
        }
    }

    

    
    /**
     * Etc: Setear las listas
     */
    public void setAll() {
        this.cotizacion = new Cotizacion();
        this.cotizacionesPendientes = new ArrayList<>();
        this.cotizacionesContratadas = new ArrayList<>();
        this.trabajosContratados = new ArrayList<>();
        this.cotizacionesRealizadas = new ArrayList<>();
        this.cotizacionesAceptadas = new ArrayList<>();
        this.cotizacionesContraofertas = new ArrayList<>();
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Integer getRatingTrabajo() {
        return ratingTrabajo;
    }

    public void setRatingTrabajo(Integer ratingTrabajo) {
        this.ratingTrabajo = ratingTrabajo;
    }

}
