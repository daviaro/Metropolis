/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Usuario;

/**
 *
 * @author chris
 */
public interface TrabajadorDao {
    public List<Usuario> mostrarTrabajadores();
    public Usuario buscarTrabajador(Usuario trabajador);
    public void insertarTrabajador(Usuario trabajador);
    public void modificarTrabajador(Usuario trabajador);
    public void eliminarTrabajador(Usuario trabajador);    
    public Integer countAll();
    
}
