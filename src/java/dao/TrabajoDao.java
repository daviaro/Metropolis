/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Trabajo;

/**
 *
 * @author andre
 */
public interface TrabajoDao {
    public List<Trabajo> mostrarTrabajos();
    public List<Trabajo> buscarTrabajosByCategoriaId(Integer idCategoria);
    public Trabajo buscarTrabajoById(Integer idTrabajo);
}
