/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Oferta;
import model.Portafolio;

/**
 *
 * @author andre
 */
public interface PortafolioDao {
    
    public boolean insertarPortafolio(Portafolio portafolio);
    public List<Portafolio> getPortafolios(Oferta oferta) ;
}
