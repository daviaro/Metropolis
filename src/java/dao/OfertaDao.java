/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Oferta;

/**
 *
 * @author andre
 */
public interface OfertaDao {
    public List<Oferta> findAllbyRecientes();
    public List<Oferta> findAllbyRecientes(String filtroTitulo, String filtroUbicacion, String filtroJornada, String filtroValor);
    public List<Oferta> findAllbyCalificacion();
    public Integer countAll();
    public Boolean insertarOferta(Oferta oferta);
    public Boolean actualizarOferta(Oferta oferta);
    public Oferta buscarOfertabyId(Integer id);

    public List<Oferta> findAllbyIdUsuario(Integer idUsuario);

    public List<Oferta> buscarOfertabyNombre(String query);
}
