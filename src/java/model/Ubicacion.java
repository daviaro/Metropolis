package model;
// Generated 4/07/2016 02:03:24 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Ubicacion generated by hbm2java
 */
@Entity
@Table(name = "ubicacion",
         catalog = "contrataciondb"
)
public class Ubicacion implements java.io.Serializable {

    private Integer idUbicacion;
    private String barrio;
    private Float latitud;
    private Float longitud;
    private boolean estado;
    private Set usuarios = new HashSet(0);

    public Ubicacion() {
    }

    public Ubicacion(String barrio, boolean estado) {
        this.barrio = barrio;
        this.estado = estado;
    }

    public Ubicacion(String barrio, Float latitud, Float longitud, boolean estado, Set usuarios) {
        this.barrio = barrio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idUbicacion", unique = true, nullable = false)
    public Integer getIdUbicacion() {
        return this.idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    @Column(name = "barrio", nullable = false, length = 45)
    public String getBarrio() {
        return this.barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    @Column(name = "latitud", precision = 12, scale = 0)
    public Float getLatitud() {
        return this.latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    @Column(name = "longitud", precision = 12, scale = 0)
    public Float getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    @Column(name = "estado", nullable = false)
    public boolean isEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ubicacion")
    public Set getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }

    //Se crea para utilizar selectOneMenu
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbicacion != null ? idUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.idUbicacion == null && other.idUbicacion != null) || (this.idUbicacion != null && !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

}
