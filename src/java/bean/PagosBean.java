/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Pagos.PagoUtil;
import dao.CotizacionDaoImplement;
import dao.UsuarioDaoImplement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Cotizacion;
import model.Usuario;
import util.MailUtil;

/**
 *
 * @author Jotamarios
 */
@ManagedBean(name = "pagosBean")
@SessionScoped
public class PagosBean {

    private Cotizacion cotizacionPagar;

    /**
     * Creates a new instance of PagosBean
     */
    public PagosBean() {
    }

    public Cotizacion getCotizacionPagar() {
        if (cotizacionPagar != null) {
            return cotizacionPagar;
        } else {
            String codigoReferencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referenceCode");
            int codigoReferenciaInt = 0;
            CotizacionDaoImplement cdi = new CotizacionDaoImplement();
            try {
                codigoReferenciaInt = Integer.parseInt(codigoReferencia);
            } catch (NumberFormatException nfe) {

            }
            return cdi.buscarCotizacionById(codigoReferenciaInt);
        }
    }

    public void setCotizacionPagar(Cotizacion cotizacion) {
        this.cotizacionPagar = cotizacion;
    }

    public int getValor() {
        if (cotizacionPagar.getValorContrapropuesta() != null && cotizacionPagar.getValorContrapropuesta() > 0) {
            return cotizacionPagar.getValorContrapropuesta().intValue();
        } else if (cotizacionPagar.getValorPresupuesto() > 0) {
            return cotizacionPagar.getValorPresupuesto();
        } else {
            return cotizacionPagar.getOferta().getCosto();
        }

    }

    public String getValorUnitario() {
        return getFormated(getValor());
    }

    public int getValorTotal() {
        try {
            int intVal = getValor();
            int cantidad = cotizacionPagar.getUnidadesTrabajo();

            int retorno = intVal * cantidad;
            return retorno;
        } catch (Exception ex) {
            System.out.print("Error de dato");
            return 0;
        }
        //return String.format(String.valueOf(getValor() * cotizacionPagar.getUnidadesTrabajo())," %d");
    }

    public String getFormatedValorTotal() {
        try {
            int intVal = getValor();
            int cantidad = cotizacionPagar.getUnidadesTrabajo();

            int retorno = intVal * cantidad;
            return getFormated(retorno);
        } catch (Exception ex) {
            System.out.print("Error de dato");
            return "";
        }
        //return String.format(String.valueOf(getValor() * cotizacionPagar.getUnidadesTrabajo())," %d");
    }

    public String getPresupuestado() {
        return getFormated(cotizacionPagar.getValorPresupuesto());
    }

    public String getFormated(int valor) {
        try {

            Locale locale = new Locale("es", "CO");
            NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
            String retorno = nf.format(valor);
            return retorno;
        } catch (Exception ex) {
            System.out.print("Error de dato");
            return "";
        }
    }

    public String getSignature() {
        PagoUtil pu = new PagoUtil();
        String retorno = pu.getMD5(this.cotizacionPagar.getIdCotizacion().toString(), getValorTotal());
        return retorno;
    }

    public String getMerchandID() {
        PagoUtil pu = new PagoUtil();
        return pu.getMerchandID();
    }

    public String getAccountID() {
        PagoUtil pu = new PagoUtil();
        return pu.getAccountID();
    }

    public String getURLRetorno() {
        PagoUtil pu = new PagoUtil();
        return pu.getURLRetorno();
    }

    public String getMailCliente() {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        Usuario usr = this.cotizacionPagar.getUsuario();

        usr = udi.buscarUsuariobyID(usr.getIdUsuario().toString());
        String mail = usr.getEmail();
        return mail;
    }

    public String getNombresCliente() {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();

        Usuario usr = this.cotizacionPagar.getUsuario();

        usr = udi.buscarUsuariobyID(usr.getIdUsuario().toString());
        String nombres = usr.getNombres() + " " + usr.getApellidos();
        return nombres;
    }

    public String getNombresTrabajador() {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();

        Usuario usr = this.cotizacionPagar.getOferta().getUsuario();

        usr = udi.buscarUsuariobyID(usr.getIdUsuario().toString());
        String nombres = usr.getNombres() + " " + usr.getApellidos();
        return nombres;
    }

    public String getURLPayU() {
        PagoUtil p = new PagoUtil();
        return p.getURLPayU();
    }

    public void cargarDatos() {

        String codigoReferencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referenceCode");
        if (cotizacionPagar == null) {
            int codigoReferenciaInt = 0;
            CotizacionDaoImplement cdi = new CotizacionDaoImplement();
            try {
                codigoReferenciaInt = Integer.parseInt(codigoReferencia);
            } catch (NumberFormatException nfe) {

            }
            cotizacionPagar = cdi.buscarCotizacionById(codigoReferenciaInt);
        }

        if (cotizacionPagar != null && cotizacionPagar.getContratos() != null && cotizacionPagar.getContratos().size() == 0) {

            String estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("transactionState");

            String firma = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("signature");
            String cus = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cus");
            String bancoPSE = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pseBank");
            String metodoPago = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lapPaymentMethodType");
            String codigoAutorizacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("authorizationCode");
            String textoEstado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lapTransactionState");
            String merchantId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("merchantId");
            String total = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("TX_VALUE");

            DecimalFormat df = new DecimalFormat("0.0");
            Double totalD = Double.valueOf(total);
            String totalUnDecimal = df.format(totalD);

            BigDecimal bg = new BigDecimal(total).setScale(1, RoundingMode.HALF_EVEN);
            String otro = bg.toPlainString();

            PagoUtil pu = new PagoUtil();

            String firmaRetorno = pu.getMD5Respuesta(codigoReferencia, merchantId, estado, otro);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String date = sdf.format(new Date());

            if (firmaRetorno.equals(firma)) {
                //Estado aprobado
                if (estado != null && estado.equals("4")) {
                    //CAMBIAR CotizacionPagar para buscarla si no existe
                    
                    String fechaTrabajo = sdf.format(this.cotizacionPagar.getFechaTrabajo());

                    pu.almacenarPago(this.cotizacionPagar.getIdCotizacion().toString(), metodoPago, date, String.valueOf(this.getValorTotal()), codigoAutorizacion, estado + '-' + textoEstado);
                    try {
                        MailUtil mi = new MailUtil();
                        mi.enviarMail(this.cotizacionPagar.getOferta().getUsuario().getEmail(), "Pago realizado por la oferta " + this.cotizacionPagar.getOferta().getIdOferta() + " de " + this.cotizacionPagar.getOferta().getUsuario().getNombres(),
                                " La oferta de " + this.cotizacionPagar.getUsuario().getNombres() + " para el trabajo de " + this.cotizacionPagar.getOferta().getTrabajo().getTitulo() + " para el día "
                                + fechaTrabajo + " fue realizado. Recuerda acudir a la fecha acordada para tener una buena calificación.");

                    } catch (Exception e) {

                    }
                }
            }

        }
    }
}
