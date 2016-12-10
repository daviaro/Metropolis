/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ContratoDao;
import dao.ContratoDaoImplement;
import dao.UbicacionDao;
import dao.UbicacionDaoImplement;
import dao.UsuarioDao;
import dao.UsuarioDaoImplement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import model.Contrato;
import model.Ubicacion;
import model.Usuario;
import org.primefaces.model.DualListModel;

/**
 *
 * @author andre
 */
@ManagedBean(name = "liquidacionBean")
@ViewScoped
public class LiquidacionBean implements Serializable {

    private String empleado;
    private Usuario empleadoO;
    private List<String> selectedContratos;
    private List<String> contratos;
    private List<String> selectedOptions;

    private DualListModel<Contrato> contratosDL;
    private List<Contrato> contratosAliquidar;
    private List<Contrato> contratosPendientes;

    /**
     * Creates a new instance of LiquidacionBean
     */
    @PostConstruct
    public void init() {
        empleadoO = new Usuario();
        contratosPendientes = getContratosPendientes();
        contratosAliquidar = new ArrayList<Contrato>();
        contratosDL = new DualListModel<Contrato>(contratosPendientes,contratosAliquidar);
    }

    public LiquidacionBean() {

    }

    public List<String> buscarEmpleado(String query) {
        UsuarioDao uLink = new UsuarioDaoImplement();
        List<Usuario> usuarios = new LinkedList<>();
        usuarios = uLink.buscarUsuariobyNombre(query);

        List<String> results = new ArrayList<String>();
        for (int i = 0; i < usuarios.size(); i++) {
            results.add(usuarios.get(i).getNombres() + " " + usuarios.get(i).getApellidos() + " " + usuarios.get(i).getDocumentoIdentidad());
        }

        return results;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public Usuario getEmpleadoO() {
        return empleadoO;
    }

    public void setEmpleadoO(Usuario empleadoO) {
        this.empleadoO = empleadoO;
    }

    public List<String> getSelectedContratos() {
        return selectedContratos;
    }

    public void setSelectedContratos(List<String> selectedContratos) {
        this.selectedContratos = selectedContratos;
    }

    public List<String> getContratos() {
        ContratoDao contratoLink = new ContratoDaoImplement();
        List<Contrato> contratosO = contratoLink.findAllContratos(empleadoO);
        for (int i = 0; i < contratosO.size(); i++) {
            contratos.add(contratosO.get(i).getIdContrato() + " / " + contratosO.get(i).getValorTotal());
        }
        return contratos;
    }

    public void setContratos(List<String> contratos) {
        this.contratos = contratos;
    }

    public void verContratos() {
        String[] dividido = empleado.split(" ");
        String cedula = dividido[dividido.length - 1];
        UsuarioDao uLink = new UsuarioDaoImplement();
        empleadoO = uLink.buscarUsuariobyCedula(cedula);

    }

    public void mostrarContratos() {
        System.out.println("mostrarContratos: " + selectedContratos.size());
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public DualListModel<Contrato> getContratosDL() {
        if(contratosDL == null){
            contratosDL = new DualListModel<>(contratosPendientes,contratosAliquidar);
        }
        return contratosDL;
    }

    public void setContratosDL(DualListModel<Contrato> contratosDL) {
        this.contratosDL = contratosDL;
    }

    public List<Contrato> getContratosAliquidar() {
        return contratosAliquidar;
    }

    public void setContratosAliquidar(List<Contrato> contratosAliquidar) {
        this.contratosAliquidar = contratosAliquidar;
    }

    public List<Contrato> getContratosPendientes() {
        if (contratosDL == null) {
            ContratoDaoImplement cd = new ContratoDaoImplement();
            contratosPendientes = cd.findVigentes();
        }
        return contratosPendientes;
    }

    public void setContratosPendientes(List<Contrato> contratosPendientes) {
        this.contratosPendientes = contratosPendientes;
    }

}
