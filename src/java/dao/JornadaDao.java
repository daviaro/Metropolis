/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Jornada;

/**
 *
 * @author chris
 */
public interface JornadaDao {
    public List<Jornada> mostrarJornadas();
    public Jornada buscarJornada(Jornada jornada);
    public Jornada buscarJornadabyId(Integer idJornada);
    public boolean insertarJornada(Jornada jornada);
    public boolean modificarJornada(Jornada jornada);
    public boolean eliminarJornada(Integer idjornada);
}
