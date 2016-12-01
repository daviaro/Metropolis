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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name="rol"
    ,catalog="contrataciondb"
)
public class Rol  implements java.io.Serializable {


     private Integer idRol;
     private String nombre;
     private boolean estado;
     private Set usuarios = new HashSet(0);

    public Rol() {
    }

	
    public Rol(String nombre, boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
    }
    public Rol(String nombre, boolean estado, Set usuarios) {
       this.nombre = nombre;
       this.estado = estado;
       this.usuarios = usuarios;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idRol", unique=true, nullable=false)
    public Integer getIdRol() {
        return this.idRol;
    }
    
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    
    @Column(name="nombre", nullable=false, length=45)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Column(name="estado", nullable=false)
    public boolean isEstado() {
        return this.estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Set getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }




}


