/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ContratoDao;
import dao.ContratoDaoImplement;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import model.Contrato;
import model.Cotizacion;

/**
 *
 * @author andre
 */
@Named(value = "contratoBean")
@ViewScoped
public class ContratoBean implements Serializable{

    private Contrato contratoUpdate;
    private List<Contrato> contratos;

    /**
     * Creates a new instance of ContratoBean
     */
    public ContratoBean() {
        contratoUpdate = new Contrato();
        contratoUpdate.setCotizacion(new Cotizacion());
    }

    public Contrato getContratoUpdate() {
        return contratoUpdate;
    }

    public void setContratoUpdate(Contrato contratoUpdate) {
        this.contratoUpdate = contratoUpdate;
    }



    public List<Contrato> getContratos() {
        this.contratos = new ArrayList<Contrato>();
        ContratoDao contratoLink = new ContratoDaoImplement();
        this.contratos.clear();
        this.contratos = contratoLink.findVigentes();        
        return this.contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }    

    public void btnUpdateContrato(ActionEvent actionevent) {
        ContratoDao contratoDao = new ContratoDaoImplement();
        String msg;
        if(contratoDao.modificarContrato(contratoUpdate)){
            msg="Se modifico correctamente el contrato";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            contratoUpdate = new Contrato();
        }else{
            msg="Error al modificar el contrato";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    public void irAlPago() throws IOException{
        String link = "https://sandbox.gateway.payulatam.com/ppp-web-gateway/";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(link.trim());
    }
    

}
