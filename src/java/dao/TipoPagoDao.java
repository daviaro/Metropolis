/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.TipoPago;

/**
 *
 * @author jotamarios
 */
public interface TipoPagoDao {
    public TipoPago buscarTipoPago(String descripcion);
    
}
