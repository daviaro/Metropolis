/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Cotizacion;
import model.Oferta;
import model.Usuario;

/**
 *
 * @author andre
 */
public interface CotizacionDao {
    public Boolean insertarCotizacion(Cotizacion cotizacion);
    public List<Cotizacion> findAllbyOferta(Oferta oferta);
    public List<Cotizacion> findAllbyOfertaPendiente(Oferta oferta);
    public List<Cotizacion> findAllbyOfertaContratada(Oferta oferta);
    public List<Cotizacion> findAllbyCotizacionesRealizadas(Usuario usuario);
    public boolean actualizarCotizacion(Cotizacion cotizacion);
    public List<Cotizacion> findAllbyCotizacionesAceptadas(Usuario usuarioRegistrado);
    public List<Cotizacion> findAllbyCotizacionesAceptadasComoEmpleado(Usuario usuarioEmpleado);
    public List<Cotizacion> findAllbyCotizacionesContraofertadas(Usuario usuarioRegistrado);
    public List<Cotizacion> findAll();
    public Cotizacion buscarCotizacionById(int idCoti);
}
