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
import java.util.Date;
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
        
    }
    
    public String getMD5(String referencia, int Total) {

        //Buscar valores fijos en web.xml /APIKEY, merchandId, currency
        String ApiKey = "3Tnprb2xKM4F1Ne6jA2WwS5ten";
        String merchandId = "565605";
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
    
}
