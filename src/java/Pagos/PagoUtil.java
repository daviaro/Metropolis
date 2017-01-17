/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pagos;

import dao.ContratoDaoImplement;
import dao.CotizacionDaoImplement;
import dao.PagoDaoImplement;
import dao.TipoPagoDaoImplement;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Contrato;
import model.Cotizacion;
import model.Pago;
import model.TipoPago;

/**
 *
 * @author jotamarios
 */
public class PagoUtil {
    
    public void almacenarPago(String idCotizacionstr, String MetododePago, String fechastr, String valorstr, 
            String numeroAutorizacion, String respuestaDelServicioWeb){
        
        Date fecha = new Date(fechastr);
        Date fechaCreacion = new Date();  
        
        int valor = Integer.parseInt(valorstr);
        int idCotizacion = Integer.parseInt(idCotizacionstr);
        
        CotizacionDaoImplement ctdi = new CotizacionDaoImplement();
        Cotizacion cotizacion = ctdi.buscarCotizacionById(idCotizacion);
                
        ContratoDaoImplement cdi = new ContratoDaoImplement();
        Contrato contrato = new Contrato(cotizacion, fechaCreacion, cotizacion.getDescripcion(), valor, true);
        cdi.crearContrato(contrato);
        
        TipoPagoDaoImplement tpdi = new TipoPagoDaoImplement();
        TipoPago tp = tpdi.buscarTipoPago(MetododePago);
        
        PagoDaoImplement pdi = new PagoDaoImplement();
        Pago pago = new Pago(contrato, tp, fecha, valor, numeroAutorizacion, respuestaDelServicioWeb, fechaCreacion);
        pdi.insertarPago(pago);
        
        Set pagos = new HashSet();
        
        //List<Pago> pagos = new ArrayList<Pago>();
        pagos.add(pago);
        contrato.setPagos(pagos);
        cdi.modificarContrato(contrato);
    }
    
    public String getMD5(String referencia, int Total) {

        //Buscar valores fijos en web.xml /APIKEY, merchandId, currency
        String ApiKey = "4Vj8eK4rloUd272L48hsrarnUA";
        String merchandId = getMerchandID();
        String currency = "COP";
        String separador = "~";
        String cadena = ApiKey + separador + merchandId + separador + referencia + separador + String.valueOf(Total) + separador + currency;
        
        try {
            MessageDigest ds = MessageDigest.getInstance("MD5");
            ds.reset();
            ds.update(cadena.getBytes());
            byte[] digest = ds.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            cadena = hashtext;
        } catch (Exception e) {

        }
        return cadena;
    }
    
    public String getMD5Respuesta(String referencia, String MerchandID, String respuesta, String total) {

        //Buscar valores fijos en web.xml /APIKEY, merchandId, currency
        String ApiKey = "4Vj8eK4rloUd272L48hsrarnUA";
        String merchandId = MerchandID;
        String currency = "COP";
        String separador = "~";
        String respuestaCom = respuesta;
        String cadena = ApiKey + separador + merchandId + separador + referencia + separador + total + separador + currency + separador + respuestaCom;
        
        try {
            MessageDigest ds = MessageDigest.getInstance("MD5");
            ds.reset();
            ds.update(cadena.getBytes());
            byte[] digest = ds.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            cadena = hashtext;
        } catch (Exception e) {

        }
        return cadena;
    }
    
    
    public String getMerchandID(){
        return "508029";
    }
    
    public String getAccountID(){
        return "512326";
    }
}
