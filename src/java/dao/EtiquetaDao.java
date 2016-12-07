/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Etiqueta;

/**
 *
 * @author andre
 */
public interface EtiquetaDao {
    public List<Etiqueta> findAll();

    public boolean modificarEtiqueta(Etiqueta selectedEtiqueta);

    public boolean insertarEtiqueta(Etiqueta createEtiqueta);
}
