/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Pagos.EstructuraLiquidacion;
import converters.CategoriaConverter;
import dao.ComisionDaoImplement;
import dao.ContratoDao;
import dao.ContratoDaoImplement;
import dao.LiquidacionDaoImplement;
import dao.UbicacionDao;
import dao.UbicacionDaoImplement;
import dao.UsuarioDao;
import dao.UsuarioDaoImplement;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import model.Contrato;
import model.Liquidacion;
import model.Ubicacion;
import model.Usuario;
import org.primefaces.model.DualListModel;

/**
 *
 * @author andre
 */
@ManagedBean(name = "liquidacionBean")
@SessionScoped
public class LiquidacionBean implements Serializable {

    private String empleado;
    private Usuario empleadoO;
    private List<String> selectedContratos;
    private List<String> contratos;
    private List<String> selectedOptions;

    private List<Contrato> contratosAliquidar;
    private List<Contrato> contratosPendientes;
    private List<Liquidacion> liquidaciones;

    converters.CategoriaConverter cvn = new CategoriaConverter();

    /**
     * Creates a new instance of LiquidacionBean
     */
    @PostConstruct
    public void init() {
        empleadoO = new Usuario();
        contratosPendientes = getContratosPendientes();
        contratosAliquidar = new ArrayList<Contrato>();
        liquidaciones = new ArrayList<>();

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

    public List<Contrato> getContratosAliquidar() {
        return contratosAliquidar;
    }

    public void setContratosAliquidar(List<Contrato> contratosAliquidar) {
        this.contratosAliquidar = contratosAliquidar;
    }

    public List<Contrato> getContratosPendientes() {
        if (contratosPendientes == null) {
            ContratoDaoImplement cd = new ContratoDaoImplement();
            contratosPendientes = cd.findVigentes();

        }
        return contratosPendientes;
    }

    public void liquidar() {

        //CREAR OBJETO LIQUIDACION E INSERTAR ID EN CONTRATO
        addMessage("Liquidación finalizada", "Terminado correctamente. Total contratos:" + contratosAliquidar.size());
        Usuario usr = null;
        try {
            LoginBean lb = new LoginBean();
            usr = lb.getUsuarioLogeado();
        } catch (Exception e) {
        }
        if (usr != null) {
            List<EstructuraLiquidacion> liquidaciones = new ArrayList<>();
            for (int i = 0; i < contratosAliquidar.size(); i++) {
                Set<Contrato> pivote = new HashSet<Contrato>();
                pivote.add(contratosAliquidar.get(i));
                EstructuraLiquidacion comparar = new EstructuraLiquidacion(pivote, contratosAliquidar.get(i).getCotizacion().getOferta().getUsuario());
                if (liquidaciones.contains(comparar)) {
                    liquidaciones = agregarContrato(liquidaciones, contratosAliquidar.get(i));
                } else {
                    Set<Contrato> contratosNew = new HashSet<>();
                    contratosNew.add(contratosAliquidar.get(i));
                    liquidaciones.add(new EstructuraLiquidacion(contratosNew, contratosAliquidar.get(i).getCotizacion().getOferta().getUsuario()));
                }
            }
            LiquidacionDaoImplement ldi = new LiquidacionDaoImplement();
            ContratoDaoImplement cdi = new ContratoDaoImplement();
            for (int i = 0; i < liquidaciones.size(); i++) {
                EstructuraLiquidacion strliq = liquidaciones.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                String date = sdf.format(new Date());
                Date fecha = new Date(date);
                int comision = strliq.getComision();
                int total = strliq.getTotal();
                int subtotal = total - comision;
                Liquidacion liq = new Liquidacion(strliq.getEmpleado(), comision, subtotal, total, false, null, fecha, strliq.getContratos());

                ldi.crearLiquidacion(liq);
                for (Iterator<Contrato> it = strliq.getContratos().iterator(); it.hasNext();) {
                    Contrato contrat = it.next();
                    contrat.setLiquidacion(liq);
                    cdi.modificarContrato(contrat);
                }
                this.liquidaciones.add(liq);

            }

            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext extContext = ctx.getExternalContext();
            String urlRedirect = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Backend/liquidado.xhtml"));
            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            try {
                extContext.redirect(urlRedirect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<EstructuraLiquidacion> agregarContrato(List<EstructuraLiquidacion> LstEstrliq, Contrato contrato) {
        for (int i = 0; i <= LstEstrliq.size(); i++) {
            if (LstEstrliq.get(i).getEmpleado().getIdUsuario().equals(contrato.getCotizacion().getOferta().getUsuario().getIdUsuario())) {
                Set contratosD = LstEstrliq.get(i).getContratos();
                contratosD.add(contrato);
                LstEstrliq.get(i).setContratos(contratosD);
                return LstEstrliq;
            }
        }
        return LstEstrliq;
    }

    public int getSubtotal(Contrato contrato) {
        int subtotal = 0;
        subtotal = contrato.getValorTotal() - getComision(contrato);
        return subtotal;
    }

    public int getComision(Contrato contrato) {
        int comision = 0;
        ComisionDaoImplement cdi = new ComisionDaoImplement();
        comision = (int) (contrato.getValorTotal() * cdi.getComisionCotizacion(contrato.getCotizacion()).getValor().floatValue());
        return comision;
    }

    public void setContratosPendientes(List<Contrato> contratosPendientes) {
        this.contratosPendientes = contratosPendientes;
    }

    public CategoriaConverter getCvn() {
        return cvn;
    }

    public void setCvn(CategoriaConverter cvn) {
        this.cvn = cvn;
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getNombres(Contrato contrat) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        Usuario usr = udi.buscarUsuariobyID(contrat.getCotizacion().getOferta().getUsuario().getIdUsuario().toString());
        String valor = usr.getNombres() + "  " + usr.getApellidos();
        return valor;
    }

    public String getNombresUsr(Usuario usuario) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        Usuario usr = udi.buscarUsuariobyID(usuario.getIdUsuario().toString());
        String valor = usr.getNombres() + "  " + usr.getApellidos();
        return valor;
    }

    public String getCedula(Contrato contrat) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        Usuario usr = udi.buscarUsuariobyID(contrat.getCotizacion().getOferta().getUsuario().getIdUsuario().toString());
        String valor = String.valueOf(usr.getDocumentoIdentidad());
        return valor;
    }

    public String getCedulaUsr(Usuario usuario) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        Usuario usr = udi.buscarUsuariobyID(usuario.getIdUsuario().toString());
        String valor = String.valueOf(usr.getDocumentoIdentidad());
        return valor;
    }

    public List<Liquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(List<Liquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

}
