package model;
// Generated 4/07/2016 02:03:24 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Liquidacion generated by hbm2java
 */
@Entity
@Table(name="liquidacion"
    ,catalog="contrataciondb"
)
public class Liquidacion  implements java.io.Serializable {


     private Integer idliquidacion;
     private Usuario usuario;
     private int comision;
     private int subtotal;
     private int total;
     private boolean pago;
     private Date fechaPago;
     private Date fechaLiquidacion;
     private Set contratos = new HashSet(0);

    public Liquidacion() {
    }

	
    public Liquidacion(Usuario usuario, int comision, int subtotal, int total, boolean pago, Date fechaPago, Date fechaLiquidacion) {
        this.usuario = usuario;
        this.comision = comision;
        this.subtotal = subtotal;
        this.total = total;
        this.pago = pago;
        this.fechaPago = fechaPago;
        this.fechaLiquidacion = fechaLiquidacion;
    }
    public Liquidacion(Usuario usuario, int comision, int subtotal, int total, boolean pago, Date fechaPago, Date fechaLiquidacion, Set contratos) {
       this.usuario = usuario;
       this.comision = comision;
       this.subtotal = subtotal;
       this.total = total;
       this.pago = pago;
       this.fechaPago = fechaPago;
       this.fechaLiquidacion = fechaLiquidacion;
       this.contratos = contratos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idliquidacion", unique=true, nullable=false)
    public Integer getIdliquidacion() {
        return this.idliquidacion;
    }
    
    public void setIdliquidacion(Integer idliquidacion) {
        this.idliquidacion = idliquidacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_idUsuario", nullable=false)
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @Column(name="comision", nullable=false)
    public int getComision() {
        return this.comision;
    }
    
    public void setComision(int comision) {
        this.comision = comision;
    }

    
    @Column(name="subtotal", nullable=false)
    public int getSubtotal() {
        return this.subtotal;
    }
    
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    
    @Column(name="total", nullable=false)
    public int getTotal() {
        return this.total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }

    
    @Column(name="pago", nullable=false)
    public boolean isPago() {
        return this.pago;
    }
    
    public void setPago(boolean pago) {
        this.pago = pago;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_pago", nullable=false, length=10)
    public Date getFechaPago() {
        return this.fechaPago;
    }
    
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_liquidacion", nullable=false, length=10)
    public Date getFechaLiquidacion() {
        return this.fechaLiquidacion;
    }
    
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="liquidacion")
    public Set getContratos() {
        return this.contratos;
    }
    
    public void setContratos(Set contratos) {
        this.contratos = contratos;
    }




}


