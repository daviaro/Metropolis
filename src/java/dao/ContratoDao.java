/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Contrato;
import model.Usuario;

/**
 *
 * @author andre
 */
public interface ContratoDao {
    public Boolean crearContrato(Contrato contrato);
    public boolean modificarContrato(Contrato contrato);
    public List<Contrato> findAllContratadoByUsuario(Usuario usuarioRegistrado);
    public Contrato findContratadoById(String idContrato);
    public List<Contrato> findAllContratos(Usuario usuario);
    public List<Contrato> findVigentes();
}
