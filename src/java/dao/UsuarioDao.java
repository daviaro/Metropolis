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
 * @author anfevari
 */
public interface UsuarioDao{
    public List<Usuario> mostrarUsuarios();
    public Usuario buscarUsuario(Usuario usuario);
    public boolean insertarUsuario(Usuario usuario);
    public boolean modificarUsuario (Usuario usuario);
    public boolean eliminarUsuario(Integer idUsuario);
    public Integer countAll();
    public Usuario login(Usuario usuario);
    public Usuario buscarUsuariobyEmail(String email);
    public Usuario buscarUsuariobyCedula(String cedula);
    public List<Usuario> buscarUsuariobyNombre(String texto);
    public Usuario buscarUsuariobyEmailnCedula(Usuario usuarioRecuperar);
    public int insertarUsuario2(Usuario createUsuario);
    public Usuario buscarUsuariobyID(String ID);
    public List<Usuario> getUsuariosDestacados();
}
