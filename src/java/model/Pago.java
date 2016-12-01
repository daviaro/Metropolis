package model;
// Generated 4/07/2016 02:03:24 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Pago generated by hbm2java
 */
@Entity
@Table(name="pago"
    ,catalog="contrataciondb"
)
public class Pago  implements java.io.Serializable {


     private Integer idPago;
     private Contrato contrato;
     private TipoPago tipoPago;
     private Date fecha;
     private int valorPago;
     private String numeroAutorizacion;
     private String respuestaDelServicioWeb;
     private Date fechaCreacion;
     private Date fechaUltimaActualizacion;

    public Pago() {
    }

	
    public Pago(Contrato contrato, TipoPago tipoPago, Date fecha, int valorPago, String numeroAutorizacion, String respuestaDelServicioWeb, Date fechaCreacion) {
        this.contrato = contrato;
        this.tipoPago = tipoPago;
        this.fecha = fecha;
        this.valorPago = valorPago;
        this.numeroAutorizacion = numeroAutorizacion;
        this.respuestaDelServicioWeb = respuestaDelServicioWeb;
        this.fechaCreacion = fechaCreacion;
    }
    public Pago(Contrato contrato, TipoPago tipoPago, Date fecha, int valorPago, String numeroAutorizacion, String respuestaDelServicioWeb, Date fechaCreacion, Date fechaUltimaActualizacion) {
       this.contrato = contrato;
       this.tipoPago = tipoPago;
       this.fecha = fecha;
       this.valorPago = valorPago;
       this.numeroAutorizacion = numeroAutorizacion;
       this.respuestaDelServicioWeb = respuestaDelServicioWeb;
       this.fechaCreacion = fechaCreacion;
       this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idPago", unique=true, nullable=false)
    public Integer getIdPago() {
        return this.idPago;
    }
    
    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contrato_idContrato", nullable=false)
    public Contrato getContrato() {
        return this.contrato;
    }
    
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tipo_pago_idTipoPago", nullable=false)
    public TipoPago getTipoPago() {
        return this.tipoPago;
    }
    
    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha", nullable=false, length=10)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    @Column(name="valor_pago", nullable=false)
    public int getValorPago() {
        return this.valorPago;
    }
    
    public void setValorPago(int valorPago) {
        this.valorPago = valorPago;
    }

    
    @Column(name="numero_autorizacion", nullable=false, length=45)
    public String getNumeroAutorizacion() {
        return this.numeroAutorizacion;
    }
    
    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    
    @Column(name="respuesta_del_servicio_web", nullable=false, length=100)
    public String getRespuestaDelServicioWeb() {
        return this.respuestaDelServicioWeb;
    }
    
    public void setRespuestaDelServicioWeb(String respuestaDelServicioWeb) {
        this.respuestaDelServicioWeb = respuestaDelServicioWeb;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_creacion", nullable=false, length=10)
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_ultima_actualizacion", length=10)
    public Date getFechaUltimaActualizacion() {
        return this.fechaUltimaActualizacion;
    }
    
    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }




}


